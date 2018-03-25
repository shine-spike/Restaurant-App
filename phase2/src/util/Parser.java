package util;

import controller.Restaurant;
import model.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;;

/**
 * Controls all file input parsing from configuration files and provides an interface for parsing
 * event files.
 *
 * <p>Passes on the parsed information to the respective Restaurant.
 */
public class Parser {
  /** Flags that a line in a file should be ignored. */
  private static final char COMMENT_SYMBOL = '%';
  // The names of all the files we need to access.
  private static final String EMPLOYEE_FILE_NAME = "employees.txt";
  private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
  private static final String MENUS_FILE_NAME = "menus.txt";
  private static final String LOCALE_FILE_NAME = "locale.txt";
  private final Restaurant restaurant;
  // Where to read the files from
  private String filesLocation = "phase2/config/";

  /**
   * Constructs a Parser for a given restaurant.
   *
   * @param restaurant the Restaurant to pass in the information to.
   */
  public Parser(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  /**
   * Changes the location of the files that need to be parsed.
   *
   * @param filesLocation the new location of the files.
   */
  public void setFilesLocation(String filesLocation) {
    this.filesLocation = filesLocation;
  }

  /**
   * Verifies that the given configuration folder exists and creates it on the spot with a
   * notification if not.
   */
  private void verifyConfigurationFolder() {
    // Make sure that the configuration file location exists and
    // notify if it was created right now
    Boolean madeNew = new File(filesLocation).mkdirs();
    if (madeNew) {
      System.out.println("Configuration folder not found. Created a new one.");
    }
  }

  /** Parses all the configuration files needed. */
  public void parseConfiguration() {
    // Make sure the configuration folder exists
    verifyConfigurationFolder();

    // Parse each type of configuration file
    parseLocale();
    parseEmployeeConfiguration();
    parseIngredientConfiguration();
    parseMenuConfiguration();
  }

  /**
   * Helper method for generating a reader for each file, and creating it if it does not exist.
   *
   * @param fileName the name of the file to generate a reader for.
   * @return the reader generated.
   */
  private BufferedReader getReader(String fileName) {
    // Find the file we want to get the reader for
    File file = new File(filesLocation + fileName);

    try {
      // Attempt to create the file and notify if it was created now
      if (file.createNewFile()) {
        System.out.println(file.getName() + " not found. Created a new one.");
      }

      // Return the reader of the file whether or not we just created it
      return new BufferedReader(new FileReader(file));
    } catch (IOException e) {
      System.out.println(file.getName() + " not found and could not be created.");

      return null;
    }
  }

  /**
   * Helper method for pre-parsing a given reader, splitting it up into parts and discarding any
   * lines that have no impact such as comments.
   *
   * @param reader the reader to generate the symbols from.
   * @return the array of symbols representing the different parts of the line read.
   */
  @NotNull
  private String[] preParse(BufferedReader reader) {
    String[] symbols;

    // Keep trying to pre-parse a line until we actually read a line with usable information
    do {
      // Try reading a line and notify if reading failed
      String line = null;
      try {
        line = reader.readLine();
      } catch (IOException e) {
        System.out.println("Reading a line failed.");
      }

      // If we read a line then try to split the line into usable information
      if (line != null) {
        // Make sure the line is not empty and is not a comment before trying to find information
        if (line.equals("") || line.charAt(0) == COMMENT_SYMBOL) {
          symbols = null;
        } else {
          symbols = line.split("\\s+");
        }
      } else {
        return new String[] {};
      }
    } while (symbols == null);

    return symbols;
  }

  /** Parses the employee configuration file. Reads in the names of employees. */
  private void parseEmployeeConfiguration() {
    BufferedReader reader = getReader(EMPLOYEE_FILE_NAME);
    if (reader == null) {
      return;
    }

    String[] symbols;
    while ((symbols = preParse(reader)).length != 0) {
      if (symbols.length != 4) {
        System.out.println(
            "Line in employees file should have four entries, "
                + symbols.length
                + " were given. Skipping.");
        continue;
      }
      restaurant
          .getEmployeeController()
          .registerEmployee(symbols[0], symbols[1], symbols[2], symbols[3]);
    }
  }

  /** Parses the ingredient configuration file. Reads in the names of ingredients. */
  private void parseIngredientConfiguration() {
    BufferedReader reader = getReader(INGREDIENTS_FILE_NAME);
    if (reader == null) {
      return;
    }

    String[] symbols;
    while ((symbols = preParse(reader)).length != 0) {
      if (symbols.length != 3) {
        System.out.println(
            "Line in ingredients file should have three entries, "
                + symbols.length
                + " were given. Skipping.");
        continue;
      }

      try {
        restaurant
            .getInventory()
            .addIngredient(symbols[0], Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
      } catch (NumberFormatException e) {
        System.out.println("Final two entries in a line should be numbers. Skipping.");
      }
    }
  }

  /**
   * Parses the menu configuration file. Reads in the names of menus, their menu items, what they
   * are made of and what additions and subtractions can be added to each.
   */
  private void parseMenuConfiguration() {
    BufferedReader reader = getReader(MENUS_FILE_NAME);
    if (reader == null) {
      return;
    }
    String currentMenu = null;

    String[] symbols;
    while ((symbols = preParse(reader)).length != 0) {
      if (currentMenu != null) {
        if (symbols[0].equals("END")) {
          currentMenu = null;
          continue;
        }

        String currentItem = symbols[0];
        HashMap<Ingredient, Integer> ingredients = new HashMap<>();

        for (String ingredientName : preParse(reader)) {
          Ingredient ingredient = restaurant.getInventory().getIngredient(ingredientName);
          ingredients.put(ingredient, 1);
        }

        for (String additionName : preParse(reader)) {
          Ingredient addition = restaurant.getInventory().getIngredient(additionName);
          ingredients.put(addition, 0);
        }

        restaurant
            .getMenuController()
            .addMenuItem(currentMenu, currentItem, Integer.parseInt(symbols[1]), ingredients);
      } else {
        currentMenu = symbols[1];
        restaurant.getMenuController().addMenu(currentMenu);
      }
    }
  }

  /** Parses the localization file. */
  private void parseLocale() {
    BufferedReader reader = getReader(LOCALE_FILE_NAME);
    if (reader == null) {
      return;
    }

    String[] symbols;
    while ((symbols = preParse(reader)).length != 0) {
      if (symbols.length < 2) {
        System.out.println(
            "Line in localization file should have at least two entries, "
                + symbols.length
                + " were given. Skipping.");
        continue;
      }

      String unlocalizedName = symbols[0];
      StringBuilder localizedName = new StringBuilder(symbols[1]);
      for (int i = 2; i < symbols.length; i++) {
        localizedName.append(" ");
        localizedName.append(symbols[i]);
      }

      if (Localizer.register(unlocalizedName, localizedName.toString()) != null) {
        System.out.println(
            "Unlocalized name \'" + unlocalizedName + "\' was previously declared. Skipping.");
      }
    }
  }
}
