package org.example.db.conection;

import org.example.db.query.QueryResultDTO;

public interface IDatabaseConnection {

    QueryResultDTO process(String query);

}
