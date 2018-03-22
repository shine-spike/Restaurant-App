package util;

import java.util.HashMap;


public class Localizer {
    private static final HashMap<String, String> localization = new HashMap<>();


    public static String register(String unlocalizedName, String localizedName) {
        return localization.putIfAbsent(unlocalizedName, localizedName);
    }

    public static String localize(String unlocalizedName) {
        return localization.getOrDefault(unlocalizedName, unlocalizedName);
    }
}
