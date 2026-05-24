package org.example.db.query;

import java.util.List;

public class QueryResultDTO{
    private boolean success;
    private int rowsAffected;
    private List<String> generatedIds;
    private List<List<String>> resultSet;

    public QueryResultDTO(){

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
        return generatedIds;
    }

    public QueryResultDTO setGeneratedIds(List<String> generatedIds) {
        this.generatedIds = generatedIds;
        return this;
    }

    public List<List<String>> getResultSet() {
        return resultSet;
    }

    public QueryResultDTO setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
        return this;
    }
}