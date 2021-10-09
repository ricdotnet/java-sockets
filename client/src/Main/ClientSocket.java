package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;

  public void startConnection(String hostname, int port, String username) throws IOException {
    clientSocket = new Socket(hostname, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    out.println(username);
  }

  public void closeConnection() {
    try {
      clientSocket.close();
      in.close();
      out.close();
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  public void sendMessage(String username, String message) {
    out.println(username + ": " + message);
  }

  public String getIn() throws IOException {
    return this.in.readLine();
  }
  public PrintWriter getOut() {
    return this.out;
  }

}
