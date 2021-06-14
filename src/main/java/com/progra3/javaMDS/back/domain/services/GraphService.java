package com.progra3.javaMDS.back.domain.services;

import com.progra3.javaMDS.back.application.algorithms.MinimumDominatingSetProcedure;
import com.progra3.javaMDS.back.application.exceptions.*;
import com.progra3.javaMDS.back.domain.repositories.GraphRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GraphService {

  private final GraphRepository repository;

  public GraphService(final Integer graphSize) throws InvalidGraphSizeException {
    repository = new GraphRepository(graphSize);
  }

  public List<HashSet<Integer>> getGraph(){
    return repository.getNeighbors();
  }

  public List<HashSet<Integer>> addEdge(
    final Integer x,
    final Integer y
  ) throws
    CircularReferenceException,
    VertexIndexOutOfBoundsException,
    EdgeAlreadyExistException,
    NullVertexException
  {
    repository.addEdge(x, y);
    return repository.getNeighbors();
  }

  public List<HashSet<Integer>> removeEdge(
    final Integer x,
    final Integer y
  ) throws
    CircularReferenceException,
    VertexIndexOutOfBoundsException,
    EdgeDoesNotExistException,
    NullVertexException
  {
    repository.removeEdge(x, y);
    return repository.getNeighbors();
  }

  public Set<Integer> executeMDS() {
    return new MinimumDominatingSetProcedure(repository.getNeighbors()).execute();
  }




}
