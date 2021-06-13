package com.progra3.javaMDS.front;


import com.progra3.javaMDS.back.application.exceptions.CircularReferenceException;
import com.progra3.javaMDS.back.application.exceptions.EdgeAlreadyExistException;
import com.progra3.javaMDS.back.application.exceptions.EdgeDoesNotExistException;
import com.progra3.javaMDS.back.application.exceptions.VertexIndexOutOfBoundsException;
import com.progra3.javaMDS.back.domain.services.GraphService;
import com.progra3.javaMDS.front.Utils.Edge;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddEdges {

  private final JFrame frame;
  private JPanel panel;
  private final GraphService graphService;
  private final ArrayList<Edge> edges;
  private final int maxVertexIndex;
  private JSpinner vertex1, vertex2;
  private JLabel edgesRecord;
  private String record;


  AddEdges(JFrame frame, int vertexCount, GraphService graphService) {
    this.graphService = graphService;
    record = "";
    this.frame = frame;
    maxVertexIndex = vertexCount == 1 ? 1 : vertexCount - 1;
    edges = new ArrayList<>();
    initPanel(frame);
    initComponents();
  }

  private void initPanel(JFrame frame) {
    panel = new JPanel();
    panel.setBackground(Color.GRAY);
    panel.setBounds(0, 0, 784, 561);
    frame.getContentPane().add(panel);
    panel.setLayout(null);
  }

  private void initComponents() {

    JButton btnDone = new JButton("Aceptar");
    btnDone.setBounds(305, 398, 167, 36);
    btnDone.addActionListener((e) -> addEdgeToRecord());
    panel.add(btnDone);

    JButton btnGoBack = new JButton("<< Anterior");
    btnGoBack.addActionListener((e) -> displayGraphCreation());
    btnGoBack.setBounds(10, 497, 136, 36);
    panel.add(btnGoBack);

    vertex1 = new JSpinner();
    vertex1.setBounds(92, 322, 128, 20);
    vertex1.setModel(new SpinnerNumberModel(0, 0, maxVertexIndex, 1));
    panel.add(vertex1);

    JLabel vertex1Label = new JLabel("Vertice 1:");
    vertex1Label.setBounds(22, 322, 60, 14);
    panel.add(vertex1Label);

    vertex2 = new JSpinner();
    vertex2.setBounds(608, 322, 128, 20);
    vertex2.setModel(new SpinnerNumberModel(0, 0, maxVertexIndex, 1));
    panel.add(vertex2);
    JLabel vertex2Label = new JLabel("Vertice 2:");
    vertex2Label.setBounds(538, 322, 60, 14);
    panel.add(vertex2Label);

    JLabel edgesRecordLabel = new JLabel("Registro de aristas:");
    edgesRecordLabel.setBounds(22, 11, 150, 14);
    panel.add(edgesRecordLabel);

    edgesRecord = new JLabel("");
    edgesRecord.setVerticalAlignment(SwingConstants.TOP);
    edgesRecord.setBounds(144, 11, 612, 111);
    panel.add(edgesRecord);

    JButton btnNext = new JButton("Siguiente >>");
    btnNext.setBounds(638, 497, 136, 36);
    btnNext.addActionListener(e -> displayEndResult());
    panel.add(btnNext);

    JButton btnDeleteLast = new JButton("Borrar anterior");
    btnDeleteLast.addActionListener((e) -> deleteLast());
    btnDeleteLast.setBounds(157, 425, 136, 36);
    panel.add(btnDeleteLast);

    JButton btnDeleteOne = new JButton("Borrar uno");
    btnDeleteOne.addActionListener((e) -> deleteOne());
    btnDeleteOne.setBounds(480, 425, 136, 36);
    panel.add(btnDeleteOne);
  }

  private void addEdgeToRecord() {
    Edge edge = createEdge();
    if ((edge != null)) {
      try {
        graphService.addEdge(edge.getX(), edge.getY());
        edges.add(edge);
        String text = String.format("(%s,%s);", edge.getX(), edge.getY());
        record = record + text;
        edgesRecord.setText("[" + record + "]");

      } catch (VertexIndexOutOfBoundsException | CircularReferenceException | EdgeAlreadyExistException e) {
        JOptionPane.showMessageDialog(this.panel, e.getMessage(), "Error: " + e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private Edge createEdge() {
      return new Edge((Integer) vertex1.getValue(), (Integer) vertex2.getValue());
  }

  private void deleteOne() {
    Edge edge = createEdge();
    if (edge != null) {
      try {
        graphService.removeEdge(edge.getX(), edge.getY());
        removeOneRecord(edge);
        edges.remove(edge);
      } catch (EdgeDoesNotExistException | VertexIndexOutOfBoundsException | CircularReferenceException e) {
        JOptionPane.showMessageDialog(this.panel, e.getMessage(), "Error: " + e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void removeOneRecord(Edge edge) {
    String edgeRecord = String.format("(%s,%s)", edge.getX(), edge.getY());

    String[] recordString = record.split(";");
    for (int i=0; i<recordString.length; i++) {
      if (recordString[i].equals(edgeRecord)) {
        recordString[i] = "";
        break;
      }
    }
    record = String.join(";", recordString);
    edgesRecord.setText(String.format("[%s]", record));
  }

  private void deleteLast() {
    Edge edge = createEdge();
    if (edge != null && edges.size() > 0) {
      try {
        graphService.removeEdge(edge.getX(),edge.getY());
        removeLastRecord();
        edges.remove(edges.size() - 1);
      } catch (EdgeDoesNotExistException | CircularReferenceException | VertexIndexOutOfBoundsException e) {
        JOptionPane.showMessageDialog(this.panel, e.getMessage(), "Error: "+e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  private void removeLastRecord() {
    String[] recordString = record.split(";");
    recordString[recordString.length - 1] = "";
    record = String.join(";", recordString);
    edgesRecord.setText(String.format("[%s]", record));
  }

  private void displayGraphCreation() {
    new GraphCreation(getFrame());
    this.panel.setVisible(false);
    panel.setEnabled(false);
  }

  private void displayEndResult() {
      new EndResult(getFrame(),2);
      this.panel.setVisible(false);
      panel.setEnabled(false);
      //else JOptionPane.showMessageDialog(this.panel, "no hay aristas en este grafo");
  }

  private JFrame getFrame() {
    return this.frame;
  }
}