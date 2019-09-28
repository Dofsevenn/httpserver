package no.kristiania;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpClient {


    private final String hostname;
    private final int port;
    private final String requestTarget;

    public HttpClient(String hostname, int port, String requestTarget) {
        this.hostname = hostname;
        this.port = port;
        this.requestTarget = requestTarget;
    }

    public HttpClientResponse execute() throws IOException {
       Socket socket = new Socket(hostname, port);

        socket.getOutputStream().write(("GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n" +
                "\r\n").getBytes());
        socket.getOutputStream().flush();

        String statusLine = readLine(socket.getInputStream());
        System.out.println(statusLine);

        return new HttpClientResponse(statusLine);
    }

    private String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while((c = inputStream.read()) != -1) {
            if(c == '\r') {
                inputStream.read();
                break;
            }
            line.append((char)c);
        }
        return line.toString();
    }
}
