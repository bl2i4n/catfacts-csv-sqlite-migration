import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

class FactsRouter {

    private final FactDatabase database;

    FactsRouter(FactDatabase database) {
        this.database = database;
    }

    // void route(HttpExchange exchange) throws IOException, SQLException {
    // String method = exchange.getRequestMethod();
    // String path = exchange.getRequestURI().getPath();

    Response route(String method, String path, String body) throws SQLException {

        if (path.equals("/facts") || path.equals("/facts/")) {
            switch (method) {
                case "GET"  -> { return new Response(200, database.getAllFacts(), null); }
                case "POST" -> { return createFact(body); }
                default     -> { return new Response(405, new ErrorBody("method not allowed"), null); }
            }
        }

        if (!path.startsWith("/facts/")) {
            return new Response(404, new ErrorBody("not found"), null);
        }

        String idText = path.substring("/facts/".length());
        int id;
        try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                return new Response(400, new ErrorBody("id must be a whole number"), null);
            }

        if (method.equals("GET")) {
            Optional<CatFact> catFactFound = database.findFactById(id);

            if (catFactFound.isPresent()) {
                return new Response(200, catFactFound.get(), null);
            } else {
                return new Response(404, new ErrorBody("no fact with id " + id), null);
            }
        } else {
            return new Response(405, new ErrorBody("method not allowed"), null);
        }
    }

    Response createFact(String body) throws IOException, SQLException {
        NewFact input;

        try {
                input = Json.gson.fromJson(body, NewFact.class);
            } catch (JsonParseException e) {
                return new Response(400, new ErrorBody("body must be JSON like {\"fact\": \"...\"}"));
            }
            if (input == null || input.fact() == null || input.fact().isBlank()) {
                return new Response(400, new ErrorBody("\"fact\" is required and cannot be blank"));
            }
            String text = input.fact().trim();
            if (text.contains("\n") || text.contains("\r")) {
                // One fact per line is how facts.csv is laid out.
                return new Response(400, new ErrorBody("\"fact\" must be a single line"));
            }

            // start section 6
            System.out.println("Within our createFact method");

            CatFact created = database.insertFact(text);

            exchange.getResponseHeaders().set("Location", "/facts/" + created.id());
            sendJson(exchange, 201, created);


    }

}