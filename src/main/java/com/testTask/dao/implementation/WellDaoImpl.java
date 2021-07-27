package com.testTask.dao.implementation;

import com.testTask.connection.ConnectionProvider;
import com.testTask.dao.WellDao;
import com.testTask.domain.Well;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class WellDaoImpl implements WellDao {

  private static final String SELECT_ALL_WELL = "SELECT id, name FROM well";
  private static final String SELECT_BY_NAME = "SELECT id, name FROM well WHERE name = ?";
  private static final String INSERT_WELL = "INSERT INTO well (name) VALUES (?)";
  private static final Logger logger = LogManager.getLogger(WellDaoImpl.class);

  @Override
  public List<Well> getAllWells() {

    List<Well> wells = new ArrayList<>();
    try (Connection connection = ConnectionProvider.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ALL_WELL)) {

      while (resultSet.next()) {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        Well well = new Well(id, name);

        wells.add(well);
      }
    }
    catch (SQLException throwable) {
      logger.error("This is error : " + throwable.getMessage(), throwable);
    }

    return wells;
  }

  @Override
  public Well createWell(@NotNull Well well) {

    try (Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WELL,
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, well.getName());

      int rowsAdded = preparedStatement.executeUpdate();

      if (rowsAdded == 0) {
        throw new SQLException("Creating well failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

        if (generatedKeys.next()) {
          well.setId(generatedKeys.getLong(1));
        }
        else {
          throw new SQLException("Creating well failed, no ID obtained.");
        }
      }

      return well;
    }
    catch (SQLException throwable) {
      logger.error("This is error : " + throwable.getMessage(), throwable);
    }

    return null;
  }

  @Override
  public Well getWellByName(String name) {

    try (Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {
      preparedStatement.setString(1, name);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        return extractWellFromResultSet(resultSet);
      }
    }
    catch (SQLException throwable) {
      logger.error("This is error : " + throwable.getMessage(), throwable);
    }

    return null;
  }

  /**
   * Extracts well from ResultSet
   *
   * @param resultSet that contains information about well
   * @return well
   */
  private Well extractWellFromResultSet(@NotNull ResultSet resultSet) {

    Well well = new Well();
    try {
      long id = resultSet.getLong(1);
      String name = resultSet.getString(2);
      well = new Well(id, name);
    }
    catch (SQLException throwable) {
      logger.error("This is error : ", throwable);
    }

    return well;
  }
}
