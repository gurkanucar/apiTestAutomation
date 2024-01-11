package com.gucardev.apitestautomation.model;

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
  private String checkOnlyField;

  private boolean success;
  private boolean completed;

  public TestScenario(
      int id,
      String scenarioName,
      String scenarioDescription,
      String request,
      String expectedResponse,
      String url,
      String method,
      String incomingResponse,
      String incomingStatus,
      String checkOnlyField,
      boolean success,
      boolean completed) {
    this.id = id;
    this.scenarioName = scenarioName;
    this.scenarioDescription = scenarioDescription;
    this.request = request;
    this.expectedResponse = expectedResponse;
    this.url = url;
    this.method = method;
    this.incomingResponse = incomingResponse;
    this.incomingStatus = incomingStatus;
    this.checkOnlyField = checkOnlyField;
    this.success = success;
    this.completed = completed;
  }

  public String getCheckOnlyField() {
    return checkOnlyField;
  }

  public void setCheckOnlyField(String checkOnlyField) {
    this.checkOnlyField = checkOnlyField;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getScenarioName() {
    return scenarioName;
  }

  public void setScenarioName(String scenarioName) {
    this.scenarioName = scenarioName;
  }

  public String getScenarioDescription() {
    return scenarioDescription;
  }

  public void setScenarioDescription(String scenarioDescription) {
    this.scenarioDescription = scenarioDescription;
  }

  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public String getExpectedResponse() {
    return expectedResponse;
  }

  public void setExpectedResponse(String expectedResponse) {
    this.expectedResponse = expectedResponse;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getIncomingResponse() {
    return incomingResponse;
  }

  public void setIncomingResponse(String incomingResponse) {
    this.incomingResponse = incomingResponse;
  }

  public String getIncomingStatus() {
    return incomingStatus;
  }

  public void setIncomingStatus(String incomingStatus) {
    this.incomingStatus = incomingStatus;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
