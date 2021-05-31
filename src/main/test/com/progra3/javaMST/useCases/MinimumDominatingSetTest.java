package com.progra3.javaMST.useCases;

import com.progra3.javaMDS.back.application.exceptions.DisconnectedGraphException;
import com.progra3.javaMDS.back.application.exceptions.InvalidGraphSizeException;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MinimumDominatingSetTest {


  @Test
  public void validConstructedGraph() throws DisconnectedGraphException, InvalidGraphSizeException {

    final var service = new GraphService(5);

    assertThat(service.executeMDS()).describedAs(
      "It should create a minimum domination set " +
        "where each remaining vertex is in a neighbour relationship " +
        "with the rest of the vertexes."
      )
      .isInstanceOf(ArrayList.class)
      .hasSize()
      .isEqualTo();
  }

  @Test
  public void executionWithDisconnectedGraph () throws InvalidGraphSizeException {

    final var service = new GraphService(5);


    assertThatThrownBy(service::executeMDS)
      .describedAs("it should fail because constructed graph is not connected")
      .isInstanceOf(DisconnectedGraphException.class)
      .hasMessage("Actual graph have internal disconnections and cannot be used by this algorithm");
  }

}
