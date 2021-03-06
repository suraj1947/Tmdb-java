package org.incubyte;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import java.util.List;
import java.util.Optional;

@Controller("people")
public class TmdbController {
  private final PeopleService peopleService;

  public TmdbController(PeopleService peopleService) {
    this.peopleService = peopleService;
  }

  @Get
  public Optional<List<SearchResult>> searchByName(@QueryValue String query)
       {
    return peopleService.searchByName(query);
  }

  @Get("/{id}")
  public Optional<Person> getById(int id) {
    return peopleService.getById(id);
  }

  @Get("/tv")
  public Optional<TVShowDto> getTvShowsByName(@QueryValue String query) {
    return peopleService.getTvShowsByName(query);
  }

}
