package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void shouldGet200StatusCode() {
        HttpServer server = new HttpServer();
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
        assertEquals(200, client.execute().getStatusCode());
    }

    @Test
    void shouldGet401StatusCode() {
        HttpServer server = new HttpServer();
        HttpClient client = new HttpClient("localhost", server.getPort(), "echo?status=401");
        assertEquals(401, client.execute().getStatusCode());
    }
}
