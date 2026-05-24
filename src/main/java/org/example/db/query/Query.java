package org.example.db.query;

import org.example.db.conection.IDatabase;
import org.example.log.ILogger;
import org.example.log.LogType;

public abstract class Query implements ICommand{

    private final ILogger logger;
    private final IDatabase db;
    protected QueryResultDTO result;

    public Query(ILogger logger, IDatabase db) {
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
            logger.log(LogType.SYSTEM, "Beginning transaction:");
            db.process("BEGIN TRANSACTION;");

            logger.log(LogType.SYSTEM, "Executing query:");
            result = db.process(query);

            logger.log(LogType.SYSTEM, "Commiting transaction:");
            db.process("COMMIT TRANSACTION;");

        } catch(Exception e){
            logger.log(LogType.ERROR, e.getMessage());
            logger.log(LogType.SYSTEM, "Rolling back transaction:");
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
            logger.log(LogType.SYSTEM, "Beginning undo transaction:");
            db.process("BEGIN TRANSACTION;");

            logger.log(LogType.SYSTEM, "Executing query:");
            result = db.process(query);

            logger.log(LogType.SYSTEM, "Commiting undo transaction:");
            db.process("COMMIT TRANSACTION;");

        } catch(Exception e){
            logger.log(LogType.ERROR, e.getMessage());

            logger.log(LogType.SYSTEM, "Rolling undo back transaction:");
            db.process("ROLLBACK TRANSACTION;");
        }

    }

}
