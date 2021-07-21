package com.testTask.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment implements MetaData {

  private Long id;
  private String name;
  private Long wellId;
}
