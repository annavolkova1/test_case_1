package com.testTask.dao;

import com.testTask.domain.Equipment;
import java.util.List;
import java.util.Map;

public interface EquipmentDao {

  /**
   * Create equipment on well.
   *
   * @param equipment the equipment instance which will be placed into database
   */
  void createEquipment(Equipment equipment);

  /**
   * Number of equipment on well.
   *
   * @param names the names of well
   * @return the map where key is a well name, value is a amount of equipment on the well
   */
  Map<String, Integer> numberOfEquipment(String names);

  /**
   * Gets all equipments.
   *
   * @return the list of all equipments
   */
  List<Equipment> getAllEquipments();
}
