package org.example.db.conection.mock;

import org.example.db.conection.IDatabase;
import org.example.db.query.QueryResultDTO;
import org.example.log.LogType;
import org.example.log.MockLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockUpdateDatabase implements IDatabase {

    private final MockLogger log = MockLogger.get();

    @Override
    public QueryResultDTO process(String query) {

        log.log(LogType.SQL, query);

        QueryResultDTO resultDTO = new QueryResultDTO();

        if (query.equals("BEGIN TRANSACTION;") ||
                query.equals("COMMIT TRANSACTION;") ||
                query.equals("ROLLBACK TRANSACTION;")) {

            resultDTO.setGeneratedIds(Collections.emptyList())
                    .setRowsAffected(0);

            return resultDTO;
        }

        if (query.toUpperCase().startsWith("SELECT ID")) {

            String[] split = query.split(" ");
            String id = split[split.length - 1].replace(";", "");

            List<String> lastInsert = log.getLastInsertedData();

            List<String> rowData = new ArrayList<>();
            rowData.add(id);

            if (query.contains("balance")) {
                rowData.add(lastInsert.get(1));
            } else {
                rowData.addAll(lastInsert);
            }

            resultDTO.setResultSet(List.of(rowData));
        }

        if (query.toUpperCase().startsWith("UPDATE")) {
            resultDTO.setRowsAffected(1);
        }

        resultDTO.setSuccess(true);

        return resultDTO;
    }
}