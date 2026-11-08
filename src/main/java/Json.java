import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class Json {
    static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
}