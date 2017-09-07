package id.garnish.android.baking.utils;


import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import id.garnish.android.baking.models.Ingredient;
import id.garnish.android.baking.models.Recipe;
import id.garnish.android.baking.models.Step;

public class NetworkUtils {

    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildUrl() {
        Uri uri = Uri.parse(BASE_URL).buildUpon().build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public static Recipe[] getRecipeDataFromJson(String recipeJsonString) throws JSONException {

        final String TAG_ID = "id";
        final String TAG_NAME = "name";
        final String TAG_INGREDIENTS = "ingredients";
        final String TAG_QUANTITY = "quantity";
        final String TAG_MEASURE = "measure";
        final String TAG_INGREDIENT = "ingredient";
        final String TAG_STEPS = "steps";
        final String TAG_SHORT_DESCRIPTION = "shortDescription";
        final String TAG_DESCRIPTION = "description";
        final String TAG_VIDEO_URL = "videoURL";
        final String TAG_THUMBNAIL_URL = "thumbnailURL";
        final String TAG_SERVINGS = "servings";
        final String TAG_IMAGE = "image";

        JSONArray recipeArray = new JSONArray(recipeJsonString);

        Recipe[] recipes = new Recipe[recipeArray.length()];

        for (int i = 0; i < recipeArray.length(); i++) {
            recipes[i] = new Recipe();

            JSONObject recipeInfo = recipeArray.getJSONObject(i);

            recipes[i].setName(recipeInfo.getString(TAG_NAME));
            recipes[i].setImage(recipeInfo.getString(TAG_IMAGE));
            recipes[i].setServings(recipeInfo.getString(TAG_SERVINGS));

            List<Ingredient> ingredientList = new ArrayList<>();
            List<Step> stepList = new ArrayList<>();

            JSONArray ingredientArray = new JSONArray(recipeInfo.getString(TAG_INGREDIENTS));
            Ingredient[] ingredients = new Ingredient[ingredientArray.length()];

            for (int j = 0; j < ingredientArray.length(); j++) {
                ingredients[j] = new Ingredient();

                JSONObject ingredientInfo = ingredientArray.getJSONObject(j);

                ingredients[j].setQuantity(ingredientInfo.getLong(TAG_QUANTITY));
                ingredients[j].setMeasure(ingredientInfo.getString(TAG_MEASURE));
                ingredients[j].setIngredient(ingredientInfo.getString(TAG_INGREDIENT));

                ingredientList.add(ingredients[j]);
            }

            recipes[i].setIngredients(ingredientList);

            JSONArray stepArray = new JSONArray(recipeInfo.getString(TAG_STEPS));
            Step[] steps = new Step[stepArray.length()];

            for (int k = 0; k < stepArray.length(); k++) {
                steps[k] = new Step();

                JSONObject stepInfo = stepArray.getJSONObject(k);

                steps[k].setId(stepInfo.getInt(TAG_ID));
                steps[k].setShortDescription(stepInfo.getString(TAG_SHORT_DESCRIPTION));
                steps[k].setDescription(stepInfo.getString(TAG_DESCRIPTION));
                steps[k].setVideoURL(stepInfo.getString(TAG_VIDEO_URL));
                steps[k].setThumbnailURL(stepInfo.getString(TAG_THUMBNAIL_URL));

                stepList.add(steps[k]);
            }

            recipes[i].setSteps(stepList);

        }

        return recipes;
    }
}
