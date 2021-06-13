package com.progra3.javaMDS.front.Utils;

import java.util.Objects;

public class Edge {

  private final Integer x;
  private final Integer y;


  public Edge(final Integer x, final Integer y) {
    this.x = x;
    this.y = y;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  @Override
  public String toString() {
    return "Edge{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Edge edge = (Edge) o;
    return x.equals(edge.x) && y.equals(edge.y) ||
        x.equals(edge.y) && y.equals(edge.x);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}

