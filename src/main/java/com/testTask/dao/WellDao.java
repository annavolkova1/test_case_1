package com.testTask.dao;

import com.testTask.domain.Well;
import java.util.List;

public interface WellDao {

  /**
   * Gets all wells.
   *
   * @return the list of all wells
   */
  List<Well> getAllWells();

  /**
   * Create new well.
   *
   * @param well the well instance
   * @return the new well
   */
  Well createWell(Well well);

  /**
   * Gets well by name of well.
   *
   * @param name the name of the well
   * @return the well by name
   */
  Well getWellByName(String name);
}
