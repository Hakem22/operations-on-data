package de.itdesign.application;

import de.itdesign.application.controller.Calculator;
import de.itdesign.application.controller.JsonAdapter;
import de.itdesign.application.model.Operation;
import de.itdesign.application.model.Result;

import java.util.ArrayList;
import java.util.List;

public class JsonCalculator {
  final static String DATA_FILE_ROOT_ELEMENT = "entries";

  public static void main(String[] args) {
    if (args.length == 0) {
      /* Get the data from the json file and save it in an adequate data structure */
      final String DATA_FILE = args[0];
      List dataToManipulate = JsonAdapter.convertJsonDataToMap(DATA_FILE, DATA_FILE_ROOT_ELEMENT);

      /* Get the operations from the json file and save it in an adequate data structure */
      final String OPERATIONS_FILE = args[1];
      List<Operation> operations = JsonAdapter.convertJsonToListOfOperations(OPERATIONS_FILE);

      /* Calculate every operation and save the result in results list */
      List<Result> results = new ArrayList<>();
      operations.forEach(operation -> results.add(Calculator.calculate(operation, dataToManipulate)));

      /* Save the list of result in json file as json array */
      final String OUTPUT_FILE = args[3];
      JsonAdapter.saveResultsInJsonFile(results, OUTPUT_FILE);
    } else {
      System.exit(1);
    }
  }
}
