import static org.junit.jupiter.api.Assertions.assertEquals;

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

}