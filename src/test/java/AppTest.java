import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;


/*

In the tests below we test each record: CatFact, NewFact and ErrorBody.
We also test loadFromCsv and the values we should be expecting from when we run that method.

There was one test that failed when we ran loadFromCsv twice and this test is expected to fail.
We need to update loadFromCsv within App.java to clear the list so we can get this test passing.

*/


class AppTest {

    /*
    Builds a db, adds two facts into it and then runs a test on our db
    Added this for the Seams homework
    */
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
        assertEquals("Cats, oddly, cannot taste sweetness",facts.get().fact());
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


    /*
    What is a record?
    A special kind of class that automatically generates constructors, accessors, and utility methods
   
    We have 3 records in App.java
    1. CatFact
    2. NewFact
    3. ErrorBody 
    */

    @Test
    void loadFromCSVTwice() throws IOException{
    
        // MigrateCsvToDb.facts.clear();
        // We can skip this test
        // initialize stuff
        MigrateCsvToDb.loadFromCsv();
        MigrateCsvToDb.loadFromCsv();

        //asert
        // same assertion as number 13 but we run loadFromCsv twice
        assertEquals(10, MigrateCsvToDb.facts.size());

    }

}

    // #6
    // @Test
    // void catFactsHasValues() {
    
    //     // initialize stuff

    //     //asert
    //     // assertTrue(newFactListExists);

    // }