package org.incubyte;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Singleton
public class PeopleService {

  private final TmdbClient tmdbClient;

  @Value("${tmdb.api-key}")
  private String apiKey;

  public PeopleService(TmdbClient tmbdClient) {
    this.tmdbClient = tmbdClient;
  }

  public Optional<List<SearchResult>> searchByName(String name) throws NoSuchFieldException {
    Optional<Page> result = tmdbClient.searchByName(name, apiKey);
    Page page;
    if (result.isPresent()) {
      page = result.get();
    } else {
      throw new NoSuchFieldException("No result found");
    }
    List<SearchResult> results = page.getResults();
    if (results.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(results);
  }

  public Optional<Person> getById(int id) throws NoSuchFieldException {
    Optional<Person> byId = this.tmdbClient.getById(id, apiKey);
    Person person;
    if (byId.isPresent()) {
      person = byId.get();
    } else {
      throw new NoSuchFieldException("No result found");
    }
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date birthDate = format.parse(person.getBirthday());
      int year = birthDate.getYear();
      long millis = Clock.systemDefaultZone().millis();
      int thisYear = new Date(millis).getYear();
      person.setAge(thisYear - year);
    } catch (ParseException e) {
     throw new RuntimeException("No Proper Date");
    }
    return byId;
  }
}
