package de.itdesign.application.model;

import java.util.List;

public class Operation {
  private String name;
  private String function;
  private String filter;
  private List<String> field;

  public Operation(String name, String function, String filter, List<String> field) {
    this.name = name;
    this.function = function;
    this.filter = filter;
    this.field = field;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFunction() {
    return function;
  }

  public void setFunction(String function) {
    this.function = function;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public List<String> getField() {
    return field;
  }

  public void setField(List<String> field) {
    this.field = field;
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Operation operation)
    {
      /* I am ignoring fields since this method is implemented for unit test purpose only. */
      return this.name.equals(operation.getName()) && this.filter.equals(operation.getFilter())
        && this.function.equals(operation.getFunction());
    }
    return false;
  }
}
