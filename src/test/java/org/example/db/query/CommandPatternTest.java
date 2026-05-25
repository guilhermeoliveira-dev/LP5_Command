package org.example.db.query;

import org.example.db.conection.mock.MockDatabase;
import org.example.db.query.insert.InsertionQueryBuilder;
import org.example.db.query.update.UpdateQuery;
import org.example.exception.IllegalActionException;
import org.example.log.MockLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandPatternTest {

    private MockLogger spyLogger;
    private MockDatabase mockDb;

    @BeforeEach
    void setUp() {
        spyLogger = MockLogger.get();
        spyLogger.clearLogs();
        mockDb = new MockDatabase();
    }

    @Test
    void givenInsertionQuery_whenExecute_thenRunsInsertTransaction() {
        Query insertQuery = new InsertionQueryBuilder()
                .setDb(mockDb)
                .setLogger(spyLogger)
                .setTableName("users")
                .setColumns("name", "balance")
                .setValues(List.of(List.of("Bruce", "5000")))
                .build();

        insertQuery.execute();

        assertTrue(spyLogger.containsLog("BEGIN TRANSACTION;"));
        assertTrue(spyLogger.containsLog("INSERT INTO users (name, balance)"));
        assertTrue(spyLogger.containsLog("VALUES (Bruce, 5000);"));
        assertTrue(spyLogger.containsLog("COMMIT TRANSACTION;"));
    }

    @Test
    void givenExecutedInsertionQuery_whenCancel_thenRunsDeleteTransaction() {
        Query insertQuery = new InsertionQueryBuilder()
                .setDb(mockDb)
                .setLogger(spyLogger)
                .setTableName("users")
                .setColumns("name", "balance")
                .setValues(List.of(List.of("Bruce", "5000")))
                .build();

        insertQuery.execute();
        spyLogger.clearLogs();

        insertQuery.cancel();

        assertTrue(spyLogger.containsLog("BEGIN TRANSACTION;"));
        assertTrue(spyLogger.containsLog("DELETE FROM users"));
        assertTrue(spyLogger.containsLog("WHERE id IN (mock-uuid-1234);"));
        assertTrue(spyLogger.containsLog("COMMIT TRANSACTION;"));
    }

    @Test
    void givenUpdateQuery_whenExecute_thenSavesStateAndRunsUpdate() {
        Query updateQuery = new UpdateQuery(
                spyLogger,
                mockDb,
                "users",
                List.of("balance"),
                List.of("3000"),
                "mock-uuid-1234"
        );

        updateQuery.execute();

        assertTrue(spyLogger.containsLog("BEGIN TRANSACTION;"));
        assertTrue(spyLogger.containsLog("SELECT id, balance FROM users WHERE id = mock-uuid-1234;"));
        assertTrue(spyLogger.containsLog("UPDATE users SET balance = 3000 WHERE id = mock-uuid-1234;"));
        assertTrue(spyLogger.containsLog("COMMIT TRANSACTION;"));
    }

    @Test
    void givenExecutedUpdateQuery_whenCancel_thenRestoresPreviousState() {
        Query updateQuery = new UpdateQuery(
                spyLogger,
                mockDb,
                "users",
                List.of("balance"),
                List.of("3000"),
                "mock-uuid-1234"
        );

        updateQuery.execute();
        spyLogger.clearLogs();

        updateQuery.cancel();

        assertTrue(spyLogger.containsLog("BEGIN TRANSACTION;"));
        assertTrue(spyLogger.containsLog("UPDATE users SET balance = 1000 WHERE id = mock-uuid-1234;"));
        assertTrue(spyLogger.containsLog("COMMIT TRANSACTION;"));
    }

    @Test
    void givenUnexecutedUpdateQuery_whenCancel_thenThrowsIllegalActionException() {
        Query updateQuery = new UpdateQuery(
                spyLogger,
                mockDb,
                "users",
                List.of("balance"),
                List.of("3000"),
                "mock-uuid-1234"
        );

        assertThrows(IllegalActionException.class, updateQuery::cancel);
    }

    @Test
    void givenMultipleValues_whenExecuteInsertionQuery_thenGeneratesMultiRowInsertStatement() {
        Query multiInsertQuery = new InsertionQueryBuilder()
                .setDb(mockDb)
                .setLogger(spyLogger)
                .setTableName("users")
                .setColumns("name", "balance")
                .setValues(List.of(
                        List.of("Bruce", "5000"),
                        List.of("Clark", "3000"),
                        List.of("Diana", "8000")
                ))
                .build();

        multiInsertQuery.execute();

        assertTrue(spyLogger.containsLog("BEGIN TRANSACTION;"));
        assertTrue(spyLogger.containsLog("INSERT INTO users (name, balance)"));
        assertTrue(spyLogger.containsLog("VALUES (Bruce, 5000), (Clark, 3000), (Diana, 8000);"));
        assertTrue(spyLogger.containsLog("COMMIT TRANSACTION;"));
    }
}