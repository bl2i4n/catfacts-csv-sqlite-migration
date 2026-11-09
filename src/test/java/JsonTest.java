import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JsonTest {

    // #9
    @Test
    void serializeCatFact() {
        CatFact fact = new CatFact(1, "A cat has 230 bones while a human has 206");
        String json = Json.gson.toJson(fact);

        assertTrue(json.contains("A cat has 230 bones while a human has 206"));
        assertTrue(json.contains("\"id\": 1"));
    }

    // #10
    @Test
    void deserializeCatFact() {
        String jsonString = "{\"fact\": \"Brian is a cat\"}";
        NewFact jsonResult = Json.gson.fromJson(jsonString, NewFact.class);

        assertEquals("Brian is a cat", jsonResult.fact());
    }

    // #11
    @Test
    void checkApostrophe() {
        CatFact fact = new CatFact(1, "Cats don't like dogs");
        String json = Json.gson.toJson(fact);

        assertTrue(json.contains("'"));
    }

    // #12
    @Test
    void commaTest() {
        CatFact fact = new CatFact(9, "Cats, unlike dogs, cannot taste sweetness");
        String json = Json.gson.toJson(fact);
        CatFact jsonResult = Json.gson.fromJson(json, CatFact.class);

        assertEquals(fact, jsonResult);
    }
}