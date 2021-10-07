package GUI;

import javax.swing.*;

public class Modal extends JDialog {

  public Modal(String title, int width, int height) {
    this.setTitle(title);
    this.setSize(width, height);
    this.setLocationRelativeTo(null);
  }

}
