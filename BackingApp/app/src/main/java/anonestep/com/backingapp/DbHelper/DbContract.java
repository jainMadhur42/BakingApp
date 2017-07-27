package anonestep.com.backingapp.DbHelper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Madhur Jain on 7/18/2017.
 */

public class DbContract {

    public static final String AUTHORITY = "anonestep.com.backingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_RECIPE_STEPS = "recipe_name";
    public static final String PATH_INGREDIENTS_STEPS = "ingredients_name";


    public DbContract() {

    }


    public static class RecipeTable implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE_STEPS).build();
        public static final String RECIPE_TABLE = "recipe_table";
        public static final String RECIPE_NAME = "recipe_name";
        public static final String RECIPE_TIME_STAMP = "time_stamp";
    }

    public static class Ingredients implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS_STEPS).build();
        public static final String Ingredients_Table = "ingredients_table";
        public static final String Ingredients_Recipe_id = "ingredients_recipe_id";
        public static final String Ingredients_Name = "ingredients_name";
        public static final String Ingredients_measure = "ingredients_measure";
        public static final String Ingredients_quantity = "ingredients_quantity";
    }

}
