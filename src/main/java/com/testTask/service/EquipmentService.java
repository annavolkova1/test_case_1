package com.testTask.service;

import com.testTask.domain.Equipment;
import java.util.List;
import java.util.Map;

public interface EquipmentService {

  /**
   * Create new equipment.
   *
   * @param wellId the well id for created equipment
   */
  void createEquipment(long wellId);

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
