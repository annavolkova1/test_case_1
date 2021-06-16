package com.testTask.service;

import com.testTask.domain.Well;
import java.util.List;

public interface WellService {

  /**
   * Gets well by name of well.
   *
   * @param name the name of well
   * @return the well by name
   */
  Well getWellByName(String name);

  /**
   * Create new well by name well.
   *
   * @param name the name of the well
   * @return the new well
   */
  Well createWellByName(String name);

  /**
   * Gets wells name.
   *
   * @return the wells name
   */
  List<String> getWellsName();

  /**
   * Gets all wells.
   *
   * @return the list of all wells
   */
  List<Well> getAllWells();
}
