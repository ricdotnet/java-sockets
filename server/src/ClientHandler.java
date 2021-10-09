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

    System.out.println("asjhsdf");

    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    String username = in.readLine();
    System.out.println("New user connected: " + username);
    Server.users.put(clientSocket, username);

    start();
  }

  public void run() {
    while (true) {

      for(Socket user : Server.users.keySet()) {
        if(user.isClosed())
          Server.users.remove(user);
      }

      if (Server.users.size() > 0) {
        try {

          String msg = in.readLine();

          if(msg.equals("closing")) {
            String user = Server.users.get(clientSocket);
            Server.users.remove(clientSocket);
          } else

          if (!msg.equals("")) {
            for (Socket user : Server.users.keySet()) {
//              Socket curSocket = Server.users.get(user);
//              String username = Server.users.get(user);

              PrintWriter bc = new PrintWriter(user.getOutputStream(), true);
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
