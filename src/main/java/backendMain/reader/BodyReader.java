package backendMain.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class BodyReader {
    public static String readBody(Properties params) throws IOException {
        int length = Integer.parseInt(params.getProperty("CONTENT_LENGTH", "0"));
        if (length <= 0) return "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        char[] bodyChars = new char[length];
        int bytesRead = reader.read(bodyChars, 0, length);
        return new String(bodyChars, 0, bytesRead);
    }
}
