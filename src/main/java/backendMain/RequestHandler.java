package backendMain;

import backendMain.utils.HitCheck;
import backendMain.utils.JsonParserUtil;
import backendMain.utils.ValidateValue;
import com.fastcgi.FCGIInterface;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class RequestHandler {
    private final FCGIInterface fcgiInterface;

    public RequestHandler(FCGIInterface fcgiInterface) {
        this.fcgiInterface = fcgiInterface;
    }

    public void processRequests() {
        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
                Properties paramsRaw = FCGIInterface.request.params;
                String method = paramsRaw.getProperty("REQUEST_METHOD");
                String uri = paramsRaw.getProperty("REQUEST_URI");
                if (!"POST".equalsIgnoreCase(method)) {
                    sendResponse(ResponseBuilder.buildErrorResponse(405, "Метод недоступен"));
                    continue;
                } else if (!"/fastcgi/".equals(uri)) {
                    sendResponse(ResponseBuilder.buildErrorResponse(405, "Неправильный URI"));
                    continue;
                }

                String body = BodyReader.readBody(paramsRaw);
                JsonObject params = JsonParserUtil.parseJson(body);

                Float x = params.has("x") && !params.get("x").isJsonNull() ? params.get("x").getAsFloat() : null;
                String y = params.has("y") && !params.get("y").isJsonNull() ? params.get("y").getAsString() : null;
                Integer r = params.has("r") && !params.get("r").isJsonNull() ? params.get("r").getAsInt() : null;


                if (ValidateValue.checkX(x) && ValidateValue.checkY(y) && ValidateValue.checkR(r)) {
                    float yNum = ValidateValue.convertToFloatY(y);
                    boolean result = HitCheck.hit(x, yNum, r);
                    sendResponse(ResponseBuilder.buildJsonResponse(200, result, x, yNum, r));
                } else {
                    sendResponse(ResponseBuilder.buildErrorResponse(400, "Invalid data"));
                }
            } catch (Exception e) {
                sendResponse(ResponseBuilder.buildErrorResponse(400, e.getMessage()));
            }
        }
    }

    private void sendResponse(String response) {
        try {
            FCGIInterface.request.outStream.write(response.getBytes(StandardCharsets.UTF_8));
            FCGIInterface.request.outStream.flush();
        } catch (IOException ignored) {
        }
    }
}
