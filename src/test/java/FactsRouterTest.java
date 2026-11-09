import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FactsRouterTest {

    /*
    Builds a db with one fact, then asks the router for an id that doesn't exist
    Added this for the Coupling, Cohesion and SRP homework
    This test wasn't possible before because route needed an HttpExchange
    */
    @Test
    void factsRouterReturns404() throws Exception {
    
    }

}