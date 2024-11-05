package backendMain;

import backendMain.handler.RequestHandler;
import com.fastcgi.FCGIInterface;


public class Main {
    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        RequestHandler requestHandler = new RequestHandler(fcgiInterface);
        requestHandler.processRequests();
    }
}
