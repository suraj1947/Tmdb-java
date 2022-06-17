package org.incubyte;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TVShowDto {

  @JsonProperty("id")
  private int id;
  @JsonProperty("overview")
  private String overview;
  @JsonProperty("first_air_date")
  private String firstAirDate;
  @JsonProperty("name")
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getFirstAirDate() {
    return firstAirDate;
  }

  public void setFirstAirDate(String firstAirDate) {
    this.firstAirDate = firstAirDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
