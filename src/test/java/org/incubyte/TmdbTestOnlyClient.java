package org.incubyte;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import java.util.Optional;

@Client("/tmdb")
public interface TmdbTestOnlyClient {

  @Get
  Optional<Page> searchByName(@QueryValue String query);

  @Get("/{id}")
  Optional<Person> getById(int id);
}
