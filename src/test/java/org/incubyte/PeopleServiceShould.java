package org.incubyte;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeopleServiceShould {

  private final Clock clock =
      Clock.fixed(
          ZonedDateTime.parse("2021-10-25T00:00:00.000+09:00[Asia/Seoul]").toInstant(),
          ZoneId.of("Asia/Seoul"));
  TmdbClient tmdbClient;
  Page page;
  Page page2;
  Person person;
  PeopleService peopleService;

  @BeforeEach
  void init() {
    tmdbClient = mock(TmdbClient.class);
    peopleService = new PeopleService(tmdbClient);
    page = new Page();

    SearchResult searchResult1 = new SearchResult();
    searchResult1.setName("Tom Cruise");
    searchResult1.setProfilePath("/Abc");
    SearchResult searchResult2 = new SearchResult();
    searchResult2.setName("Tom Cruise123");
    searchResult2.setProfilePath("/xyz");

    List<SearchResult> allResults = new ArrayList<>();
    allResults.add(searchResult1);
    allResults.add(searchResult2);
    page.setResults(allResults);

    page2 = new Page();
    page2.setResults(new ArrayList<>());

    person = new Person();
    person.setAge(25);
    person.setName("Hero");
    person.setBirthday("1997-06-15");
    person.setPlaceOfBirth("Road");
    person.setImage("Some Image");
    person.setGender("M");
  }

  @Test
  void invoke_http_client() throws NoSuchFieldException {
    when(tmdbClient.searchByName("tom cruise", null)).thenReturn(Optional.of(page));
    peopleService.searchByName("tom cruise");
    verify(tmdbClient).searchByName("tom cruise", null);
  }

  @Test
  void return_empty_optional_when_no_results_found() throws NoSuchFieldException {
    when(tmdbClient.searchByName("abc xyz", null)).thenReturn(Optional.of(page2));
    Optional<List<SearchResult>> results = peopleService.searchByName("abc xyz");
    assertThat(results).isEmpty();
  }

  @Test
  void return_person_information_by_id() throws NoSuchFieldException {
    when(tmdbClient.getById(500, null)).thenReturn(Optional.of(person));
    Optional<Person> result = peopleService.getById(500);
    assertThat(result).isPresent();
    Person actualPerson = result.get();
    Assertions.assertEquals(25,actualPerson.getAge());
    Assertions.assertEquals("Hero",actualPerson.getName());
    Assertions.assertEquals("1997-06-15",actualPerson.getBirthday());
    Assertions.assertEquals("Some Image",actualPerson.getImage());
    Assertions.assertEquals("M",actualPerson.getGender());

    verify(tmdbClient).getById(500, null);
  }
}