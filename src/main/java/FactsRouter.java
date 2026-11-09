import com.google.gson.JsonParseException;

import java.io.IOException;
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
            return new Response(exchange, 404, new ErrorBody("not found"), null);
        }

        String idText = path.substring("/facts/".length());
        int id;
        try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                return new Response(exchange, 400, new ErrorBody("id must be a whole number"), null);
            }

        if (method.equals("GET")) {
            Optional<CatFact> catFactFound = database.findFactById(id);

            if (catFactFound.isPresent()) {
                return new Response(exchange, 200, catFactFound.get(), null);
            } else {
                return new Response(exchange, 404, new ErrorBody("no fact with id " + id), null);
            }
        } else {
            return new Response(exchange, 405, new ErrorBody("method not allowed"), null);
        }
    }


}