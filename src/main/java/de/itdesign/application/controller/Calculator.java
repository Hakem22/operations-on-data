package de.itdesign.application.controller;

import de.itdesign.application.exception.FieldNotFoundException;
import de.itdesign.application.exception.NoNameMatchingException;
import de.itdesign.application.exception.UnknownFunctionException;
import de.itdesign.application.model.enumuration.Function;
import de.itdesign.application.model.Operation;
import de.itdesign.application.model.Result;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Calculator {
  private static final DecimalFormat df = new DecimalFormat("0.00");
  private static final String NO_NAME_MATCHED = "No data entry has matched the name pattern";
  private static final String FIELD_NOT_FOUND = ": field can not be found";
  private static final String UNKNOWN_FUNCTION = "The specified function can not be resolved";

  public static Result calculate(Operation operation, List<Map> entryList) {
    try {
      List<Map> entriesFilteredByName = filterDataByName(operation.getFilter(), entryList);
      List<Double> collectedDataByField = dataCollectorByFiled(operation.getField(), entriesFilteredByName);
      Double value = aggregate(operation.getFunction(), collectedDataByField);

      df.setRoundingMode(RoundingMode.UP);
      return new Result(operation.getName(), df.format(value));
    } catch (NoNameMatchingException | FieldNotFoundException | UnknownFunctionException e) {
      return new Result(operation.getName(), e.getMessage());
    }
  }

  private static List<Map> filterDataByName(String condition, List<Map> entries) {
    List<Map> filteredEntries = entries.stream().filter(entry -> entry.get("name").toString().matches(condition)).collect(Collectors.toList());
    if (filteredEntries.size() == 0) throw new NoNameMatchingException(NO_NAME_MATCHED);
    return filteredEntries;
  }

  private static List<Double> dataCollectorByFiled(List<String> fields, List<Map> entries) {
    return entries.stream().map(entry -> getValueOfDeepestField(fields, entry)).collect(Collectors.toList());
  }

  private static Double getValueOfDeepestField(List<String> fields, Map entry) {
    Object valueOfTheDeepestField = entry;
    for (String field : fields) {
      valueOfTheDeepestField = ((Map) valueOfTheDeepestField).get(field);
      if (valueOfTheDeepestField == null) throw new FieldNotFoundException(field + FIELD_NOT_FOUND);
    }
    return (Double) valueOfTheDeepestField;
  }

  private static double aggregate(String function, List<Double> en) {
    try {
      switch (Function.valueOf(function.toUpperCase())) {
        case MIN -> {
          return Collections.min(en);
        }
        case MAX -> {
          return Collections.max(en);
        }
        case SUM -> {
          return en.stream().mapToDouble(Double::doubleValue).sum();
        }
        case AVERAGE -> {
          OptionalDouble average = en.stream().mapToDouble(Double::doubleValue).average();
          return average.isPresent() ? average.getAsDouble() : 0;
        }
        default -> throw new UnknownFunctionException(UNKNOWN_FUNCTION);
      }
    } catch (IllegalArgumentException e){
      throw new UnknownFunctionException(UNKNOWN_FUNCTION);
    }
  }
}
