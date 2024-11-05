package backendMain.handler;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private static final String RESPONSE_TEMPLATE = """
            Status: %d %s
            Content-Type: application/json
            Content-Length: %d

            %s""";

    public static String buildJsonResponse(int status, boolean result, float x, float y, int r) {
        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<>();
        response.put("result", result);
        response.put("x", x);
        response.put("y", y);
        response.put("r", r);
        String json = gson.toJson(response);
        return formatResponse(status, "OK", json);
    }

    public static String buildErrorResponse(int status, String errorMessage) {
        String json = String.format("{\"error\": \"%s\"}", errorMessage);
        return formatResponse(status, "Bad Request", json);
    }

    private static String formatResponse(int status, String message, String json) {
        return String.format(RESPONSE_TEMPLATE, status, message, json.getBytes(StandardCharsets.UTF_8).length, json);
    }
}
