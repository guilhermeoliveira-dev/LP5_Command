package org.example;

import org.example.db.conection.IDatabaseConnection;
import org.example.db.query.InsertionQueryBuilder;
import org.example.db.query.Query;
import org.example.db.query.QueryResultDTO;
import org.example.log.ILogger;
import org.example.log.LogType;
import org.example.log.MockLogger;
import org.example.log.PrintLogger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;



public class Main {


    static class MockDatabaseConnection implements IDatabaseConnection {

        ILogger log = new MockLogger();

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

    private record User(String name, int balance, LocalDate admissionDate){

        public List<String> toStringList(){
            return List.of(
                    name,
                    Integer.toString(balance),
                    admissionDate.toString());
        }
    }

    public static void main(String[] args) {

        ILogger log = PrintLogger.get();
        IDatabaseConnection db = new MockDatabaseConnection();

        User user1 = new User("john", 1000, LocalDate.of(2026, 1, 1));
        User user2 = new User("mary", 1200, LocalDate.of(2026, 2, 1));

        Query query;

        try{
            query = new InsertionQueryBuilder()
                    .setDb(db)
                    .setLogger(log)
                    .setTableName("users")
                    .setColumns("name", "balance", "admission_date")
                    .setValues(List.of(
                            user1.toStringList(),
                            user2.toStringList()
                    ))
                    .build();
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return;
        }

        query.execute();

        query.cancel();

    }
}