package com.testTask.exception;

public class NoDataException extends Exception {

  static {
    System.out.println("Нет данных для выбранных скважин!");
  }
}
