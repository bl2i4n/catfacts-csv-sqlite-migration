import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;




class AppTest {

    /*
    What is a record?
    A special kind of class that automatically generates constructors, accessors, and utility methods
   
    We have 3 records in App.java
    1. CatFact
    2. NewFact
    3. ErrorBody 
    */

    @Test
    void catFactKeepsData() {

        //1. Construct a new CatFact record, passing in an arbitrary id and fact ,
        App.CatFact fact = new App.CatFact(1, "A cat has 230 bones while a human has 206");

        //2. assert that the id is the id you passed in from constructing the record
        assertEquals(1, fact.id());
        
        //3. and assert that the fact is the fact you passed in from constructing the record.
        assertEquals("A cat has 230 bones while a human has 206", fact.fact());
    }

    @Test
    void compareTwoCatFacts() {
    
        // initialize 2 cat facts
        App.CatFact catFactOne = new App.CatFact(1, "A cat has 230 bones while a human has 206");
        App.CatFact catFactTwo = new App.CatFact(1, "A cat has 230 bones while a human has 206");

        //asert
        assertEquals(catFactOne, catFactTwo);

    }

    @Test
    void twoCatFactsAreDifferent() {
    
        // initialize 2 cat facts
        App.CatFact catFactOne = new App.CatFact(1, "A cat has 230 bones while a human has 206");
        App.CatFact catFactTwo = new App.CatFact(1, "Cats sleep for about 70 percent of their lives");

        //asert
        assertNotEquals(catFactOne, catFactTwo);

    }

}