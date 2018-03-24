package util;

import model.Ingredient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Reorderer {
  private static final String REORDER_REQUEST_FILE = "phase2/requests.txt";

  /**
   * Prints to file the need to reorder the given ingredient.
   *
   * @param ingredient the ingredient to reorder.
   */
  public static void reorder(Ingredient ingredient, int amount) {
    // Find the file we want to write to
    File file = new File(REORDER_REQUEST_FILE);

    try {
      // Attempt to create the file and notify if it was created now
      if (file.createNewFile()) {
        System.out.println(file.getName() + " not found. Created a new one.");
      }

      // Write out to the file
      PrintWriter out = new PrintWriter(new FileWriter(file));
      out.println("Reorder request for " + amount + " of " + ingredient.getName() + ".");
      out.close();
    } catch (IOException e) {
      System.out.println(file.getName() + " not found and could not be created.");
    }
  }
}
