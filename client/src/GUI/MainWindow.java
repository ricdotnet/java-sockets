package GUI;

import javax.swing.*;

import Utils.Colors;

public class MainWindow extends JFrame {

  public JTextField messageBox = new JTextField();
  public JButton sendMessage = new JButton();

  public MessagesArea messagesArea = new MessagesArea();

  public MainWindow() {
    this.setLayout(null);
    this.setSize(500, 500);
    this.setLocationRelativeTo(null);
    this.setTitle("Chat Client");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    messageBox.setSize(480, 30);
    messageBox.setLocation(10, 20);
    messageBox.setBackground(new Colors().RED);
    messageBox.setBorder(null);
    messageBox.setForeground(new Colors().WHITE);

    sendMessage.setSize(100, 30);
    sendMessage.setLocation(10, 60);
    sendMessage.setText("Send");

    this.add(messageBox);
    this.add(sendMessage);

    this.add(messagesArea);
  }

}
