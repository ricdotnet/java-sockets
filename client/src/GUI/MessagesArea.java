package GUI;

import Utils.Colors;

import javax.swing.*;

public class MessagesArea extends JTextArea {

  public MessagesArea() {
    this.setSize(480, 300);
    this.setBackground(new Colors().GRAY);
    this.setLocation(10, 100);
    this.setEditable(false);
  }

}
