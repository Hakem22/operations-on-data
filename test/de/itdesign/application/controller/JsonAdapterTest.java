package de.itdesign.application.controller;

import de.itdesign.application.model.Operation;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class JsonAdapterTest {

  /* Mockito could be used to mock the fileReader and bufferReader, but I avoided to add new lib since it was noted in the README */
  @Test
  public void getOperations() throws Exception {
    String operationsAsString = """
      {
        "operations": [
          {
            "name": "important",
            "function": "average",
            "field": [
              "population"
            ],
            "filter": "M.*"
          },
          {
            "name": "information",
            "function": "sum",
            "field": [
              "extendedStatistics",
              "area"
            ],
            "filter": ".*burg"
          }
      ]
      }""";
    File tempFile = File.createTempFile("temp", ".json");
    Files.writeString(tempFile.toPath(), operationsAsString);
    tempFile.deleteOnExit();
    List<Operation> expectedOperations = List.of(
      new Operation("important", "average", "M.*", List.of("population")),
      new Operation("information", "sum", ".*burg", List.of("extendedStatistics", "area"))
    );

    List<Operation> operations = JsonAdapter.convertJsonToListOfOperations(tempFile.getPath());

    Assert.assertEquals(operations.size(), expectedOperations.size());
    Assert.assertEquals(operations.get(1), expectedOperations.get(1));
    Assert.assertEquals(operations.get(0), expectedOperations.get(0));
  }

  @Test
  public void getData() throws IOException {
    String dataAsString = """
      {
        "entries": [
          {
            "name": "Stuttgart",
            "population": 601646
          },
          {
            "name": "Hamburg",
            "population": 1774224,
            "extendedStatistics": {
              "area": 755.264
            }
          }
        ]
      }
      """;
    File tempFile = File.createTempFile("temp", ".json");
    Files.writeString(tempFile.toPath(), dataAsString);
    tempFile.deleteOnExit();
    List expectedData = Arrays.asList(
      Map.ofEntries(
        entry("name", "Stuttgart"),
        entry("population", 601646.0)),
      Map.ofEntries(
        entry("name", "Hamburg"),
        entry("population", 1774224.0),
        entry("extendedStatistics", Map.ofEntries(entry("area", 755.264))))
    );

    List data = JsonAdapter.convertJsonDataToMap(tempFile.getPath(), "entries");

    Assert.assertEquals(data.size(), expectedData.size());
    Assert.assertEquals(data.get(1), expectedData.get(1));
    Assert.assertEquals(data.get(0), expectedData.get(0));
  }
}
