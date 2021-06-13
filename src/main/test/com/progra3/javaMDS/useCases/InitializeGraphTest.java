package com.progra3.javaMDS.useCases;

import com.progra3.javaMDS.back.application.exceptions.InvalidGraphSizeException;
import com.progra3.javaMDS.back.domain.repositories.GraphRepository;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InitializeGraphTest {

  @Test
  public void validGraphInitialization() throws InvalidGraphSizeException {

    final var graphSize = 5;

    final var result = new GraphRepository(graphSize);

    assertThat(result)
      .describedAs("It should initialize sucessfully")
      .isInstanceOf(ArrayList.class);

    assertThat(result.getNeighbors())
      .describedAs("It should contain 5 list, one per vertex")
      .hasSize(graphSize);

  }

  @Test
  public void nullGraphSize() {
    assertThatThrownBy(() -> new GraphService(null))
      .describedAs("It should fail due to null graph size")
      .isInstanceOf(InvalidGraphSizeException.class)
      .hasMessage("Graph size cannot be negative nor null: "+ null);
  }

  @Test
  public void invalidGraphSize() {

    final var graphSize = 0;

    assertThatThrownBy(() -> new GraphService(graphSize))
      .describedAs("It should fail due to null graph size")
      .isInstanceOf(InvalidGraphSizeException.class)
      .hasMessage("Graph size cannot be negative nor null: " + graphSize);
  }
}
