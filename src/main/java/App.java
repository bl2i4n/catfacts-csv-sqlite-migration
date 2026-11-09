import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.Optional;

/**
Wires all the parts together and starts the HTTPServer
We first connect the handler to the HTTPServer
 */
public class App {

    static final int PORT = 8080;
    static final String DB_URL = "jdbc:sqlite:catfacts.db";
    // removed the record lines
    // removed Gson stuff

    public static void main(String[] args) throws IOException {

        FactDatabase database = new FactDatabase(DB_URL);
        FactsRouter router = new FactsRouter(database);
        FactsHandler handler = new FactsHandler(router);

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/facts", handler);
        server.start();
        System.out.println("Cat Facts API running on http://localhost:" + PORT + "/facts");

        //removed factsHandler

        //removed getAllFacts

        //removed getFactById

        //removed createFacts

        //removed sendJson

    }
}
