package Main;

import Models.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;

  public void startConnection(String hostname, int port) throws IOException {
    clientSocket = new Socket(hostname, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  public void closeConnection() throws IOException {
    clientSocket.close();
    in.close();
    out.close();
  }

  public void sendMessage(String message) {
    out.println(message);
  }

  public String getIn() throws IOException {
    return this.in.readLine();
  }
  public PrintWriter getOut() {
    return this.out;
  }

}
