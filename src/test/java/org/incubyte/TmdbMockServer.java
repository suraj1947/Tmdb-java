package org.incubyte;



import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.reset;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;


public class TmdbMockServer {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static WireMockServer wireMockServer;

  static {
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    mapper.registerModule(javaTimeModule);
  }

  static void initWireMock() {
    wireMockServer =
        new WireMockServer(
            options()
                .port(9090)
                .notifier(new ConsoleNotifier(true))
                .extensions(new ResponseTemplateTransformer(false)));
    wireMockServer.start();
  }

  public static void startWithoutStubs() {
    if (wireMockServer == null || !wireMockServer.isRunning()) {
      initWireMock();
    }
  }

  public static WireMockServer getServer() {
    return wireMockServer;
  }

  public static void start() {
    if (wireMockServer == null || !wireMockServer.isRunning()) {
      startWithoutStubs();
    }
    reset();
    addGetPeopleByNameEndpoint();
    addGetPeopleByIdEndpoint();
  }

  private static void addGetPeopleByNameEndpoint() {
    wireMockServer.stubFor(
        get(urlMatching("search/person"))
            .withQueryParam("query", WireMock.equalTo("tom"))
            .withHeader("apiKey", WireMock.equalTo("fd6bbf5b30d16dbe410178c8f6c2b0a8"))
            .willReturn(okJson((getBody()))));
  }

  private static void addGetPeopleByIdEndpoint() {
    wireMockServer.stubFor(
        get(urlMatching("person/500"))
            .withQueryParam("apiKey", WireMock.equalTo("fd6bbf5b30d16dbe410178c8f6c2b0a8"))
            .willReturn(okJson((getBody()))));
  }

  private static String getBody() {
    return "{\n"
        + "\t\"name\": \"Tom Cruise\",\n"
        + "\t\"age\": \"0\",\n"
        + "\t\"gender\": \"2\",\n"
        + "\t\"profile_path\": \"/8qBylBsQf4llkGrWR3qAsOtOU8O.jpg\",\n"
        + "\t\"place_of_birth\": \"Syracuse, New York, USA\""
        + "}";
  }

  public static void verifyFindById(int id) {
    wireMockServer.verify(getRequestedFor(
        urlEqualTo("/person/" + id + "?api_key=fd6bbf5b30d16dbe410178c8f6c2b0a8")).withQueryParam(
        "api_key", WireMock.equalTo("fd6bbf5b30d16dbe410178c8f6c2b0a8")));
  }

  public static void stop() {
    if (wireMockServer != null && wireMockServer.isRunning()) {
      wireMockServer.stop();
    }
  }
}

