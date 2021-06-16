package com.testTask.domain;

import java.util.Objects;

public class Well implements MetaData {

  public void setId(Long id) {

    this.id = id;
  }

  @Override
  public String toString() {

    return "Well{" +
        "id=" + id +
        ", name='" + name + '\'' +
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
    Well well = (Well) object;
    return Objects.equals(id, well.id) && Objects.equals(name, well.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name);
  }

  public void setName(String name) {

    this.name = name;
  }

  public Long getId() {

    return id;
  }

  public String getName() {

    return name;
  }

  public Well() {

  }

  public Well(Long id, String name) {

    this.id = id;
    this.name = name;
  }

  private Long id;
  private String name;
}
