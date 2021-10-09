import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {

  private Socket clientSocket;
  private BufferedWriter out;
  private BufferedReader in;

  public ClientHandler(Socket socket) throws Exception {
    this.clientSocket = socket;

//    out = new PrintWriter(clientSocket.getOutputStream(), true);
    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    String username = in.readLine();
    System.out.println("New user connected: " + username);
    Server.users.put(clientSocket, username);
    StringBuilder listOfUsers = onlineUsers();

    for(Socket user : Server.users.keySet()) {
      PrintWriter sendNewUser = new PrintWriter(user.getOutputStream(), true);
      sendNewUser.println("newUser");
      sendNewUser.println(listOfUsers);
      sendNewUser.println("finished");
      sendNewUser.println("Say hello to " + username);
    }

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

  public StringBuilder onlineUsers() {
    int index = 0;
    StringBuilder listOfUsers = new StringBuilder();
    for(Socket user : Server.users.keySet()) {
      String username = Server.users.get(user);
      listOfUsers.append(username).append("\n");
//      index++;
//      if(index < Server.users.size()) {
//        listOfUsers.append("\n");
//      }
    }
    return listOfUsers;
  }

}
