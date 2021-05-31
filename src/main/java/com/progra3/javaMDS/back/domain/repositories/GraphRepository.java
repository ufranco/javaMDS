package com.progra3.javaMDS.back.domain.repositories;

import com.progra3.javaMDS.back.application.algorithms.interfaces.GraphAlgorithm;
import com.progra3.javaMDS.back.application.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphRepository {

  public ArrayList<Set<Integer>> getNeighbors() {
    return (ArrayList<Set<Integer>>) neighbors.clone();
  }

  private final ArrayList<Set<Integer>> neighbors;

  public GraphRepository(final Integer graphSize) throws InvalidGraphSizeException {
    if(graphSize <= 0 ) {
      throw new InvalidGraphSizeException("Graph size cannot be negative nor null: "+ graphSize);
    }

    neighbors = new ArrayList<>(graphSize);

    for (int x = 0; x < graphSize; x++) {
      neighbors.add(new HashSet<>());
    }
  }

  public void addEdge(final Integer x, final Integer y) throws CircularReferenceException, VertexIndexOutOfBoundsException, EdgeAlreadyExistException {
    checkEdge(x, y);

    if(neighbors.get(x).contains(y)) {
      throw new EdgeAlreadyExistException("That edge already exists!");
    }

    neighbors.get(x).add(y);
    neighbors.get(y).add(x);
  }

  public void removeEdge(final Integer  x, final Integer  y) throws CircularReferenceException, VertexIndexOutOfBoundsException, EdgeDoesNotExistException {

    checkEdge(x, y);

    if(!neighbors.get(x).contains(y)) {
      throw new EdgeDoesNotExistException("Edge to be removed does not exists: " + x + ", " + y);
    }

    neighbors.get(x).remove(y);
    neighbors.get(y).remove(x);

  }

  public Boolean edgeExists(final Integer x, final Integer y) throws CircularReferenceException, VertexIndexOutOfBoundsException {
    checkEdge(x, y);

    return neighbors.get(x).contains(y);
  }

  public Set<Integer> getNeighbors(final Integer vertex) throws VertexIndexOutOfBoundsException {
    checkVertex(vertex);
    return neighbors.get(vertex);
  }

  public Integer size() {
    return this.neighbors.size();
  }

  public Integer getEdgeGrade(final Integer vertex) {
    return neighbors.get(vertex).size();
  }

  private void checkEdge(final Integer x, final Integer y) throws CircularReferenceException, VertexIndexOutOfBoundsException {

    if (x.equals(y)) {
      throw new CircularReferenceException("Loops not supported (" + x + ", "+ y + ")");
    }

    checkVertex(x);
    checkVertex(y);

  }

  private void checkVertex(final Integer vertex) throws VertexIndexOutOfBoundsException {
    if (vertex < 0 || vertex >= size()) {
      throw new VertexIndexOutOfBoundsException("Vertex out of graph range: " + vertex);
    }
  }

  public Boolean isConnected() {
    final var connectedGraphs = new HashSet<Integer>();
    neighbors.forEach(connectedGraphs::addAll);

    return connectedGraphs.size() == size();
  }

  public boolean isClique(final Set<Integer> set) throws VertexIndexOutOfBoundsException, CircularReferenceException, NullSetException {
    if (set == null) {
      throw new NullSetException("Set cannot be null");
    }

    for (Integer vertex: set) {
      checkVertex(vertex);
    }

    if (set.isEmpty()) {
      return true;
    }

    for (Integer vertex: set) {
      for (Integer otherVertex: set) {
        if (!vertex.equals(otherVertex)) {
          if (!edgeExists(vertex, otherVertex)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  public void executeAlgorithm(GraphAlgorithm algorithm) { algorithm.execute(neighbors); }
}
