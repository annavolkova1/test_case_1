package com.testTask.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionProvider {

  private static final Logger logger = LogManager.getLogger(ConnectionProvider.class);

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
    catch (Exception exception) {
      logger.error(exception.getClass().getName() + ": " + exception.getMessage());

      System.exit(0);
    }
    System.out.println("Opened database successfully");

    return null;
  }
}
