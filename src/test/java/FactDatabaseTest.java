import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FactDatabaseTest {

    @TempDir
    Path tempDir;

    @Test
    void getAllFactsReturnsEveryRowInIdOrder() throws Exception {
        Path db = tempDir.resolve("test.db");
        String url = "jdbc:sqlite:" + db;

        try (Connection c = DriverManager.getConnection(url);
             Statement s = c.createStatement()) {
            s.executeUpdate("CREATE TABLE facts (id INTEGER PRIMARY KEY AUTOINCREMENT, fact TEXT NOT NULL)");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (1, 'Cats purr')");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (2, 'Cats, oddly, cannot taste sweetness')");
        }

        List<CatFact> facts = new FactDatabase(url).getAllFacts();

        assertEquals(2, facts.size());
        assertEquals(1, facts.get(0).id());
        assertEquals("Cats, oddly, cannot taste sweetness", facts.get(1).fact());
    }

    @Test
    void findFactByIdReturnsFact() throws Exception {
        Path db = tempDir.resolve("test.db");
        String url = "jdbc:sqlite:" + db;

        try (Connection c = DriverManager.getConnection(url);
             Statement s = c.createStatement()) {
            s.executeUpdate("CREATE TABLE facts (id INTEGER PRIMARY KEY AUTOINCREMENT, fact TEXT NOT NULL)");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (1, 'Cats purr')");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (2, 'Cats, oddly, cannot taste sweetness')");
        }

        Optional<CatFact> facts = new FactDatabase(url).findFactById(2);

        assertTrue(facts.isPresent());
        assertEquals("Cats, oddly, cannot taste sweetness", facts.get().fact());
    }

    @Test
    void findFactByIdReturnsEmpty() throws Exception {
        Path db = tempDir.resolve("test.db");
        String url = "jdbc:sqlite:" + db;

        try (Connection c = DriverManager.getConnection(url);
             Statement s = c.createStatement()) {
            s.executeUpdate("CREATE TABLE facts (id INTEGER PRIMARY KEY AUTOINCREMENT, fact TEXT NOT NULL)");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (1, 'Cats purr')");
            s.executeUpdate("INSERT INTO facts (id, fact) VALUES (2, 'Cats, oddly, cannot taste sweetness')");
        }

        Optional<CatFact> facts = new FactDatabase(url).findFactById(99);

        assertFalse(facts.isPresent());
    }
}