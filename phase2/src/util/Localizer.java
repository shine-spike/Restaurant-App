package util;

import java.util.ArrayList;
import java.util.HashMap;


public class Localizer {
    private static final HashMap<String, String> localization = new HashMap<>();


    public static String register(String unlocalizedName, String localizedName) {
        return localization.putIfAbsent(unlocalizedName, localizedName);
    }

    public static String localize(String unlocalizedName) {
        return localization.getOrDefault(unlocalizedName, unlocalizedName);
    }

    public static ArrayList<String> localize(ArrayList<String> unlocalizedNames) {
        ArrayList<String> out = new ArrayList<>();

        for(String i: unlocalizedNames){
            out.add(localization.getOrDefault(i, i));
        }

        return out;
    }
}
