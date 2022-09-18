package de.itdesign.application.model;

import com.google.gson.annotations.SerializedName;

public class Result {
  private String name;
  @SerializedName(value = "roundedValue")
  private String value;

  public Result(String name, String roundedValue) {
    this.name = name;
    this.value = roundedValue;
  }

  public String getName() { return name; }

  public String getValue() { return value; }

  public void setName(String name) { this.name = name; }

  public void setValue(String value) { this.value = value; }
}
