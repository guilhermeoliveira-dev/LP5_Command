package org.example.db.conection;

import org.example.db.query.QueryResultDTO;

public interface IDatabase {

    QueryResultDTO process(String query);

}
