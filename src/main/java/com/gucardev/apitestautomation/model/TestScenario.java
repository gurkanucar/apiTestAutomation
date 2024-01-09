package com.gucardev.apitestautomation.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestScenario {
  private int id;
  private String scenarioName;
  private String scenarioDescription;
  private String request;
  private String expectedResponse;
  private String url;
  private String method;
  private String incomingResponse;
  private String incomingStatus;
  private boolean success;
  private boolean completed;
}
