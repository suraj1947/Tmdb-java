package org.incubyte;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import java.util.Optional;

@Client("https://api.themoviedb.org/3/")
public interface TmdbClient {

  @Get("search/person{?query}{&api_key}")
  Optional<Page> searchByName(@QueryValue String query, @QueryValue("api_key") String apikey);

  @Get("person/{id}{?api_key}")
  Optional<Person> getById(int id, @QueryValue("api_key") String apikey);
}
