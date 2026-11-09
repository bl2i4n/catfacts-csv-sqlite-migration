import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FactsRouterTest {

    @TempDir
    Path tempDir;
    // initialize stuff
    // 1. build a temp db with one fact (same setup as your Week 9 tests)
    // 2. make a FactsRouter with a FactDatabase pointed at it
    // 3. call route with "GET", "/facts/99", and an empty body

    //asert
    // the status should be 404
    @Test

    void factsRouterReturns404() throws Exception {
    
        // initialize stuff
        Path db = tempDir.resolve("test.db");
        String url = "jdbc:sqlite:" + db;

    // 1. build a temp db with one fact (same setup as your Week 9 tests)
        try (Connection c = DriverManager.getConnection(url);
        Statement s = c.createStatement()) {

            s.executeUpdate("CREATE TABLE facts (id INTEGER PRIMARY KEY AUTOINCREMENT, fact TEXT NOT NULL)");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (1, 'Cats purr')");

        
        }
    // 2. make a FactsRouter with a FactDatabase pointed at it
        FactDatabase factDatabase = new FactDatabase(url);
        FactsRouter router = new FactsRouter(factDatabase);

    }

}