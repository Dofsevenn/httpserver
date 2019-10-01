package no.kristiania;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    private String URLECHO = "urlecho.appspot.com";

    @Test
    void shouldExecuteHttpRequest() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo");
        assertEquals(200, client.execute().getStatusCode());
    }

    @Test
    void shouldReadStatusCode() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo?status=401");
        assertEquals(401, client.execute().getStatusCode());
    }

    @Test
    void shouldReadHeathers() throws IOException {
        HttpClient client = new HttpClient(URLECHO, 80, "/echo?content-type=text/plain");
        assertEquals("text/plain; charset=utf-8", client.execute().getHeather("content-type"));
    }
}
