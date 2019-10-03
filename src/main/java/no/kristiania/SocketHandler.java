package no.kristiania;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class SocketHandler implements Runnable {
    private Socket socket;
    private String fileLocation;

    public SocketHandler(Socket socket, String fileLocation) {
        this.socket = socket;
        this.fileLocation = fileLocation;
    }

    private Map<String, String> parseRequestParameters(String query) {
        Map<String, String> requestParameters = new HashMap<>();
        if(query != null) {
            for (String parameter : query.split("&")) {
                int equalsPos = parameter.indexOf("=");
                String parameterValue = parameter.substring(equalsPos+1);
                String parameterName = parameter.substring(0, equalsPos);
                requestParameters.put(parameterName, parameterValue);

            }

        }
        return requestParameters;
    }

    @Override
    public void run() {
        HttpServerRequest request;
        try {
            request = new HttpServerRequest(socket.getInputStream());


            //  GET /echo HTTP/1.1
            String requestLine = request.getStartLine();

            String requestTarget = requestLine.split(" ")[1];
            int questionPos = requestTarget.indexOf('?');
            String query = questionPos != -1 ? requestTarget.substring(questionPos + 1) : null;
            String requestPath = questionPos != -1 ? requestTarget.substring(0, questionPos) : requestTarget;
            Map<String, String> requestParameters = parseRequestParameters(query);

            System.out.println("Requested path: " + requestPath);
            if ( !requestPath.equals("/echo") ) {
                if(requestPath .equals("/")) {
                    requestPath = "/index.html";
                }
                File file = new File(fileLocation + requestPath);
                socket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + file.length() + "\r\n" +
                        "Content-type: " + Files.probeContentType(new File(fileLocation + requestPath).toPath()) + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n").getBytes());

                new FileInputStream(file).transferTo(socket.getOutputStream());
                socket.getOutputStream().flush();
                socket.close();
            } else {

                // sender response på /echo request
                String statusCode = requestParameters.getOrDefault("status", "200");
                String location = requestParameters.get("location");
                String body = requestParameters.getOrDefault("body", "Hello World!");

                socket.getOutputStream().write(("HTTP/1.1 " + statusCode + " OK\r\n" +

                        "Content-length: " + body.length() + "\r\n" +
                        "Content-type: " + "text/plain" + "\r\n" +
                        (location != null ? "Location: " + location + "\r\n" : "") +
                        "Connection: close\r\n" +
                        "\r\n" +
                        body).getBytes());
                socket.getOutputStream().flush();
                socket.close();
            }




        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
