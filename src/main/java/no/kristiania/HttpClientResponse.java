package no.kristiania;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static no.kristiania.HttpClient.readLine;

public class HttpClientResponse {

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

    }

    public int getStatusCode() {
        return Integer.parseInt(statusLine.split(" ")[1]);
    }

    public String getHeather(String heatherName) {
        return heathers.get(heatherName.toLowerCase());
    }
}
