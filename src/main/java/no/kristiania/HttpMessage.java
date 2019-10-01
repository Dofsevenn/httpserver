package no.kristiania;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    protected String body;
    protected String startLine;
    protected Map<String, String> heathers = new HashMap<>();

    public HttpMessage(InputStream inputStream) throws IOException {
        startLine = readLine(inputStream);
        String heatherLine;
        while(!(heatherLine = readLine(inputStream)).isBlank()) {
            int colonPos = heatherLine.indexOf(":");
            String heatherName = heatherLine.substring(0, colonPos).trim();
            String heatherValue = heatherLine.substring(colonPos+1).trim();
            System.out.println("HeatherLine: " + heatherName + " ->" + heatherValue);
            heathers.put(heatherName.toLowerCase(), heatherValue);
        }
        System.out.println();

        if(getHeather("content-length") != null) {
            this.body = readBytes(inputStream, getContentLength());
        }
    }

    public String getHeather(String heatherName) {
        return heathers.get(heatherName.toLowerCase());
    }

    public int getContentLength() {
        return Integer.parseInt(getHeather("content-length"));
    }

    public static String readLine(InputStream inputStream) throws IOException {
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

    protected String readBytes(InputStream inputStream, int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength ; i++) {
            body.append((char)inputStream.read());
        }
        return body.toString();
    }

    public String getStartLine() {
        return startLine;
    }
}
