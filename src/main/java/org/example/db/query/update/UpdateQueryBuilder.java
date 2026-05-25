package org.example.db.query.update;

import org.example.db.conection.IDatabase;
import org.example.log.ILogger;

import java.util.List;

public class UpdateQueryBuilder {

    private ILogger logger;
    private IDatabase db;

    private String tableName;
    private List<String> columns;
    private List<String> values;
    private String id;


    public UpdateQuery build(){
        if(logger == null){
            throw new IllegalArgumentException("Empty Logger");
        }
        if(db == null){
            throw new IllegalArgumentException("Empty Database Connection");
        }
        if(tableName == null || tableName.isBlank()){
            throw new IllegalArgumentException("Empty Table Name");
        }
        if(columns.isEmpty() ){
            throw new IllegalArgumentException("Empty Columns");
        }
        if(values == null){
            throw new IllegalArgumentException("Empty Values");
        }
        if(id == null){
            throw new IllegalArgumentException("Empty Id for selection");
        }

        return new UpdateQuery(logger, db, tableName, columns, values, id);

    }

    public UpdateQueryBuilder setLogger(ILogger logger) {
        this.logger = logger;
        return this;
    }

    public UpdateQueryBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public UpdateQueryBuilder setColumns(List<String> columns) {
        this.columns = columns;
        return this;
    }

    public UpdateQueryBuilder setColumns(String... columns) {
        return setColumns(List.of(columns));
    }

    public UpdateQueryBuilder setValues(List<String> values) {
        this.values = values;
        return this;
    }

    public UpdateQueryBuilder setValues(String... values) {
        return setValues(List.of(values));
    }

    public UpdateQueryBuilder setDb(IDatabase db) {
        this.db = db;
        return this;
    }

    public UpdateQueryBuilder setId(String id) {
        this.id = id;
        return this;
    }
}
