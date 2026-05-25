package org.example.db.query;

import java.util.Collections;
import java.util.List;

public class QueryResultDTO{
    private boolean success;
    private int rowsAffected;
    private List<String> generatedIds;
    private List<List<String>> resultSet;

    public QueryResultDTO(){

    }

    public QueryResultDTO(QueryResultDTO result) {
        this.success = result.success;
        this.rowsAffected = result.getRowsAffected();
        this.resultSet = result.getResultSet();
        this.generatedIds = result.generatedIds;
    }

    public boolean isSuccess() {
        return success;
    }

    public QueryResultDTO setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public QueryResultDTO setRowsAffected(int rowsAffected) {
        this.rowsAffected = rowsAffected;
        return this;
    }

    public List<String> getGeneratedIds() {
        return Collections.unmodifiableList(this.generatedIds);
    }

    public QueryResultDTO setGeneratedIds(List<String> generatedIds) {
        this.generatedIds = generatedIds;
        return this;
    }

    public List<List<String>> getResultSet() {
        return Collections.unmodifiableList(this.resultSet);
    }

    public QueryResultDTO setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
        return this;
    }


}