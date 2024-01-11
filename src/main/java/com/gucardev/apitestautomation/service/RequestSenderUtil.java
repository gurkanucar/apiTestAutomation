package com.gucardev.apitestautomation.service;

import com.gucardev.apitestautomation.model.TestScenario;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class RequestSenderUtil {

  private HttpClient client;

  public RequestSenderUtil() {
    this.client = HttpClient.newHttpClient();
  }

  public HttpResponse<String> sendRequest(TestScenario scenario) throws Exception {
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(new URI(scenario.getUrl()))
            .method(
                scenario.getMethod().toUpperCase(),
                HttpRequest.BodyPublishers.ofString(scenario.getRequest(), StandardCharsets.UTF_8))
            .header("Content-Type", "application/json")
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (JsonUtil.simplify(response.body())
        .equals(JsonUtil.simplify(scenario.getExpectedResponse()))) {
      scenario.setSuccess(true);
    }

    scenario.setIncomingResponse(response.body());
    scenario.setCompleted(true);
    return response;
  }
}
