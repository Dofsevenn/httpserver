package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {


    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {

        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();
    }

    void start() {

        new Thread(this::run).start();
    }

    private void run() {
        try {
            Socket socket = serverSocket.accept();

            HttpServerRequest request = new HttpServerRequest(socket.getInputStream());
            String requestLine = request.getStartLine();

            String requestTarget = requestLine.split(" ")[1];
            Map<String, String> requestParameters = parseRequestParameters(requestTarget);

            String statusCode = requestParameters.getOrDefault("status", "200");
            String location = requestParameters.get("location");

            socket.getOutputStream().write(("HTTP/1.1 " + statusCode + " OK\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-length: 12\r\n" +
                    (location != null ? "Location: " + location + "\r\n" : "") +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "Hello World!").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseRequestParameters(String requestTarget) {
        Map<String, String> requestParameters = new HashMap<>();
        int questionPos = requestTarget.indexOf('?');
        if(questionPos != -1) {
            String query = requestTarget.substring(questionPos+1);
            for (String parameter : query.split("&")) {
                int equalsPos = parameter.indexOf("=");
                String parameterValue = parameter.substring(equalsPos+1);
                String parameterName = parameter.substring(0, equalsPos);
                requestParameters.put(parameterName, parameterValue);

            }

        }
        return requestParameters;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
