package com.progra3.javaMST.useCases;

import com.progra3.javaMDS.back.application.exceptions.InvalidGraphSizeException;
import com.progra3.javaMDS.back.domain.services.GraphService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InitializeGraphTest {

  @Test
  public void validGraphInitialization() throws InvalidGraphSizeException {

    final var graphSize = 5;

    assertThat(new GraphService(graphSize))
      .describedAs("It should initialize sucessfully")
      .isInstanceOf(ArrayList.class)
      .isEqualTo();

  }

  @Test
  public void nullGraphSize() {

    final Integer graphSize = null;

    assertThatThrownBy(() -> new GraphService(graphSize))
      .describedAs("It should fail due to null graph size")
      .isInstanceOf(InvalidGraphSizeException.class)
      .hasMessage("Graph size cannot be negative nor null: "+ graphSize);
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
