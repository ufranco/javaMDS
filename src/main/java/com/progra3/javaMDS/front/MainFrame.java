package com.progra3.javaMDS.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame {
  private JFrame frmJavaagm;
  JPanel panel;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        MainFrame window = new MainFrame();
        window.frmJavaagm.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Create the application.
   */
  public MainFrame() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {

    frmJavaagm = new JFrame();
    frmJavaagm.setTitle("javaAgm");
    frmJavaagm.getContentPane().setSize(new Dimension(800, 600));
    frmJavaagm.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    frmJavaagm.getContentPane().setLayout(null);
    frmJavaagm.setResizable(false);
    frmJavaagm.setBounds(100, 100, 800, 600);
    frmJavaagm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new JPanel();
    panel.setBounds(0, 0, 784, 561);
    frmJavaagm.getContentPane().add(panel);
    panel.setBackground(Color.GRAY);
    panel.setLayout(null);

    JButton btnStart = new JButton("Empezar");
    btnStart.setBounds(251, 218, 259, 78);
    btnStart.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        displayCreateGraph();
      }
    });
    panel.add(btnStart);
  }

  private void displayCreateGraph(){
    new GraphCreation(getFrame());
    this.panel.setVisible(false);
    panel.setEnabled(false);
  }

  public JFrame getFrame() {
    return frmJavaagm;
  }

}
