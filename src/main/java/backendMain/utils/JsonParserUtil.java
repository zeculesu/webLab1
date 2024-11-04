package backendMain.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonParserUtil {
    public static JsonObject parseJson(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }
}
