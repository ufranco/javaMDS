package com.progra3.javaMDS.front;


import com.progra3.javaMDS.back.domain.services.GraphService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

public class EndResult {


  private JFrame frame;
  private JPanel panel;
  private JLabel result;
  private JButton btnStartProcess;
  private final GraphService graphService;
  private Set<Integer> endResult;
  private final ArrayList<Set<Integer>> originalGraph;

  public EndResult(JFrame frame, GraphService graphService) {
    originalGraph = graphService.getGraph();
    this.frame = frame;
    this.graphService = graphService;
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
    endResult = graphService.executeMDS();
    parseResponse();
  }

  private void parseResponse() {
    String response ="<html>Vertices:<br>";
    for(Integer i : endResult){
      response = response + "Vertice: "+i.toString() + "<br>  vecinos: [";
      Set<Integer> neighbours = originalGraph.get(i);
      response = response + neighbours.toString()+"]<br>";
    }
    System.out.println(response);
    result.setText(response+"</html>");
  }

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
