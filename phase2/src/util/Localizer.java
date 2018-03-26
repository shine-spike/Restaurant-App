package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class only contains static methods that allow the localization of names in the program.
 * Allows for many different languages and easier changing of names.
 */
public class Localizer {
  private static final String LOCALE_LOCATION = "phase2/config/";
  private static final String LOCALE_NAME = "locale.txt";
  private static final char COMMENT_SYMBOL = '%';

  private static final HashMap<String, String> localization = new HashMap<>();

  /**
   * Registers a given unlocalized name to a given localized name in the localizer.
   *
   * @param unlocalizedName string representing the unlocalized name.
   * @param localizedName string representing the respective localized name.
   * @return the string to which the unlocalized string was previously localized, or {@code null} if
   *     it was not previously localized.
   */
  private static String register(String unlocalizedName, String localizedName) {
    return localization.putIfAbsent(unlocalizedName, localizedName);
  }

  /**
   * Localizes the given unlocalized name to its registered localization. If no localization exists
   * returns the unlocalized name.
   *
   * @param unlocalizedName the string representing the unlocalized name.
   * @return the respective localized name or the given unlocalized name if the name has not been
   *     localized.
   */
  public static String localize(String unlocalizedName) {
    return localization.getOrDefault(unlocalizedName, unlocalizedName);
  }

  /**
   * Localizes the given list of unlocalized names to their registered localizations. If no
   * localization exists for any name returns the unlocalized name for that entry.
   *
   * @param unlocalizedNames the string representing the unlocalized name.
   * @return a list of the respective localized names.
   */
  public static ArrayList<String> localize(ArrayList<String> unlocalizedNames) {
    ArrayList<String> localized = new ArrayList<>();
    for (String unlocalizedName : unlocalizedNames) {
      localized.add(localize(unlocalizedName));
    }
    return localized;
  }

  /** Parses the localization file and registers unlocalized names. */
  public static void parseLocale() {
    File file = new File(LOCALE_LOCATION + LOCALE_NAME);
    if (!file.exists()) {
      // TODO: remove print
      System.out.println("No localization file found.");
      return;
    }

    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(file));
    } catch (IOException e) {
      // TODO: remove print
      System.out.println("Something went wrong.");
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

  /**
   * Helper method for pre-parsing a given reader, splitting it up into parts and discarding any
   * lines that have no impact such as comments.
   *
   * @param reader the reader to generate the symbols from.
   * @return the array of symbols representing the different parts of the line read.
   */
  private static String[] preParse(BufferedReader reader) {
    String[] symbols;

    // Keep trying to pre-parse a line until we actually read a line with usable information
    do {
      // Try reading a line and notify if reading failed
      String line = null;
      try {
        line = reader.readLine();
      } catch (IOException e) {
        // TODO: remove print
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
}
