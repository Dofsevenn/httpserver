package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    @Test
    void shouldExecuteHttpRequest() {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo");
        assertEquals(200, client.execute().getStatusCode());
    }

    @Test
    void shouldReadStatusCode() {
        HttpClient client = new HttpClient("urlecho.appspot.com", 80, "/echo?status=401");
        assertEquals(401, client.execute().getStatusCode());
    }
}
