import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

  private Socket client;
  private PrintWriter out;
  private BufferedReader in;

  String usermsg;
  Boolean on = true;
  Scanner scanner = new Scanner(System.in);

  public void connect(String ip, int port) {
    try {
    client = new Socket(ip, port);
    out = new PrintWriter(client.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  public String sendMessage(String msg) throws IOException {
    out.println(msg);
    String response = in.readLine();
    return response;
  }

  public void disconnect() throws IOException {
    client.close();
    in.close();
    out.close();
  }

  public static void main(String[] args) {

    Client c = new Client();
    c.connect("localhost", 6666);

    while(c.on) {
      System.out.print("Message >> ");
      c.usermsg = c.scanner.nextLine();

      try {
        System.out.println(c.sendMessage(c.usermsg));
      } catch (IOException e) {
        System.out.println(e.toString());
      }
    }
  }

}