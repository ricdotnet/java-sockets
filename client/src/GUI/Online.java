package GUI;

import Utils.Colors;

import javax.swing.*;

public class Online extends JTextArea {

  public Online() {
    setSize(180, 440);
    setLocation(510, 30);
    setBackground(new Colors().GRAY);
    setEditable(false);
  }

}
