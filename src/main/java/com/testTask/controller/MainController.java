package com.testTask.controller;

import com.testTask.domain.Equipment;
import com.testTask.domain.MetaData;
import com.testTask.domain.Well;
import com.testTask.exception.NoDataException;
import com.testTask.exception.WrongOperationException;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.testTask.service.EquipmentService;
import com.testTask.service.WellService;
import com.testTask.service.implementation.EquipmentServiceImpl;
import com.testTask.service.implementation.WellServiceImpl;

public class MainController implements Runnable {

  WellService wellService = new WellServiceImpl();
  EquipmentService equipmentService = new EquipmentServiceImpl();
  Scanner scanner = new Scanner(System.in);
  private static final Logger logger = LogManager.getLogger(MainController.class);

  @Override
  public void run() {

    boolean isValid = true;
    showAvailableOperations();

    while (isValid) {
      int number = Integer.parseInt(scanner.nextLine());

      switch (number) {
        case 1:
          createEquipmentOnWell();
          break;
        case 2:
          displayGeneralInfoAboutEquipment();
          break;
        case 3:
          exportAllDataToXmlFile();
          break;
        default:
          isValid = false;
          try {
            throw new WrongOperationException();
          }
          catch (WrongOperationException woe) {
            logger.error("This is error : " + woe.getMessage(), woe);
          }
      }

      System.out.println("\nХотите продолжить? 1 - да, 2 - нет");
      int contin = Integer.parseInt(scanner.nextLine());

      if (contin == 1) {
        showAvailableOperations();
      }
      else {
        isValid = false;
      }
    }
  }

  /**
   * Creates of N number of equipment on the well
   */
  private void createEquipmentOnWell() {

    System.out.print("Введите количество оборудования: ");
    int numberOfEquipment = Integer.parseInt(scanner.nextLine());
    System.out.println("Список скважин: " + wellService.getWellsName());
    System.out.print("Введите название скважины: ");
    String name = scanner.nextLine();
    Well well = wellService.getWellByName(name);

    if (well == null) {
      well = wellService.createWellByName(name);
    }
    System.out.println("Создаём оборудование");

    for (int i = 0; i < numberOfEquipment; i++) {
      equipmentService.createEquipment(well.getId());
    }
    System.out.println("Done!");
  }

  /**
   * Displays general information about equipment on the well in tabular form
   */
  private void displayGeneralInfoAboutEquipment() {

    Map<String, Integer> map;
    System.out.println("Список скважин: " + wellService.getWellsName());
    System.out.println("Введите названия скважин через пробел или запятую");
    String namesWell = scanner.nextLine();
    String[] names = namesWell.split("[\\s,]");
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < names.length; i++) {

      if (i == names.length - 1) {
        stringBuilder.append("'")
            .append(names[i])
            .append("'");
      }
      else {
        stringBuilder.append("'")
            .append(names[i])
            .append("', ");
      }
    }

    map = equipmentService.numberOfEquipment(stringBuilder.toString());

    if (map.size() == 0) {
      try {
        throw new NoDataException();
      }
      catch (NoDataException exception) {
        logger.error("This is error : " + exception.getMessage(), exception);
      }
    }
    else {
      String divider = "-------------------------------";
      String format = "|%15s|%15s|%n";
      System.out.println(divider);

      for (Map.Entry<String, Integer> entry : map.entrySet()) {
        String key = entry.getKey();
        int value = entry.getValue();
        System.out.format(format, StringUtils.center(key, 15), StringUtils.center(String.valueOf(value), 15));
        System.out.println(divider);
      }
    }
  }

  /**
   * Exports all data to Xml file
   */
  private void exportAllDataToXmlFile() {

    System.out.println("Введите имя файла");
    String fileName = scanner.nextLine();
    String xmlFilePath = fileName + ".xml";
    List<Well> wells = wellService.getAllWells();
    List<Equipment> equipments = equipmentService.getAllEquipments();

    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
      Document document = documentBuilder.newDocument();
      Element root = document.createElement("dbinfo");

      document.appendChild(root);

      for (Well well : wells) {
        Element wellEl = createDomElement(well, document, root, "well");

        for (Equipment equipment : equipments) {

          if (well.getId().equals(equipment.getWellId())) {
            createDomElement(equipment, document, wellEl, "equipment");
          }
        }
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);
      File tmp = new File("./", xmlFilePath);
      StreamResult streamResult = new StreamResult(tmp);

      transformer.transform(domSource, streamResult);

      System.out.println("Done creating XML File");
    }
    catch (ParserConfigurationException | TransformerException exception) {
      logger.error("This is error : " + exception.getMessage(), exception);
    }
  }

  /**
   * Prints available operation
   */
  private void showAvailableOperations() {

    System.out.println("Введите 1 для создания N кол-ва оборудования на скважине");
    System.out.println("Введите 2 для вывода общей информации об оборудовании на скважинах");
    System.out.println("Введите 3 для экспорта всех данных в xml файл");
  }

  /**
   * Creates DOM element
   *
   * @param metaData well or equipment instance
   * @param document document instance
   * @param element parent DOM element
   * @param str well or equipment
   * @return created DOM element
   */
  @NotNull
  private Element createDomElement(@NotNull MetaData metaData, @NotNull Document document, @NotNull Element element,
      String str) {

    Element documentElement = document.createElement(str);
    element.appendChild(documentElement);

    Attr id = document.createAttribute("id");
    id.setValue(String.valueOf(metaData.getId()));
    documentElement.setAttributeNode(id);

    Attr name = document.createAttribute("name");
    name.setValue(metaData.getName());
    documentElement.setAttributeNode(name);

    return documentElement;
  }
}
