package no.kristiania;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpServer(0);
        server.start();
    }

    @Test
    void shouldGetStatusCode200() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(),
                "/echo");
        assertEquals(200, client.execute().getStatusCode());
    }

    @Test
    void shouldGetStatusCode401() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(),
                "/echo?status=401");
        assertEquals(401, client.execute().getStatusCode());
    }

    @Test
    void shouldReturnHeathers() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(),
                "/echo?status=302&location=http://www.example.com");
        HttpClientResponse response = client.execute();
        assertEquals(302, response.getStatusCode());
        assertEquals("http://www.example.com", response.getHeather("location"));
    }
}
