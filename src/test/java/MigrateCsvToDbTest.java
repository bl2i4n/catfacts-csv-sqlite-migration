import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class MigrateCsvToDbTest {

    // #13
    @Test
    void loadFromCSVCheck() throws IOException {
        MigrateCsvToDb.facts.clear();
        MigrateCsvToDb.loadFromCsv();
        assertEquals(10, MigrateCsvToDb.facts.size());
    }

    // #14
    @Test
    void factWithCommas() throws IOException {
        MigrateCsvToDb.loadFromCsv();
        assertEquals("Cats, like people, tend to favor one paw over the other",
                MigrateCsvToDb.facts.get(8).fact());
    }

    // #15
    @Test
    void idCheck() throws IOException {
        MigrateCsvToDb.loadFromCsv();
        assertEquals(1, MigrateCsvToDb.facts.get(0).id());
    }

    // #16
    @Test
    void nextIdCheck() throws IOException {
        MigrateCsvToDb.loadFromCsv();
        assertEquals(11, MigrateCsvToDb.nextId);
    }

    // #17
    /*
    This test runs loadFromCsv twice and therefore we then have 20 entries.
    loadFromCsv reads the CSV and adds everything to a list, so reading it
    twice adds everything twice. loadFromCsv should clear the list first
    to avoid duplicates. This test is expected to stay red.
    */
    @Test
    void loadFromCSVTwice() throws IOException {
        MigrateCsvToDb.facts.clear();
        MigrateCsvToDb.loadFromCsv();
        MigrateCsvToDb.loadFromCsv();
        assertEquals(10, MigrateCsvToDb.facts.size());
    }
}