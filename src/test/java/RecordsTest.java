import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class RecordsTest {

    // #2
    @Test
    void catFactKeepsData() {
        CatFact fact = new CatFact(1, "A cat has 230 bones while a human has 206");
        assertEquals(1, fact.id());
        assertEquals("A cat has 230 bones while a human has 206", fact.fact());
    }

    // #3
    @Test
    void compareTwoCatFacts() {
        CatFact catFactOne = new CatFact(1, "A cat has 230 bones while a human has 206");
        CatFact catFactTwo = new CatFact(1, "A cat has 230 bones while a human has 206");
        assertEquals(catFactOne, catFactTwo);
    }

    // #4
    @Test
    void catFactContainsValues() {
        CatFact catFactOne = new CatFact(1, "A cat has 230 bones while a human has 206");
        CatFact catFactTwo = new CatFact(1, "Cats sleep for about 70 percent of their lives");
        assertNotEquals(catFactOne, catFactTwo);
    }

    // #5
    @Test
    void catFactsHasValues() {
        List<CatFact> newFactList = new ArrayList<>();
        newFactList.add(new CatFact(1, "some text"));
        boolean newFactListExists = newFactList.contains(new CatFact(1, "some text"));
        assertTrue(newFactListExists);
    }

    // #6
    @Test
    void newFactReturnsFact() {
        NewFact newFact = new NewFact("Brian is a cat");
        assertEquals("Brian is a cat", newFact.fact());
    }

    // #7
    @Test
    void checkErrorBody() {
        ErrorBody newError = new ErrorBody("404");
        assertEquals("404", newError.error());
    }

    // #8
    @Test
    void catFactsHasEmptyString() {
        CatFact emptyCatFact = new CatFact(1, "");
        assertNotNull(emptyCatFact);
        assertEquals("", emptyCatFact.fact());
    }
}