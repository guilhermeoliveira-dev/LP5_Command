package org.example.db.query.insert;

import org.example.db.conection.IDatabase;
import org.example.db.query.Query;
import org.example.exception.IllegalActionException;
import org.example.log.ILogger;

import java.util.ArrayList;
import java.util.List;

public class InsertionQuery extends Query {

    private final String tableName;
    private final List<String> columns;
    private final List<List<String>> values;


    InsertionQuery(ILogger logger, IDatabase db, String tableName, List<String> columns, List<List<String>> values) {
        super(logger, db);
        this.tableName = tableName;
        this.columns = columns;
        this.values = values;
    }


    /*
    Model:
    INSERT INTO table_name (column1, columns2, column3)
    VALUES
    (value1, value2, value3),
    (value1, value2, value3),
    (value1, value2, value3);
     */
    @Override
    protected String generateQuery(){
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ").append(tableName).append(" (");
        query.append(String.join(", ", columns));
        query.append(") ");

        query.append("VALUES ");
        List<String> lines = new ArrayList<>();
        for(List<String> line: values){
            lines.add("("+String.join(", ", line)+")");
        }
        query.append(String.join(", ", lines));
        query.append(";");

        return query.toString();
    }


    /*
    Model:
    DELETE FROM table_name
    WHERE id_column IN (id1, id2, id3, ...);
    */
    @Override
    protected String undoQuery() {

        if(result == null){
            throw new IllegalActionException(logger, "Cannot undo a query that was not executed");
        }

        StringBuilder query = new StringBuilder();

        query.append("DELETE FROM ").append(tableName);
        query.append(" WHERE id IN (");
        query.append(String.join(", ", result.getGeneratedIds()));
        query.append(");");

        return query.toString();
    }


}
