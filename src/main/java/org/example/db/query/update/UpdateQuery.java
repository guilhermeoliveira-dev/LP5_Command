package org.example.db.query.update;

import org.example.db.conection.IDatabase;
import org.example.db.query.Query;
import org.example.db.query.QueryResultDTO;
import org.example.exception.IllegalActionException;
import org.example.log.ILogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpdateQuery extends Query {

    private final String tableName;
    private final List<String> columnsToUpdate;
    private final List<String> newValues;
    private final String id;

    private List<List<String>> previousState;

    public UpdateQuery(ILogger logger, IDatabase db, String tableName, List<String> columnsToUpdate, List<String> newValues, String id) {
        super(logger, db);
        this.tableName = tableName;
        this.columnsToUpdate = columnsToUpdate;
        this.newValues = newValues;
        this.id = id;
    }

    @Override
    protected void savePreviousState() {
        StringBuilder selectBackup = new StringBuilder("SELECT id, ");
        selectBackup.append(String.join(", ", columnsToUpdate));
        selectBackup.append(" FROM ").append(tableName);
        selectBackup.append(" WHERE id = ").append(id).append(";");

        QueryResultDTO backupResult = db.process(selectBackup.toString());
        this.previousState = backupResult.getResultSet();
    }


    @Override
    protected String generateQuery() {
        StringBuilder query = new StringBuilder();

        structureUpdate(query, tableName, newValues);

        return query.toString();
    }


    @Override
    protected String undoQuery() {

        if (previousState == null || previousState.isEmpty()) {
            throw new IllegalActionException(logger, "Cannot undo: No previous state recorded or no rows were affected.");
        }

        StringBuilder query = new StringBuilder();
        for(List<String> item: previousState){
            List<String> oldValues = item.subList(1, item.size());
            structureUpdate(query, tableName, oldValues);
        }
        return query.toString();
    }

    /*
    Model:
    UPDATE table_name
    SET column1 = value1, column2 = value2
    WHERE id = row_id;
     */
    private void structureUpdate(StringBuilder query, String tableName, List<String> values) {
        query.append("UPDATE ").append(tableName).append(" SET ");
        List<String> setAssignments = new ArrayList<>();
        for (int i = 0; i < columnsToUpdate.size(); i++) {
            setAssignments.add(columnsToUpdate.get(i) + " = " + values.get(i));
        }
        query.append(String.join(", ", setAssignments));
        query.append(" WHERE id = ").append(id).append(";");
    }
}
