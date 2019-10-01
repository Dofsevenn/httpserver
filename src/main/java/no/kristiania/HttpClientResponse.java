package no.kristiania;

public class HttpClientResponse {

    private String statusLine;

    public HttpClientResponse(String statusLine) {

        this.statusLine = statusLine;
    }

    public int getStatusCode() {
        return Integer.parseInt(statusLine.split(" ")[1]);
    }

    public String getHeather(String heatherName) {
        return null;
    }
}
