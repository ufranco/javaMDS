package com.progra3.javaMDS.useCases;

import com.progra3.javaMDS.back.application.exceptions.*;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class MinimumDominatingSetTest {

  @Test
  public void executionWithConnectedGraph() throws
    InvalidGraphSizeException,
    EdgeAlreadyExistException,
    CircularReferenceException,
    VertexIndexOutOfBoundsException,
    NullVertexException
  {
    final var graphSize = 6;
    final var service = new GraphService(graphSize);

    service.addEdge(5,3);
    service.addEdge(3,4);
    service.addEdge(3,2);
    service.addEdge(2,1);
    service.addEdge(4,0);
    service.addEdge(4,1);
    service.addEdge(1,0);

    final var result = service.executeMDS();

    assertThat(result).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbour relationship " +
        "with the rest of the vertexes."
      )
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(HashSet.class)
      .hasSize(2);
  }

  @Test
  public void partiallyDisconnectedGraph() throws
    InvalidGraphSizeException,
    EdgeAlreadyExistException,
    CircularReferenceException,
    VertexIndexOutOfBoundsException,
    NullVertexException
  {
    final var graphSize = 6;
    final var service = new GraphService(graphSize);

    service.addEdge(5,3);
    service.addEdge(3,4);
    service.addEdge(3,2);
    service.addEdge(2,1);
    service.addEdge(4,1);

    final var result = service.executeMDS();

    assertThat(result).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbor relationship " +
        "with the rest of the vertexes."
    )
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(HashSet.class)
      .hasSize(3);

  }

  @Test
  public void totallyDisconectedGraph() throws InvalidGraphSizeException {

    final var graphSize = 6;
    final var service = new GraphService(graphSize);

    final var result = service.executeMDS();

    assertThat(result).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbor relationship " +
        "with the rest of the vertexes."
    )
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(HashSet.class)
      .hasSize(graphSize);

  }

  @Test
  public void monoVertexGraph() throws InvalidGraphSizeException {

    final var graphSize = 1;
    final var service = new GraphService(graphSize);

    final var result = service.executeMDS();

    assertThat(result).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbor relationship " +
        "with the rest of the vertexes."
    )
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(HashSet.class)
      .hasSize(graphSize);

  }

  @Test
  public void dualVertexGraph() throws InvalidGraphSizeException {

    final var graphSize = 2;
    final var service = new GraphService(graphSize);

    final var result = service.executeMDS();

    assertThat(result).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbor relationship " +
        "with the rest of the vertexes."
    )
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(HashSet.class)
      .hasSize(graphSize);

  }

  @Test
  public void originalGraph_shouldNotBeModified() throws InvalidGraphSizeException, NullVertexException, EdgeAlreadyExistException, CircularReferenceException, VertexIndexOutOfBoundsException {
    final var graphSize = 6;
    final var service = new GraphService(graphSize);

    service.addEdge(5,3);
    service.addEdge(3,4);
    service.addEdge(3,2);
    service.addEdge(2,1);
    service.addEdge(4,0);
    service.addEdge(4,1);
    service.addEdge(1,0);

    final var originalGraph = service.getGraph();
    final var result = service.executeMDS();

    assertThat(result).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbour relationship " +
        "with the rest of the vertexes."
    )
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(HashSet.class)
      .hasSize(2);

    assertThat(originalGraph.get(0))
      .describedAs("it should not be modified by the algorithm")
      .hasSize(2);

    assertThat(originalGraph.get(5))
      .describedAs("it should not be modified by the algorithm")
      .hasSize(1);

  }



}
