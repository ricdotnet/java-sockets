package Main;

import GUI.*;
import Utils.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class Main extends Thread {

  MainWindow mainWindow;
  Modal askUsername;
  JTextField usernameBox = new JTextField();
  JButton setUsername = new JButton();
  JLabel currentUser = new JLabel();
  JLabel onlineUsers = new JLabel();
  JLabel sendMessageTip = new JLabel();

  ClientSocket clientSocket;

  Boolean online = false;

  public static void main (String[] args) {

    Main main = new Main();

    // main.initSocket();
    main.initMain();
  }

  public void initSocket () {
    try {
      clientSocket = new ClientSocket();
      clientSocket.startConnection("admin.rrocha.uk", 6666, usernameBox.getText());
      online = true;
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  public void initMain () {
    askUsername = new Modal("Enter your username", 300, 100);
    askUsername.setLayout(null);

    usernameBox.setSize(300, 50);
    usernameBox.setLocation(0, 0);
    setUsername.setText("Set");
    setUsername.setSize(300, 20);
    setUsername.setLocation(0, 50);
    setUsername.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        if (usernameBox.getText().equals("")) {
          usernameBox.setBackground(new Colors().RED);
        } else {
          currentUser.setText("Username: " + usernameBox.getText());
          askUsername.setVisible(false);
          mainWindow.setVisible(true);
          initSocket();
          start();
        }
      }
    });

    onlineUsers.setLocation(510, 10);
    onlineUsers.setSize(180, 20);
    onlineUsers.setText("Online Users");
    sendMessageTip.setText("Write message and press Enter");
    sendMessageTip.setSize(500, 20);
    sendMessageTip.setLocation(10, 10);

    askUsername.add(setUsername);
    askUsername.add(usernameBox);
    askUsername.setVisible(true);
    askUsername.setAlwaysOnTop(true);

    mainWindow = new MainWindow();
    currentUser.setLocation(10, 70);
    currentUser.setSize(150, 20);
    mainWindow.add(currentUser);
    mainWindow.add(onlineUsers);
    mainWindow.add(sendMessageTip);
//    mainWindow.setVisible(true);

    mainWindow.messageBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        clientSocket.sendMessage(currentUser.getText(), mainWindow.messageBox.getText());
        mainWindow.messageBox.setText("");
      }
    });

    mainWindow.sendMessage.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        clientSocket.sendMessage(currentUser.getText(), mainWindow.messageBox.getText());
//        mainWindow.messagesArea.append(message + "\n");
        mainWindow.messageBox.setText("");
      }
    });

    mainWindow.addWindowListener(new WindowListener() {
      @Override
      public void windowOpened (WindowEvent e) {

      }

      @Override
      public void windowClosing (WindowEvent e) {
        clientSocket.closeConnection();
      }

      @Override
      public void windowClosed (WindowEvent e) {

      }

      @Override
      public void windowIconified (WindowEvent e) {

      }

      @Override
      public void windowDeiconified (WindowEvent e) {

      }

      @Override
      public void windowActivated (WindowEvent e) {

      }

      @Override
      public void windowDeactivated (WindowEvent e) {

      }
    });
  }

  public void run () {
    while (true) {
      try {

        String incoming = clientSocket.getIn();
        if (incoming.equals("newUser")) {
          mainWindow.onlineBox.setText("");
          while(!(incoming = clientSocket.getIn()).equals("finished")) {
            mainWindow.onlineBox.append(incoming + "\n");
          }
//          mainWindow.onlineBox.append(clientSocket.getIn() + "\n");
        } else {
          mainWindow.messagesArea.append(incoming + "\n");
        }
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }

}
