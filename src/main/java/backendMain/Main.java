package backendMain;

import backendMain.utils.HitCheck;
import backendMain.utils.ValidateValue;
import com.fastcgi.FCGIInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Properties;

import static backendMain.utils.ValidateValue.removeTrailingZeros;

public class Main {
    private static final String RESPONSE_TEMPLATE = """
            Content-Type: text/plain
            Content-Length: %d

            %s""";

    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
                long startTime = System.nanoTime();
                String body = fillBody(FCGIInterface.request.params);
                HashMap<String, String> params = parseJson(body);

                float x = Float.parseFloat(params.get("x"));
                float y = Float.parseFloat(params.get("y"));
                int r = Integer.parseInt(params.get("r"));

                long executionTime = System.nanoTime() - startTime;

                if (ValidateValue.checkX(x) && ValidateValue.checkY(y) && ValidateValue.checkR(r)) {
                    sendJson(createJsonResponse(HitCheck.hit(x, y, r), x, y, r, executionTime));
                } else {
                    sendJson("{\"error\": \"invalid data\"}");
                }
            } catch (NumberFormatException e) {
                sendJson("{\"error\": \"wrong query param type\"}");
            } catch (NullPointerException e) {
                sendJson("{\"error\": \"missed necessary query param\"}");
            } catch (Exception e) {
                sendJson(String.format("{\"error\": \"%s\"}", (Object) e.getStackTrace()));
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
            } catch (Exception ignored){}
        }
        return requestBody.toString();
    }

    private static void sendJson(String jsonDump) {
        System.out.println(String.format(RESPONSE_TEMPLATE, jsonDump.getBytes(StandardCharsets.UTF_8).length, jsonDump));
    }

    private static HashMap<String, String> parseJson(String json) {
        HashMap<String, String> params = new HashMap<>();
        json = json.replaceAll("[{}\"]", "");
        String[] keyValues = json.split(",");
        for (String keyValue : keyValues) {
            String[] pair = keyValue.split(":");
            if (pair.length == 2) {
                String key = pair[0].trim();
                String value = pair[1].trim();
                params.put(key, value);
            }
        }
        return params;
    }


    private static String createJsonResponse(boolean result, float x, float y, int r, long executionTime) {
        return String.format("%b\n%.1f\n%s\n%d\n%d", result, x, removeTrailingZeros(y), r, executionTime);
    }
}
