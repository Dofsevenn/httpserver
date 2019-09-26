package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {


    public HttpServer(int port) {

    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        Socket socket = serverSocket.accept();

        StringBuilder readLine = new StringBuilder();
        int c;
        while((c = socket.getInputStream().read()) != -1) {
            if(c == '\r') {
                c = socket.getInputStream().read();
                System.out.println(readLine);
                if(readLine.toString().isBlank()) {
                    break;
                }
                readLine = new StringBuilder();
            }
            readLine.append((char)c);
        }

        socket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                "Content-type: text/plain\r\n" +
                "Content-length: 12\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                "Hello World!").getBytes());
    }

    public int getPort() {
        return 0;
    }
}
