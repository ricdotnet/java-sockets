package Main;

import GUI.*;
import Utils.Colors;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends Thread {

  MainWindow mainWindow;
  Modal askUsername;
  JTextField usernameBox = new JTextField();
  JButton setUsername = new JButton();
  JLabel currentUser = new JLabel();

  ClientSocket clientSocket;

  Boolean online = false;

  public static void main (String[] args) {

    Main main = new Main();

    main.initSocket();
    main.initMain();
  }

  public void initSocket () {
    try {
      clientSocket = new ClientSocket();
      clientSocket.startConnection("localhost", 6666);
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
          currentUser.setText(usernameBox.getText());
          askUsername.setVisible(false);
        }
      }
    });

    askUsername.add(setUsername);
    askUsername.add(usernameBox);
//    askUsername.setVisible(true);
    askUsername.setAlwaysOnTop(true);

    mainWindow = new MainWindow();
    currentUser.setLocation(150, 60);
    currentUser.setSize(150, 20);
    mainWindow.add(currentUser);
    mainWindow.setVisible(true);

    mainWindow.sendMessage.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        clientSocket.sendMessage(mainWindow.messageBox.getText());
//        mainWindow.messagesArea.append(message + "\n");
        mainWindow.messageBox.setText("");
      }
    });

    start();
  }

  public void run () {
    while (true) {
      try {
        mainWindow.messagesArea.append(clientSocket.getIn() + "\n");
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }

}
