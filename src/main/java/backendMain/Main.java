package backendMain;

import backendMain.utils.HitCheck;
import backendMain.utils.ValidateValue;
import com.fastcgi.FCGIInterface;
import com.google.gson.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Main {
    private static final String RESPONSE_TEMPLATE = """
            Status: %d %s
            Content-Type: application/json
            Content-Length: %d

            %s""";


    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
                String body = fillBody(FCGIInterface.request.params);
                JsonObject params = parseJson(body);

                float x = Float.parseFloat(params.get("x").getAsString());
                float y = Float.parseFloat(params.get("y").getAsString());
                int r = Integer.parseInt(params.get("r").getAsString());

                if (ValidateValue.checkX(x) && ValidateValue.checkY(y) && ValidateValue.checkR(r)) {
                    sendJson(createJsonResponse(HitCheck.hit(x, y, r), x, y, r));
                } else {
                    sendJsonStatus(400, "Bad Request", "{\"error\": \"invalid data\"}");
                }
            } catch (NumberFormatException e) {
                sendJsonStatus(400, "Bad Request", "{\"error\": \"wrong query param type\"}");
            } catch (NullPointerException | UnsupportedOperationException e) {
                sendJsonStatus(400, "Bad Request", "{\"error\": \"missed necessary query param\"}");
            } catch (Exception ignored) {
            }
        }
    }

    public static String fillBody(Properties params) {
        String contentLength = params.getProperty("CONTENT_LENGTH");
        int length = (contentLength != null) ? Integer.parseInt(contentLength) : 0;
        StringBuilder requestBody = new StringBuilder();
        if (length > 0) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                char[] bodyChars = new char[length];
                reader.read(bodyChars, 0, length);
                String line = new String(bodyChars);
                requestBody.append(line);
            } catch (Exception ignored) {
            }
        }
        return requestBody.toString();
    }

    private static JsonObject parseJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject;
    }

    private static void sendJson(String jsonDump) {
        String httpResponse = String.format(RESPONSE_TEMPLATE, 200, "OK", jsonDump.getBytes(StandardCharsets.UTF_8).length, jsonDump);
        try {
            FCGIInterface.request.outStream.write(httpResponse.getBytes(StandardCharsets.UTF_8));
            FCGIInterface.request.outStream.flush();
        } catch (IOException e) {
        }
    }

    private static void sendJsonStatus(int statusCode, String message, String jsonDump) {
        String httpResponse = String.format(RESPONSE_TEMPLATE, statusCode, message, jsonDump.getBytes(StandardCharsets.UTF_8).length, jsonDump);
        try {
            FCGIInterface.request.outStream.write(httpResponse.getBytes(StandardCharsets.UTF_8));
            FCGIInterface.request.outStream.flush();
        } catch (IOException e) {
        }
    }


    private static String createJsonResponse(boolean result, float x, float y, int r) {
        Gson gson = new Gson();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", result);
        responseMap.put("x", x);
        responseMap.put("y", y);
        responseMap.put("r", r);
        return gson.toJson(responseMap);
    }
}
