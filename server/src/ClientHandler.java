import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread {

  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  private String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h" };
  private Random rand = new Random();

  public ClientHandler(Socket socket) throws Exception {
    this.clientSocket = socket;

    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    System.out.println("New client connected...");
    String username = randomUsername();
    Server.users.put(username, clientSocket);

    System.out.println(Server.users.size());

    start();
  }

  public void run() {
    while (true) {
      if (Server.users.size() > 0) {
        try {

          String msg = in.readLine();
          // while((msg = in.readLine()) != null) {
          // out.println(msg);

          if (!msg.equals("")) {
            for (String user : Server.users.keySet()) {
              Socket curSocket = Server.users.get(user);
              PrintWriter bc = new PrintWriter(curSocket.getOutputStream(), true);
              bc.println(msg);
            }
          } else {
            
          }

          // in.close();
          // out.close();
          // clientSocket.close();
        } catch (IOException e) {
          System.out.println(e.toString());
        }
      }
    }
  }

  public String randomUsername() {
    StringBuilder temp = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      temp.append(chars[rand.nextInt(chars.length)]);
    }

    return temp.toString();
  }

}
