import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

class FactsHandler implements HttpHandler {

    private final FactsRouter router;

    FactsHandler(FactsRouter router) {
        this.router = router;
    }

}