package com.progra3.javaMDS.useCases;

import com.progra3.javaMDS.back.application.exceptions.*;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MinimumDominatingSetTest {


  @Test
  public void validConstructedGraph() throws DisconnectedGraphException, InvalidGraphSizeException, EdgeAlreadyExistException, CircularReferenceException, VertexIndexOutOfBoundsException {
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
      .isInstanceOf(ArrayList.class)
      .hasSize(2);
  }

  @Test
  public void executionWithDisconnectedGraph () throws InvalidGraphSizeException, EdgeAlreadyExistException, CircularReferenceException, VertexIndexOutOfBoundsException {

    final var graphSize = 6;

    final var service = new GraphService(graphSize);

    service.addEdge(5,3);
    service.addEdge(3,4);
    service.addEdge(3,2);
    service.addEdge(2,1);
    service.addEdge(4,1);


    assertThatThrownBy(service::executeMDS)
      .describedAs("it should fail because constructed graph is not connected")
      .isInstanceOf(DisconnectedGraphException.class)
      .hasMessage("Actual graph have internal disconnections and cannot be used by this algorithm");
  }

}
