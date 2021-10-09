import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

  private ServerSocket serverSocket;

  private final int PORT = 6666;

  public static HashMap<Socket, String> users = new HashMap<>();

  public static void main(String[] args) throws Exception {
    Server app = new Server();

    app.startServer(app.PORT);
  }

  private void startServer(int PORT) throws Exception {
    try {
      serverSocket = new ServerSocket(PORT);

      while (true) {
        new ClientHandler(serverSocket.accept());
      }

    } catch (IOException e) {
      System.out.println(e.toString());
    }

  }
}