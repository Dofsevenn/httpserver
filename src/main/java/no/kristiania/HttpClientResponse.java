package no.kristiania;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpClientResponse extends HttpMessage {

    private final String body;
    private String statusLine;
    private Map<String, String> heathers = new HashMap<>();

    public HttpClientResponse(InputStream inputStream) throws IOException {

        statusLine = readLine(inputStream);
        System.out.println(statusLine);
        String heatherLine;
        while(!(heatherLine = readLine(inputStream)).isBlank()) {
            int colonPos = heatherLine.indexOf(":");
            String heatherName = heatherLine.substring(0, colonPos).trim();
            String heatherValue = heatherLine.substring(colonPos+1).trim();
            System.out.println("HeatherLine: " + heatherName + " ->" + heatherValue);
            heathers.put(heatherName.toLowerCase(), heatherValue);
        }
        System.out.println();

        this.body = readBytes(inputStream, getContentLength());

    }

    private String readBytes(InputStream inputStream, int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength ; i++) {
            body.append((char)inputStream.read());
        }
        return body.toString();
    }

    public int getStatusCode() {
        return Integer.parseInt(statusLine.split(" ")[1]);
    }

    public String getHeather(String heatherName) {
        return heathers.get(heatherName.toLowerCase());
    }

    public int getContentLength() {
        return Integer.parseInt(getHeather("content-length"));
    }

    public String getBody() {
        return body;
    }
}
