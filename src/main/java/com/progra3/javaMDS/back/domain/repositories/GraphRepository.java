package com.progra3.javaMDS.back.domain.repositories;

import com.progra3.javaMDS.back.application.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.iterate;

public final class GraphRepository {

  public List<Set<Integer>> getNeighbors() {
    return neighbors.stream().map(
        vertexNeighbor -> new HashSet<>(vertexNeighbor)
    ).collect(Collectors.toList());
  }

  private final ArrayList<Set<Integer>> neighbors;

  public GraphRepository(final Integer graphSize) throws InvalidGraphSizeException {
    if(graphSize <= 0 ) throw new InvalidGraphSizeException("Graph size cannot be negative nor null: "+ graphSize);

    neighbors = new ArrayList<>(graphSize);

    iterate(0, i -> i + 1)
        .limit(graphSize)
        .forEach(i -> neighbors.add(new HashSet<>()));
  }

  public void addEdge(final Integer x, final Integer y) throws
      CircularReferenceException,
      VertexIndexOutOfBoundsException,
      EdgeAlreadyExistException,
      NullVertexException
  {
    if(edgeExists(x,y)) throw new EdgeAlreadyExistException("That edge already exists!");

    neighbors.get(x).add(y);
    neighbors.get(y).add(x);
  }

  public void removeEdge(final Integer  x, final Integer  y) throws
      CircularReferenceException,
      VertexIndexOutOfBoundsException,
      EdgeDoesNotExistException,
      NullVertexException
  {
    if(!edgeExists(x,y)) throw new EdgeDoesNotExistException("Edge to be removed does not exists: " + x + ", " + y);

    neighbors.get(x).remove(y);
    neighbors.get(y).remove(x);
  }

  private Boolean edgeExists(final Integer x, final Integer y) throws
      CircularReferenceException,
      VertexIndexOutOfBoundsException,
      NullVertexException
  {
    checkEdge(x, y);

    return neighbors.get(x).contains(y);
  }

  private Integer size() { return neighbors.size(); }

  private void checkEdge(final Integer x, final Integer y) throws
      CircularReferenceException,
      VertexIndexOutOfBoundsException,
      NullVertexException
  {
    checkVertex(x);
    checkVertex(y);

    if (x.equals(y)) throw new CircularReferenceException("Loops not supported (" + x + ", "+ y + ")");

  }

  private void checkVertex(final Integer vertex) throws VertexIndexOutOfBoundsException, NullVertexException {
    if (vertex == null) throw new NullVertexException("Vertex cannot be null!");
    if (vertex < 0 || vertex >= size()) {
      throw new VertexIndexOutOfBoundsException("Vertex out of graph range: " + vertex);
    }
  }

}
