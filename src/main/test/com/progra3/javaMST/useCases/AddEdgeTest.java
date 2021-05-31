package com.progra3.javaMST.useCases;

import com.progra3.javaMDS.back.application.exceptions.*;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddEdgeTest {


  private final GraphService service;

  public AddEdgeTest() throws InvalidGraphSizeException { service = new GraphService(5); }

  @Test
  public void validEdgeAddition() throws CircularReferenceException, VertexIndexOutOfBoundsException, EdgeAlreadyExistException {

    final var x = 2;
    final var y = 3;

    assertThat(service.addEdge(x, y))
      .describedAs("It should add edge successfully")
      .isNotNull()
      .isInstanceOf(ArrayList.class)
    .isEqualTo();
  }

  @Test
  public void nullIndexes() {

    final Integer x = null;
    final Integer y = null;

    assertThatThrownBy(() -> service.addEdge(x, y))
      .describedAs("It should fail due to invalid indexes")
      .isInstanceOf(InvalidVertexException.class)
      .hasMessage("Not supported negative nor null vertex: " + x);
  }

  @Test
  public void invalidIndexes() {

    final var x = -4;
    final var y = -2;

    assertThatThrownBy(() -> service.addEdge(x,y))
      .describedAs("It should fail due to invalid indexes")
      .isInstanceOf(InvalidVertexException.class)
      .hasMessage("Not supported negative nor null vertex: " + x);

  }

  @Test
  public void vertexIndexOutOfBounds() {

    final var x = 600;
    final var y = 2;

    assertThatThrownBy(() -> service.addEdge(x,y))
      .describedAs("It should fail due to invalid indexes")
      .isInstanceOf(VertexIndexOutOfBoundsException.class)
      .hasMessage("Vertex out of graph range: " + x);

  }


  @Test
  public void edgeAlreadyExist() throws CircularReferenceException, VertexIndexOutOfBoundsException, EdgeAlreadyExistException {

    final var x = 2;
    final var y = 3;

    service.addEdge(x,y);

    assertThatThrownBy(() -> service.addEdge(x, y))
      .describedAs("It should fail, that edge already exist")
      .isInstanceOf(EdgeAlreadyExistException.class)
      .hasMessage("That edge already exists!");
  }


  @Test
  public void circularEdge() {

    final var x = 2;
    final var y = 3;

    assertThatThrownBy(() -> service.addEdge(x, y))
      .describedAs("It should fail because you can't make a circular edge")
      .isInstanceOf(CircularReferenceException.class)
      .hasMessage("Loops not supported (" + x + ", " + y + ")");
  }



}
