package org.example;

import org.example.db.conection.IDatabase;
import org.example.db.conection.mock.MockInsertDatabase;
import org.example.db.query.InsertionQueryBuilder;
import org.example.db.query.Query;
import org.example.log.ILogger;
import org.example.log.PrintLogger;
import org.example.model.User;

import java.time.LocalDate;
import java.util.List;


public class Main {



    public static void main(String[] args) {

        ILogger log = PrintLogger.get();
        IDatabase db = new MockInsertDatabase();

        User user1 = new User("John", 1000, LocalDate.of(2026, 1, 1));
        User user2 = new User("Mary", 1200, LocalDate.of(2026, 2, 1));

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