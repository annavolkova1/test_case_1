package com.testTask.service.implementation;

import com.testTask.dao.implementation.WellDaoImpl;
import com.testTask.domain.Well;
import com.testTask.service.WellService;
import java.util.ArrayList;
import java.util.List;

public class WellServiceImpl implements WellService {

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
      npe.printStackTrace();
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
