package org.incubyte;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class TMDBControllerTest {

//  @Client("/tmdb")
  @Inject
  HttpClient httpClient;

  @BeforeEach
  void init() {
    TmdbMockServer.start();
  }

  @AfterEach
  void tearDown() {
    TmdbMockServer.stop();
  }
  @Client("/tmdb")
  @Inject
  TmdbTestOnlyClient tmdbTestOnlyClient;

  @Test
  void should_search_for_people_based_on_query() {
    List<SearchResult> results =
        httpClient
            .toBlocking()
            .retrieve(
                HttpRequest.GET("people?query=tom+cruise"),
                Argument.listOf(SearchResult.class));//client could have been retrived
    assertThat(results).isNotEmpty();
    SearchResult result = results.get(0);
    assertThat(result.getName()).isNotEmpty();
    assertThat(result.getProfilePath()).isNotEmpty();
    assertThat(result.getId()).isEqualTo(500L);
  }

  @Test()
  void should_return_404_if_person_not_found() {
    HttpClientResponseException httpClientResponseException =
        assertThrows(
            HttpClientResponseException.class,
            () -> {
              List<SearchResult> results =
                  httpClient
                      .toBlocking()
                      .retrieve(
                          HttpRequest.GET("people?query=ABC+XYZ"),
                          Argument.listOf(SearchResult.class));
            });
    assertThat(httpClientResponseException.getResponse().code())
        .isEqualTo(HttpResponse.notFound().status().getCode());
  }

  @Test
  void should_return_person_info() {
//    Person person = new Person();
    Person person = httpClient
        .toBlocking()
        .retrieve(
            HttpRequest.GET("people/500"),
            Argument.of(Person.class));
//    TmdbMockServer.verifyFindById(500);

    assertThat(person.getName()).isEqualTo("Tom Cruise");
    assertThat(person.getImage()).isNotEmpty();
    assertThat(person.getGender()).isEqualTo(2);
    assertThat(person.getPlaceOfBirth()).isEqualTo("Syracuse, New York, USA");

  }
}
