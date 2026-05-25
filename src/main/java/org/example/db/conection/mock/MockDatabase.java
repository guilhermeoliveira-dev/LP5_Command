package org.example.db.conection.mock;

import org.example.db.conection.IDatabase;
import org.example.db.query.QueryResultDTO;
import org.example.log.LogType;
import org.example.log.MockLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockDatabase implements IDatabase {

    private final MockLogger log = MockLogger.get();

    @Override
    public QueryResultDTO process(String query) {

        log.log(LogType.SQL, query);

        QueryResultDTO resultDTO = new QueryResultDTO();

        if (query.equals("BEGIN TRANSACTION;") ||
                query.equals("COMMIT TRANSACTION;") ||
                query.equals("ROLLBACK TRANSACTION;")) {

            return resultDTO.setGeneratedIds(Collections.emptyList()).setRowsAffected(0);
        }

        if (query.toUpperCase().startsWith("INSERT INTO")) {
            resultDTO.setGeneratedIds(List.of("mock-uuid-1234"));
            resultDTO.setRowsAffected(1);
        }

        if (query.toUpperCase().startsWith("SELECT ID")) {
            List<String> rowData = new ArrayList<>();
            rowData.add("mock-uuid-1234");
            rowData.add("1000");
            resultDTO.setResultSet(List.of(rowData));
        }

        if (query.toUpperCase().startsWith("UPDATE") || query.toUpperCase().startsWith("DELETE")) {
            resultDTO.setRowsAffected(1);
        }

        return resultDTO.setSuccess(true);
    }
}