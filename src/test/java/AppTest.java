import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;





class AppTest {

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
        App.CatFact fact = new App.CatFact(1, "A cat has 230 bones while a human has 206");

        //2. assert that the id is the id you passed in from constructing the record
        assertEquals(1, fact.id());
        
        //3. and assert that the fact is the fact you passed in from constructing the record.
        assertEquals("A cat has 230 bones while a human has 206", fact.fact());
    }

    // #3
    @Test
    void compareTwoCatFacts() {
    
        // initialize 2 cat facts
        App.CatFact catFactOne = new App.CatFact(1, "A cat has 230 bones while a human has 206");
        App.CatFact catFactTwo = new App.CatFact(1, "A cat has 230 bones while a human has 206");

        //asert
        assertEquals(catFactOne, catFactTwo);

    }

    // #4
    @Test
    void catFactContainsValues() {
    
        // initialize 2 cat facts
        App.CatFact catFactOne = new App.CatFact(1, "A cat has 230 bones while a human has 206");
        App.CatFact catFactTwo = new App.CatFact(1, "Cats sleep for about 70 percent of their lives");

        //asert
        assertNotEquals(catFactOne, catFactTwo);

    }

    // #5
    @Test
    void catFactsHasValues() {
    
        // initialize stuff
        List<App.CatFact> newFactList = new ArrayList<>();
        newFactList.add(new App.CatFact(1, "some text"));
        
        boolean newFactListExists = newFactList.contains(new App.CatFact(1, "some text"));

        //asert
        assertTrue(newFactListExists);

    }

    // #6
    @Test
    void newFactReturnsFact() {
    
        // initialize stuff
        App.NewFact newFact = new App.NewFact("Brian is a cat");

        //asert
        assertEquals("Brian is a cat", newFact.fact());

    }

    // #7
    @Test
    void checkErrorBody() {
    
        // initialize stuff
        App.ErrorBody newError = new App.ErrorBody("404");

        //asert
        assertEquals("404", newError.error());

    }

    // #8
    @Test
    void catFactsHasEmptyString() {
    
        // initialize stuff
        App.CatFact emptyCatFact = new App.CatFact(1, "");


        //asert
        assertNotNull(emptyCatFact);
        assertEquals("", emptyCatFact.fact());

    }

    // #9
    @Test
    void serializeCatFact() {
    
        // initialize stuff
        App.CatFact fact = new App.CatFact(1, "A cat has 230 bones while a human has 206");
        String json = App.gson.toJson(fact);
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
        App.NewFact jsonResult = App.gson.fromJson(jsonString, App.NewFact.class);

        //asert
        assertEquals("Brian is a cat", jsonResult.fact());

    }

    // #11
    @Test
    void checkApostrophe() {
    
        // initialize stuff
        App.CatFact fact = new App.CatFact(1, "Cats don't like dogs");
        String json = App.gson.toJson(fact);
        System.out.println(json);


        //asert
        assertTrue(json.contains("'"));

    }

    // #12
    @Test
    void commaTest() {
    
        // initialize stuff
        App.CatFact fact = new App.CatFact(9, "Cats, unlike dogs, cannot taste sweetness");
        String json = App.gson.toJson(fact);
        System.out.println(json);

        App.CatFact jsonResult = App.gson.fromJson(json, App.CatFact.class);



        //asert
        assertEquals(jsonResult, fact);

    }

}

    // #6
    // @Test
    // void catFactsHasValues() {
    
    //     // initialize stuff

    //     //asert
    //     // assertTrue(newFactListExists);

    // }