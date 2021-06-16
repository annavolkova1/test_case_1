package com.testTask.dao.implementation;

import com.testTask.connection.ConnectionProvider;
import com.testTask.dao.EquipmentDao;
import com.testTask.domain.Equipment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;


public class EquipmentDaoImpl implements EquipmentDao {

  private final static String INSERT_EQUIPMENT = "INSERT INTO equipment (name, Well_id) VALUES (?, ?)";
  private final static String SELECT_ALL_EQUIPMENT = "SELECT id, name, Well_id FROM equipment";

  @Override
  public void createEquipment(@NotNull Equipment equipment) {

    try (Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EQUIPMENT, Statement.RETURN_GENERATED_KEYS)) {
      equipment.setName(generateName());
      preparedStatement.setString(1, equipment.getName());
      preparedStatement.setLong(2, equipment.getWellId());

      int rowsAdded = preparedStatement.executeUpdate();

      if (rowsAdded == 0) {
        throw new SQLException("Creating equipment failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {

        if (generatedKeys.next()) {
          equipment.setId(generatedKeys.getLong(1));
        }
        else {
          throw new SQLException("Creating equipment failed, no ID obtained.");
        }
      }
    }
    catch (SQLException throwable) {
      throwable.printStackTrace();
    }
  }

  @Override
  public Map<String, Integer> numberOfEquipment(String names) {

    Map<String, Integer> map = new HashMap<>();

    String SELECT_NUMBER = "SELECT well.name, COUNT(Well_id) AS Total FROM well "
        + "LEFT JOIN equipment e on well.id = e.Well_id WHERE well.name IN (" + names + ") GROUP BY well.id ";

    try (Connection connection = ConnectionProvider.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_NUMBER)) {

      while (resultSet.next()) {
        String name = resultSet.getString(1);
        int amount = resultSet.getInt(2);
        map.put(name, amount);
      }
    }
    catch (SQLException exception) {
      exception.printStackTrace();
    }

    return map;
  }

  @Override
  public List<Equipment> getAllEquipments() {

    List<Equipment> equipments = new ArrayList<>();
    try (Connection connection = ConnectionProvider.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ALL_EQUIPMENT)) {

      while (resultSet.next()) {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        long wellId = resultSet.getLong(3);
        Equipment equipment = new Equipment(id, name, wellId);

        equipments.add(equipment);
      }
    }
    catch (SQLException throwable) {
      throwable.printStackTrace();
    }

    return equipments;
  }

  /**
   * Generates a name of equipment using latin letters and numbers
   *
   * @return random name of equipment
   */
  @NotNull
  private String generateName() {

    return RandomStringUtils.randomAlphanumeric(10);
  }
}
