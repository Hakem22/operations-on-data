package de.itdesign.application.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import de.itdesign.application.model.DataManipulation;
import de.itdesign.application.model.Operation;
import de.itdesign.application.model.Result;

import java.io.*;
import java.util.List;
import java.util.Map;

public class JsonAdapter {
  public static List<Operation> convertJsonToListOfOperations(String filePath) {
    return new Gson().fromJson(fileReader(filePath), DataManipulation.class).getOperations();
  }

  public static void saveResultsInJsonFile(List<Result> data, String filePath) {
    Gson gson = new Gson();
    try {
      Writer writer = new FileWriter(filePath);
      JsonArray jsonArray = gson.toJsonTree(data).getAsJsonArray();
      gson.toJson(jsonArray, writer);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List convertJsonDataToMap(String filePath, String rootElement) {
    Gson gson = new Gson();
    Map<String, Object> r = gson.fromJson(fileReader(filePath), Map.class);
    String inner = gson.toJson(r.get(rootElement));
    return gson.fromJson(inner, List.class);
  }

  private static BufferedReader fileReader(String filePath) {
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(filePath));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return br;
  }
}
