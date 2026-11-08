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
        
        List<CatFact> facts = new App.FactsHandler().getAllFacts(url);
        
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
        
        Optional<CatFact> facts = new App.FactsHandler().findFactById(url, 2);
        
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
        
        Optional<CatFact> facts = new App.FactsHandler().findFactById(url, 99);        
        
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

    // #2
    @Test
    void catFactKeepsData() {

        //1. Construct a new CatFact record, passing in an arbitrary id and fact ,
        CatFact fact = new CatFact(1, "A cat has 230 bones while a human has 206");

        //2. assert that the id is the id you passed in from constructing the record
        assertEquals(1, fact.id());
        
        //3. and assert that the fact is the fact you passed in from constructing the record.
        assertEquals("A cat has 230 bones while a human has 206", fact.fact());
    }

    // #3
    @Test
    void compareTwoCatFacts() {
    
        // initialize 2 cat facts
        CatFact catFactOne = new CatFact(1, "A cat has 230 bones while a human has 206");
        CatFact catFactTwo = new CatFact(1, "A cat has 230 bones while a human has 206");

        //asert
        assertEquals(catFactOne, catFactTwo);

    }

    // #4
    @Test
    void catFactContainsValues() {
    
        // initialize 2 cat facts
        CatFact catFactOne = new CatFact(1, "A cat has 230 bones while a human has 206");
        CatFact catFactTwo = new CatFact(1, "Cats sleep for about 70 percent of their lives");

        //asert
        assertNotEquals(catFactOne, catFactTwo);

    }

    // #5
    @Test
    void catFactsHasValues() {
    
        // initialize stuff
        List<CatFact> newFactList = new ArrayList<>();
        newFactList.add(new CatFact(1, "some text"));
        
        boolean newFactListExists = newFactList.contains(new CatFact(1, "some text"));

        //asert
        assertTrue(newFactListExists);

    }

    // #6
    @Test
    void newFactReturnsFact() {
    
        // initialize stuff
        NewFact newFact = new NewFact("Brian is a cat");

        //asert
        assertEquals("Brian is a cat", newFact.fact());

    }

    // #7
    @Test
    void checkErrorBody() {
    
        // initialize stuff
        ErrorBody newError = new ErrorBody("404");

        //asert
        assertEquals("404", newError.error());

    }

    // #8
    @Test
    void catFactsHasEmptyString() {
    
        // initialize stuff
        CatFact emptyCatFact = new CatFact(1, "");


        //asert
        assertNotNull(emptyCatFact);
        assertEquals("", emptyCatFact.fact());

    }

    // #9
    @Test
    void serializeCatFact() {
    
        // initialize stuff
        CatFact fact = new CatFact(1, "A cat has 230 bones while a human has 206");
        String json = Json.gson.toJson(fact);
        System.out.println(json);


        //asert
        assertTrue(json.contains("A cat has 230 bones while a human has 206"));
        assertTrue(json.contains("\"id\": 1"));

    }

    // #10
    @Test
    void deserializeCatFact() {

        // JSON String from POST
        // {"fact": "Brian is a cat"}
        // need to convert this to a string so we can then deserialize
        String jsonString = "{\"fact\": \"Brian is a cat\"}";
        // create an jsonResult which is a NewFact type and then we convert the json back to that object type
        NewFact jsonResult = Json.gson.fromJson(jsonString, NewFact.class);

        //asert
        assertEquals("Brian is a cat", jsonResult.fact());

    }

    // #11
    @Test
    void checkApostrophe() {
    
        // initialize stuff
        CatFact fact = new CatFact(1, "Cats don't like dogs");
        String json = Json.gson.toJson(fact);
        System.out.println(json);


        //asert
        assertTrue(json.contains("'"));

    }

    // #12
    @Test
    void commaTest() {
    
        // initialize stuff
        CatFact fact = new CatFact(9, "Cats, unlike dogs, cannot taste sweetness");
        String json = Json.gson.toJson(fact);
        System.out.println(json);

        CatFact jsonResult = Json.gson.fromJson(json, CatFact.class);



        //asert
        assertEquals(fact, jsonResult);

    }

    // #13
    @Test
    void loadFromCSVCheck() throws IOException{
    
        MigrateCsvToDb.facts.clear();
        // initialize stuff
        MigrateCsvToDb.loadFromCsv();

        //asert
        assertEquals(10, MigrateCsvToDb.facts.size());

    }

    // #14
    @Test
    void factWithCommas() throws IOException {
    
        // initialize stuff
        MigrateCsvToDb.loadFromCsv();

        //asert
        assertEquals("Cats, like people, tend to favor one paw over the other", MigrateCsvToDb.facts.get(8).fact());

    }

    // #15
    @Test
    void idCheck() throws IOException{
    
        // initialize stuff
        MigrateCsvToDb.loadFromCsv();

        //asert
        assertEquals(1, MigrateCsvToDb.facts.get(0).id());

    }

    // #16
    @Test
    void nextIdCheck() throws IOException{
    
        // initialize stuff
        MigrateCsvToDb.loadFromCsv();

        //asert
        assertEquals(11, MigrateCsvToDb.nextId);


    }

    // #17
    /*
    This test runs loadFromCsv twice and therefore we then have 20 entries
    loadFromCsv reads the CSV and adds everything to a list
    since we read the CSV twice we're simply adding everything to the list twice

    I would change what we expect to be 20 since we don't clear the list
    We should be expecting 20 actually since our test is checking if loading from CSV is working
    two times in a row and if so, we *should* be getting 20.

    In our actual App.java file we should be clearing the CSV as well to ensure we're not
    adding unnecessary values to our list
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