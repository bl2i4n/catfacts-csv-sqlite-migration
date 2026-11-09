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

    // static class FactsHandler implements HttpHandler {

    //     private final FactDatabase database = new FactDatabase(DB_URL);

    //     @Override
    //     public void handle(HttpExchange exchange) throws IOException {
    //         try {
    //             route(exchange);
    //         } catch (Exception e) {
    //             // Print the real problem in the server terminal, send a clean 500 to the client.
    //             e.printStackTrace();
    //             sendJson(exchange, 500, new ErrorBody("internal server error"));
    //         }
    //     }

    //     /** Decides which endpoint a request is for. */
    //     void route(HttpExchange exchange) throws IOException, SQLException {
    //         String method = exchange.getRequestMethod();
    //         String path = exchange.getRequestURI().getPath();

    //         // "/facts" or "/facts/"  ->  returns the whole collection
    //         if (path.equals("/facts") || path.equals("/facts/")) {
    //             switch (method) {
    //                 case "GET"  -> sendJson(exchange, 200, database.getAllFacts());
    //                 case "POST" -> createFact(exchange);
    //                 default     -> sendJson(exchange, 405, new ErrorBody("method not allowed"));
    //             }
    //             return;
    //         }

    //         // Anything else that merely starts with "/facts", like "/factsabc"
    //         if (!path.startsWith("/facts/")) {
    //             sendJson(exchange, 404, new ErrorBody("not found"));
    //             return;
    //         }

    //         // "/facts/{id}"  ->  returns one fact with the provided id
    //         String idText = path.substring("/facts/".length());
    //         int id;
    //         try {
    //             id = Integer.parseInt(idText);
    //         } catch (NumberFormatException e) {
    //             sendJson(exchange, 400, new ErrorBody("id must be a whole number"));
    //             return;
    //         }
    //         if (method.equals("GET")) {
    //             Optional<CatFact> catFactFound = database.findFactById(id);
    //             if (catFactFound.isPresent()) {
    //                 sendJson(exchange, 200, catFactFound.get());

    //             } else {
    //                 sendJson(exchange, 404, new ErrorBody("no fact with id " + id));                    
    //             }
    //         } else {
    //             sendJson(exchange, 405, new ErrorBody("method not allowed"));
    //         }
    //     }

        //removed getAllFacts

        //removed getFactById

        //removed createFacts

        // ---- Helper ------------------------------------------------------

        /** Converts any object to JSON with Gson and sends it with the given status. */
        void sendJson(HttpExchange exchange, int status, Object body) throws IOException {
            byte[] bytes = Json.gson.toJson(body).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(bytes);
            }
        }
    }
}
