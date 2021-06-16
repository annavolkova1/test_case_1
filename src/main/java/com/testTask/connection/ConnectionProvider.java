package com.testTask.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {

  /**
   * Gets com.testTask.connection with database.
   *
   * @return the com.testTask.connection
   */
  public static Connection getConnection() {

    Connection connection;

    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:C:/testTask/src/main/resources/test.db");

      return connection;
    }
    catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");

    return null;
  }
}
