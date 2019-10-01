package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

            String statusCode = "200";

            String requestTarget = requestLine.split(" ")[1];
            int questionPos = requestTarget.indexOf('?');
            if(questionPos != -1) {
                String query = requestTarget.substring(questionPos);
                int equalsPos = query.indexOf('=');
                String parameterValue = query.substring(equalsPos+1);
                statusCode = parameterValue;
            }


            socket.getOutputStream().write(("HTTP/1.1 " + statusCode + " OK\r\n" +
                    "Content-type: text/plain\r\n" +
                    "Content-length: 12\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "Hello World!").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }
}
