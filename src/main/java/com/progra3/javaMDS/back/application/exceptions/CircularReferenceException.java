package com.progra3.javaMDS.back.application.exceptions;

public class CircularReferenceException extends Exception {
  public CircularReferenceException(final String errorMessage) { super(errorMessage); }
}
