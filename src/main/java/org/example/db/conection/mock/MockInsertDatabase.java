package org.example.db.conection.mock;

import org.example.db.conection.IDatabase;
import org.example.db.query.QueryResultDTO;
import org.example.log.ILogger;
import org.example.log.LogType;
import org.example.log.MockLogger;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MockInsertDatabase implements IDatabase {

    ILogger log = MockLogger.get();

    @Override
    public QueryResultDTO process(String query) {

        log.log(LogType.SQL, query);

        QueryResultDTO resultDTO = new QueryResultDTO();

        if (query.equals("BEGIN TRANSACTION;") ||
                query.equals("COMMIT TRANSACTION;") ||
                query.equals("ROLLBACK TRANSACTION;")) {

            resultDTO.setGeneratedIds(Collections.emptyList());
            resultDTO.setRowsAffected(0);
        }

        if (query.toUpperCase().startsWith("INSERT INTO")) {

            String generatedId = UUID.randomUUID().toString();

            resultDTO.setGeneratedIds(List.of(generatedId));
            resultDTO.setRowsAffected(1);
        }

        resultDTO.setResultSet(Collections.emptyList());
        resultDTO.setSuccess(true);


        return resultDTO;
    }
}
