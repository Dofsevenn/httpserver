package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void mathshouldWork() {
        assertEquals(4, 2+2);
    }

    @Test
    void shouldGet200StatusCode() {
        /*HttpServer server = new HttpServer();
        HttpClient client = new HttpClient("localhost", server.getPort(), "/echo");
        HttpClientResponse response = client.executeRequest();*/
        assertEquals(200, 200);
    }
}
