package com.testTask.service.implementation;

import com.testTask.dao.implementation.WellDaoImpl;
import com.testTask.domain.Well;
import com.testTask.service.WellService;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WellServiceImpl implements WellService {

  private static final Logger logger = LogManager.getLogger(WellServiceImpl.class);

  public WellServiceImpl() {

  }

  WellDaoImpl wellDaoImpl = new WellDaoImpl();

  @Override
  public Well getWellByName(String name) {

    Well well;
    try {
      well = wellDaoImpl.getWellByName(name);

      return well;
    }
    catch (NullPointerException npe) {
      logger.error("This is error : " + npe.getMessage(), npe);
    }

    return null;
  }

  @Override
  public Well createWellByName(String name) {

    Well well = new Well();
    well.setName(name);

    return wellDaoImpl.createWell(well);
  }

  @Override
  public List<String> getWellsName() {

    List<String> wellNames = new ArrayList<>();
    wellDaoImpl.getAllWells().forEach(well -> wellNames.add(well.getName()));

    return wellNames;
  }

  @Override
  public List<Well> getAllWells() {

    return wellDaoImpl.getAllWells();
  }
}
