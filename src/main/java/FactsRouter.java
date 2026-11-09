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

        switch (method) {
            case "GET"  -> { return new Response(200, database.getAllFacts(), null); }
            case "POST" -> { return createFact(body); }
            default     -> { return new Response(405, new ErrorBody("method not allowed"), null); }
        }

        if (!path.startsWith("/facts/")) {
            sendJson(exchange, 404, new ErrorBody("not found"));
            return;
        }

        String idText = path.substring("/facts/".length());
        int id;
        try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                sendJson(exchange, 400, new ErrorBody("id must be a whole number"));
                return;
            }
        }

        if (method.equals("GET")) {
            Optional<CatFact> catFactFound = database.findFactById(id);

            if (catFactFound.isPresent()) {
                sendJson(exchange, 200, catFactFound.get());
            } else {
                sendJson(exchange, 404, new ErrorBody("no fact with id " + id));
            }
        } else {
            sendJson(exchange, 405, new ErrorBody("method not allowed"));
        }
    }

}