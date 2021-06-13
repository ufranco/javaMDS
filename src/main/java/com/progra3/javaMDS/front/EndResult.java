package com.progra3.javaMDS.front;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndResult {


  private JFrame frame;
  private JPanel panel;
  private JSpinner regionCounter;
  private JLabel result;
  private int[][] endResult;
  private int maxRegions;
  private int maxWeight;

  JButton btnStartProcess;

  public EndResult(JFrame frame, int edgesCount) {
    this.frame = frame;
    maxRegions = edgesCount;
    initPanel(frame);
    initComponents();
  }

  private void initComponents() {
    JPanel resultPanel = new JPanel();
    resultPanel.setBounds(10, 11, 764, 330);
    panel.add(resultPanel);
    resultPanel.setLayout(new GridLayout(1, 0, 0, 0));

    result = new JLabel("");
    result.setVerticalAlignment(SwingConstants.TOP);
    result.setVerticalTextPosition(SwingConstants.TOP);
    result.setHorizontalAlignment(SwingConstants.CENTER);
    result.setBackground(Color.LIGHT_GRAY);
    result.setFont(new Font("Verdana", Font.PLAIN, 16));
    resultPanel.add(result);

    regionCounter = new JSpinner();
    regionCounter.setBounds(352, 400, 201, 20);
    regionCounter.setModel(new SpinnerNumberModel(1, 1,maxRegions , 1));
    panel.add(regionCounter);

    JLabel regionCounterLabel = new JLabel("Cantidad de regiones:");
    regionCounterLabel.setBounds(214, 403, 127, 14);
    panel.add(regionCounterLabel);

    btnStartProcess = new JButton("Empezar");
    btnStartProcess.setBounds(296, 454, 143, 33);
    btnStartProcess.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        startProcess();
        btnStartProcess.removeMouseListener(this);
      }
    });
    panel.add(btnStartProcess);

    JButton btnStartOver = new JButton("Volver al Inicio");
    btnStartOver.setBounds(296, 498, 143, 33);
    btnStartOver.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        displayGraphCreation(getFrame());
      }
    });
    panel.add(btnStartOver);
  }

  private void startProcess() {

    btnStartProcess.setEnabled(false);

  }

  /*private void parseResponse() {
    String response ="";
    int[] vertex = new int[maxRegions];
    for(int i=0; i<maxRegions;i++) vertex[i] = i;

    for (int i=0; i< edges.size(); i++){
      response = response + edges.get(i).toString() +"<br>";
    }
    result.setText(String.format("<html>Vertices:<br>  %s<br><br>Aristas:<br>  %s<br>Peso total: %d</html>",Arrays.toString(vertex), response, maxWeight));
  }

  private void getResponse() {
    maxWeight = 0;
    for (int i=0; i<endResult.length;  i++){
      for (int j=0; j<endResult.length; j++){
        if (endResult[i][j] != Integer.MAX_VALUE) {
          maxWeight += endResult[i][j];
          Edge edge = new Edge(i, j, endResult[i][j]);
          if (!edges.contains(edge))
            edges.add(edge);
        }
      }
    }
      maxWeight/=2;
      parseResponse();
  }
*/
  private void initPanel(JFrame frame) {
    panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.GRAY);
    panel.setBounds(0, 0, 784, 561);
    frame.getContentPane().add(panel);
  }

  private void displayGraphCreation(JFrame frame) {
    GraphCreation gc = new GraphCreation(frame);
    this.panel.setVisible(false);
    panel.setEnabled(false);
  }

  private JFrame getFrame() {
    return this.frame;
  }

}
