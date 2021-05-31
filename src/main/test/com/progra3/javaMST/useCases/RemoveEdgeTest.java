package com.progra3.javaMST.useCases;

import com.progra3.javaMDS.back.application.exceptions.*;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RemoveEdgeTest {

  private final GraphService service;

  public RemoveEdgeTest() throws InvalidGraphSizeException { this.service = new GraphService(5); }

  @Test
  public void validEdgeRemove() throws CircularReferenceException, VertexIndexOutOfBoundsException, EdgeAlreadyExistException, EdgeDoesNotExistException {

    service.addEdge(2,3);

    assertThat(service.removeEdge(2, 3))
      .describedAs("It should remove edge successfully")
      .isNotNull()
      .isInstanceOf(ArrayList.class)
      .isEqualTo();

  }

  @Test
  public void nullIndexes() {
    assertThatThrownBy(() -> service.removeEdge(null,null))
      .describedAs("It should fail due to null indexes")
      .isInstanceOf(InvalidVertexException.class)
      .hasMessage("not supported negative nor null vertex: null");
  }

  @Test
  public void invalidIndexes() {
    final var x = -4;
    final var y = -2;

    assertThatThrownBy(() -> service.removeEdge(x,y))
      .describedAs("It should fail due to invalid indexes")
      .isInstanceOf(InvalidVertexException.class)
      .hasMessage("not supported negative nor null vertex: " + x);

  }


  @Test
  public void edgeDoesNotExist() {

    final var x = 5;
    final var y = 4;

    assertThatThrownBy(() -> service.removeEdge(x, y))
      .describedAs("it should fail because that edge does not exists")
      .isInstanceOf(EdgeDoesNotExistException.class)
      .hasMessage("Edge to be removed does no exists");

  }
}
