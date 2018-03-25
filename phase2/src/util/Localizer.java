package util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class only contains static methods that allow the localization of names in the program.
 * Allows for many different languages and easier changing of names.
 */
public class Localizer {
  private static final HashMap<String, String> localization = new HashMap<>();

  /**
   * Registers a given unlocalized name to a given localized name in the localizer.
   *
   * @param unlocalizedName string representing the unlocalized name.
   * @param localizedName string representing the respective localized name.
   * @return the string to which the unlocalized string was previously localized, or {@code null} if
   *     it was not previously localized.
   */
  @Nullable
  public static String register(String unlocalizedName, String localizedName) {
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
  @NotNull
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
  @NotNull
  public static ArrayList<String> localize(ArrayList<String> unlocalizedNames) {
    ArrayList<String> localized = new ArrayList<>();
    for (String unlocalizedName : unlocalizedNames) {
      localized.add(localize(unlocalizedName));
    }
    return localized;
  }
}
