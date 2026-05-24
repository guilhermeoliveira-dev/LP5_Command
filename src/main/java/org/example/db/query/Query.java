package org.example.db.query;

import org.example.db.conection.IDatabaseConnection;
import org.example.log.ILogger;
import org.example.log.LogType;

public abstract class Query implements ICommand{

    private final ILogger logger;
    private final IDatabaseConnection db;
    protected QueryResultDTO result;

    public Query(ILogger logger, IDatabaseConnection db) {
        this.logger = logger;
        this.db = db;
    }

    protected abstract String generateQuery();
    protected abstract String undoQuery();

    @Override
    public void execute() {

        String query;

        try{
            query = generateQuery();
        } catch(IllegalArgumentException e){
            logger.log(LogType.ERROR, e.getMessage());
            return;
        }

        try{
            logger.log(LogType.DB_QUERY, "Beginning transaction:");
            db.process("BEGIN TRANSACTION;");

            logger.log(LogType.DB_QUERY, "Executing query:");
            result = db.process(query);

            logger.log(LogType.DB_QUERY, "Commiting transaction:");
            db.process("COMMIT TRANSACTION;");

        } catch(Exception e){
            logger.log(LogType.ERROR, e.getMessage());
            logger.log(LogType.DB_QUERY, "Rolling back transaction:");
            db.process("ROLLBACK TRANSACTION;");
        }


    }

    @Override
    public void cancel() {

        String query;

        try{
            query = undoQuery();
        } catch(IllegalArgumentException e){
            logger.log(LogType.ERROR, e.getMessage());
            return;
        }

        try{
            logger.log(LogType.DB_QUERY, "Beginning undo transaction:");
            db.process("BEGIN TRANSACTION;");

            logger.log(LogType.DB_QUERY, "Executing query:");
            result = db.process(query);

            logger.log(LogType.DB_QUERY, "Commiting undo transaction:");
            db.process("COMMIT TRANSACTION;");

        } catch(Exception e){
            logger.log(LogType.ERROR, e.getMessage());

            logger.log(LogType.DB_QUERY, "Rolling undo back transaction:");
            db.process("ROLLBACK TRANSACTION;");
        }

    }

}
