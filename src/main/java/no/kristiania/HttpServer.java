package no.kristiania;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer implements Runnable {


    private ServerSocket serverSocket;
    private String fileLocation;

    public HttpServer(int port) throws IOException {

        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(8080);
        httpServer.setFileLocation("src/main/resources");
        httpServer.start();

    }

    void start() {

        new Thread(this::run).start();
    }

    @Override
    public void run() {

        while(serverSocket.isBound() && !serverSocket.isClosed()) {

            try {
                Socket socket = serverSocket.accept();
                SocketHandler handler = new SocketHandler(socket, fileLocation);

                new Thread(handler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
