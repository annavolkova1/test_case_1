package com.testTask.domain;

import java.util.Objects;

public class Equipment implements MetaData {

  public Equipment(Long id, String name, Long wellId) {

    this.id = id;
    this.name = name;
    this.wellId = wellId;
  }

  @Override
  public String toString() {

    return "Equipment{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", wellId=" + wellId +
        '}';
  }

  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Equipment equipment = (Equipment) object;
    return Objects.equals(id, equipment.id) && Objects.equals(name, equipment.name)
        && Objects.equals(wellId, equipment.wellId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, wellId);
  }

  public Long getWellId() {

    return wellId;
  }

  public void setWellId(Long wellId) {

    this.wellId = wellId;
  }

  public void setId(Long id) {

    this.id = id;
  }

  public void setName(String name) {

    this.name = name;
  }

  public Equipment() {

  }

  public Long getId() {

    return id;
  }

  public String getName() {

    return name;
  }

  private Long id;
  private String name;
  private Long wellId;
}
