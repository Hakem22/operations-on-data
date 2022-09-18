package de.itdesign.application.model.enumuration;

public enum Function {
  MIN("min"),
  MAX("max"),
  SUM("sum"),
  AVERAGE("average");

  private String func;

  Function(String func) { this.func = func; }

  public String getFunc() { return func; }

  public void setFunc(String func) { this.func = func; }
}
