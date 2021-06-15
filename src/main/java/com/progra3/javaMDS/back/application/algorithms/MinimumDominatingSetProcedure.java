package com.progra3.javaMDS.back.application.algorithms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public final class MinimumDominatingSetProcedure {
  private final List<Set<Integer>> graphTopology;
  private final Set<Integer> vertexesLeftToPick;

  public MinimumDominatingSetProcedure(final List<Set<Integer>> graphTopology) {
    this.graphTopology = graphTopology;

    vertexesLeftToPick = Stream.iterate(0, i -> i + 1)
      .limit(graphTopology.size())
      .collect(toSet());

    vertexesLeftToPick.forEach(
      vertexIndex -> graphTopology.get(vertexIndex).add(vertexIndex)
    );
  }

  public Set<Integer> execute() {
    final var algorithmResult = new HashSet<Integer>();

    if(graphHasATrivialMDS()) {
      algorithmResult.add(0);
      return algorithmResult;
    }

    final var vertexesAlreadyInvolved = new HashSet<Integer>();

    findAndAddOrphanVertexes(vertexesAlreadyInvolved, algorithmResult);

    while(vertexesAlreadyInvolved.size() != graphTopology.size()) {
      addVertexBySortingFactor(vertexesAlreadyInvolved, algorithmResult);
    }

    return algorithmResult;
  }

  private Boolean graphHasATrivialMDS() {
    return  graphTopology.size() < 3;
  }

  private void findAndAddOrphanVertexes(
    final HashSet<Integer> vertexesAlreadyInvolved,
    final HashSet<Integer> algorithmResult
  ) {
    graphTopology.stream()
      .filter( vertexNeighbors -> vertexNeighbors.size() == 1 )
      .forEach( vertexNeighbors -> {
        final var indexOf = graphTopology.indexOf(vertexNeighbors);
        algorithmResult.add(indexOf);
        vertexesAlreadyInvolved.add(indexOf);
        vertexesLeftToPick.remove(indexOf);
      });
  }

  private void addVertexBySortingFactor(
    final HashSet<Integer> vertexesAlreadyInvolved,
    final HashSet<Integer> algorithmResult
  ) {

    graphTopology.stream()
      .max(
        comparingDouble(vertex -> {
          final var indexOf = graphTopology.indexOf(vertex);
          return (
            (getVertexNeighborsNotInvolvedMDS(vertex, vertexesAlreadyInvolved).size() + (indexOf * .1) ) *
              (vertexesLeftToPick.contains(indexOf) ? 1 : 0)
          );
        }))
      .ifPresent( selectedVertex -> {
        final var indexOf = graphTopology.indexOf(selectedVertex);
        algorithmResult.add(indexOf);
        vertexesAlreadyInvolved.addAll(selectedVertex);
        vertexesLeftToPick.remove(indexOf);
      });

  }

  private Set<Integer> getVertexNeighborsNotInvolvedMDS(
      final Set<Integer> vertexNeighbors,
      final Set<Integer> vertexesAlreadyInvolved

  ) {
    return vertexNeighbors.stream()
             .filter(not(vertexesAlreadyInvolved::contains))
             .collect(toSet());
  }
}