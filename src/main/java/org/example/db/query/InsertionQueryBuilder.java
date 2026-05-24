package org.example.db.query;

import org.example.db.conection.IDatabase;
import org.example.log.ILogger;

import java.util.ArrayList;
import java.util.List;

public class InsertionQueryBuilder {

    private ILogger logger;
    private IDatabase db;

    private String tableName;
    private List<String> columns;
    private List<List<String>> values;

    public InsertionQueryBuilder(){
        columns = new ArrayList<>();
    }

    public InsertionQuery build(){
        if(tableName == null || tableName.isBlank()){
            throw new IllegalArgumentException("Empty Table Name");
        }
        if(columns.isEmpty() ){
            throw new IllegalArgumentException("Empty Values and Columns");
        }
        if(logger == null){
            throw new IllegalArgumentException("Empty Logger");
        }

        return new InsertionQuery(logger, db, tableName, columns, values);

    }

    public InsertionQueryBuilder setLogger(ILogger logger) {
        this.logger = logger;
        return this;
    }

    public InsertionQueryBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertionQueryBuilder setColumns(List<String> columns) {
        this.columns = columns;
        return this;
    }

    public InsertionQueryBuilder setColumns(String... columns) {
        return setColumns(List.of(columns));
    }

    public InsertionQueryBuilder setValues(List<List<String>> values) {
        this.values = values;
        return this;
    }

    public InsertionQueryBuilder setDb(IDatabase db) {
        this.db = db;
        return this;
    }
}
