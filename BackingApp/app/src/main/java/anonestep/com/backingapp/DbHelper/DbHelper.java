package anonestep.com.backingapp.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Madhur Jain on 7/18/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String Name = "recipe_db";
    private static final int DB_VERSION = 5;
    Context context;

    private static final String CREATE_RECIPE_TABLE =
            "CREATE TABLE " + DbContract.RecipeTable.RECIPE_TABLE + " ("
                    + DbContract.RecipeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DbContract.RecipeTable.RECIPE_TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,"
                    + DbContract.RecipeTable.RECIPE_NAME + " TEXT )";

    private static final String CREATE_INGREDIENTS_TABLE =
            "CREATE TABLE " + DbContract.Ingredients.Ingredients_Table + " ("
                    + DbContract.Ingredients._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DbContract.Ingredients.Ingredients_Recipe_id + " INTEGER, "
                    + DbContract.Ingredients.Ingredients_Name + " TEXT, "
                    + DbContract.Ingredients.Ingredients_measure + " TEXT,"
                    + DbContract.Ingredients.Ingredients_quantity + " REAL, "
                    + "FOREIGN KEY (" + DbContract.Ingredients.Ingredients_Recipe_id + ") REFERENCES "
                    + DbContract.RecipeTable.RECIPE_TABLE + " (" + DbContract.RecipeTable._ID + "));";


    public DbHelper(Context context) {
        super(context, Name, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.RecipeTable.RECIPE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.Ingredients.Ingredients_Table);

        onCreate(db);
    }
}
