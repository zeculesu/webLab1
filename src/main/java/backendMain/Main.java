package backendMain;

import com.fastcgi.FCGIInterface;


public class Main {
    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        RequestHandler requestHandler = new RequestHandler(fcgiInterface);
        requestHandler.processRequests();
    }
}
