package org.incubyte;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
 class TMDBControllerShould {
  PeopleService peopleService;
  @BeforeEach
  void init(){
    peopleService = mock(PeopleService.class);
  }

  @Test
   void invoke_service_to_retrieve_search_results() throws NoSuchFieldException {
    TmdbController peopleController = new TmdbController(peopleService);
    Optional<List<SearchResult>> results = peopleController.searchByName("tom cruise");
    verify(peopleService).searchByName("tom cruise");
  }

  @Test
   void invoke_service_to_get_person_by_id() throws NoSuchFieldException {
    TmdbController peopleController = new TmdbController(peopleService);
    Optional<Person> person = peopleController.getById(500);
    verify(peopleService).getById(500);
  }
}
