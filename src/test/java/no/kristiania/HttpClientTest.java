package no.kristiania;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    @Test
    void shouldExecuteHttpRequest() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo");
        assertEquals(200, client.execute().getStatusCode());
    }

    @Test
    void shouldReadStatusCode() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=401");
        assertEquals(401, client.execute().getStatusCode());
    }

    @Test
    void shouldReadHeathers() throws IOException {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?content-type=text/html");
        assertEquals("test/html", client.execute().getHeather("content-type"));
    }
}
