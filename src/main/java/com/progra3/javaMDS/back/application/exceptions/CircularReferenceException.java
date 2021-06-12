package com.progra3.javaMDS.back.application.exceptions;

public final class CircularReferenceException extends Exception {
  public CircularReferenceException(final String errorMessage) { super(errorMessage); }
}
