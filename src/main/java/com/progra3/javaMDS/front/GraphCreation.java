package com.progra3.javaMDS.front;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphCreation {
  private JPanel panel;
  private JSpinner vertexCounter;
  private int vertexCount;
  private JFrame frame;

  GraphCreation(JFrame frame) {
    this.frame = frame;
    initPanel(frame);
    initComponents();
  }

  private void initPanel(JFrame frame) {
	panel = new JPanel();
    panel.setBackground(Color.GRAY);
    panel.setBounds(0, 0, 784, 561);
    frame.getContentPane().add(panel);
    panel.setLayout(null);
    panel.setVisible(true);
  }

  private void initComponents() {
    vertexCounter = new JSpinner();
    vertexCounter.setModel(new SpinnerNumberModel(2, 2, null, 1));
    vertexCounter.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        setVertexValue();
      }
    });
    vertexCounter.setBounds(368, 371, 106, 20);
    panel.add(vertexCounter);

    JLabel VertexCountLabel = new JLabel("Cantidad de vertices:");
    VertexCountLabel.setBounds(242, 371, 180, 20);
    panel.add(VertexCountLabel);

    JLabel Titles = new JLabel("Crear Grafo");
    Titles.setHorizontalTextPosition(SwingConstants.CENTER);
    Titles.setFont(new Font("Palatino Linotype", Font.PLAIN, 28));
    Titles.setHorizontalAlignment(SwingConstants.CENTER);
    Titles.setBounds(157, 28, 430, 136);
    panel.add(Titles);

    JButton btnNext = new JButton("Siguiente >>");
    btnNext.setBounds(638, 497, 136, 36);
    btnNext.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        displayAddEdges();
      }
    });
    panel.add(btnNext);

    JButton btnDone = new JButton("Aceptar");
    btnDone.setBounds(297, 425, 136, 36);
    btnDone.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        verifyValues();
      }
    });
    panel.add(btnDone);
  }

  private void setVertexValue() {
    vertexCount = (Integer) vertexCounter.getValue();
  }

  public JFrame getFrame() {
    return frame;
  }

  private void displayAddEdges() {
    setVertexValue();
    AddEdges gc = new AddEdges(getFrame(), vertexCount);
    this.panel.setVisible(false);
    panel.setEnabled(false);
  }
  private void initController(){ }
  private void verifyValues() {
    setVertexValue();
    initController();
    vertexCount = (Integer) vertexCounter.getValue();
  }
}
