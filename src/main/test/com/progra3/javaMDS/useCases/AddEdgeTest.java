package com.progra3.javaMDS.useCases;

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
  public void validEdgeAddition() throws
    CircularReferenceException,
    VertexIndexOutOfBoundsException,
    EdgeAlreadyExistException,
    NullVertexException
  {
    final var x = 2;
    final var y = 3;

    final var result = service.addEdge(x, y);

    assertThat(result)
      .describedAs("It should add edge successfully")
      .isNotNull()
      .isNotInstanceOf(Exception.class)
      .isInstanceOf(ArrayList.class);

    assertThat(result.get(x))
      .describedAs("Y should be added to X's neighbors list.")
      .contains(y);

    assertThat(result.get(y))
      .describedAs("X should be added to Y's neighbors list as well.")
      .contains(x);
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
  public void edgeAlreadyExist() throws
    CircularReferenceException,
    VertexIndexOutOfBoundsException,
    EdgeAlreadyExistException,
    NullVertexException
  {

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
