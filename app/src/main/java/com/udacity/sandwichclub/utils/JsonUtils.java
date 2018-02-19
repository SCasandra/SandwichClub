package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /* Sandwich informations */
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        List<String> alsoKnownAsList = null;
        List<String> ingredientsList = null;

        /* Get the JSON object representing the sandwich */
        JSONObject root = new JSONObject(json);

        JSONObject name = root.getJSONObject(NAME);
        String mainName = name.getString(MAIN_NAME);

        JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
        if (alsoKnownAs.length() > 0) {
            alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }
        }

        String placeOfOrigin = root.getString(PLACE_OF_ORIGIN);
        String description = root.getString(DESCRIPTION);
        String image = root.getString(IMAGE);

        JSONArray ingredients = root.getJSONArray(INGREDIENTS);
        if (ingredients.length() > 0) {
            ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }
        }

        /* Create and return Sandwich Object */
        return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
    }
}
