package org.incubyte;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
  private String name;
  @JsonProperty("profile_path")
  private String image;
  @JsonProperty("age")
  private int age;
  @JsonProperty("gender")
  private String gender;
  @JsonProperty("birthday")
  private String birthday;
  @JsonProperty("place_of_birth")
  private String placeOfBirth;

  public String getPlaceOfBirth() {
    return placeOfBirth;
  }

  public void setPlaceOfBirth(String placeOfBirth) {
    this.placeOfBirth = placeOfBirth;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }
}
