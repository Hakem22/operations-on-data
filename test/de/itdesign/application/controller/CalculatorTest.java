package de.itdesign.application.controller;

import de.itdesign.application.model.Operation;
import de.itdesign.application.model.Result;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class CalculatorTest {
  private final List<Map> data = Arrays.asList(
    Map.ofEntries(
      entry("name", "Stuttgart"),
      entry("population", 1774224.09),
      entry("extraField", "ExtraValue" )),
    Map.ofEntries(
      entry("name", "Hamburg"),
      entry("population", 601646.98)),
    Map.ofEntries(
      entry("name", "Mainz"),
      entry("population", 197778.99))
  );

  @Test
  public void testIfExceptionIsThrownWhenMissingField() {
    Operation operation = new Operation("op1", "sum", ".*", List.of("notValidField"));
    Result result = Calculator.calculate(operation, data);

    Assert.assertEquals(result.getValue(), "notValidField: field can not be found");
    Assert.assertEquals(result.getName(), "op1");
  }

  @Test
  public void testIfExceptionIsThrownWhenTheOperationIsUnknown() {
    Operation operation = new Operation("op1", "unknown", ".*", List.of("population"));
    Result result = Calculator.calculate(operation, data);

    Assert.assertEquals(result.getValue(), "The specified function can not be resolved");
    Assert.assertEquals(result.getName(), "op1");
  }

  @Test
  public void testTheSumOperation() {
    Operation operation = new Operation("op1", "sum", ".*", List.of("population"));

    Assert.assertEquals(Calculator.calculate(operation, data).getValue(), "2573650,06");
  }

  @Test
  public void testTheAverageOperation() {
    Operation operation = new Operation("op1", "average", ".*", List.of("population"));

    Assert.assertEquals(Calculator.calculate(operation, data).getValue(), "857883,36");
  }

  @Test
  public void testTheMaximumOperation() {
    Operation operation = new Operation("op1", "max", ".*", List.of("population"));

    Assert.assertEquals(Calculator.calculate(operation, data).getValue(), "1774224,09");
  }

  @Test
  public void testTheMinimumOperation() {
    Operation operation = new Operation("op1", "max", ".*", List.of("population"));
    Result result = Calculator.calculate(operation, data);

    Assert.assertEquals(result.getValue(), "1774224,09");
    Assert.assertEquals(result.getName(), "op1");
  }

  @Test
  public void testNameFiltering() {
    Operation operation = new Operation("op1", "sum", "M.*", List.of("population"));

    Assert.assertEquals(Calculator.calculate(operation, data).getValue(), "197778,99");
  }

  @Test
  public void testNameFilteringReturnEmpty() {
    Operation operation = new Operation("op1", "sum", "k.*", List.of("population"));
    Result result = Calculator.calculate(operation, data);

    Assert.assertEquals(result.getValue(), "No data entry has matched the name pattern");
    Assert.assertEquals(result.getName(), "op1");
  }
}
