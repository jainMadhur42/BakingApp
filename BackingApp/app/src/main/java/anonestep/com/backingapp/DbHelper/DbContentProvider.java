package anonestep.com.backingapp.DbHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Madhur Jain on 7/18/2017.
 */

public class DbContentProvider extends ContentProvider {

    private DbHelper mDbHelper;
    private static final int TABLE_RECIPE = 100;
    private static final int TABLE_INGREDIENTS = 200;

    private static UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_RECIPE_STEPS, TABLE_RECIPE);
        uriMatcher.addURI(DbContract.AUTHORITY, DbContract.PATH_INGREDIENTS_STEPS, TABLE_INGREDIENTS);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = uriMatcher.match(uri);
        SQLiteDatabase mDb = mDbHelper.getReadableDatabase();
        Cursor mCursor = null;
        switch (match) {
            case TABLE_RECIPE:
                mCursor = mDb.query(DbContract.RecipeTable.RECIPE_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder);
                break;
            case TABLE_INGREDIENTS:

                mCursor = mDb.query(DbContract.Ingredients.Ingredients_Table,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }

        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = uriMatcher.match(uri);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
        Uri returnUri = null;
        long id = 0;
        switch (match) {
            case TABLE_RECIPE:
                String selection = DbContract.RecipeTable.RECIPE_NAME + " like ?";
                Cursor cursor = mDb.query(DbContract.RecipeTable.RECIPE_TABLE,
                        null,
                        selection,
                        new String[]{values.getAsString(DbContract.RecipeTable.RECIPE_NAME)},
                        null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int recipe_id = cursor.getInt(cursor.getColumnIndex(DbContract.RecipeTable._ID));
                    mDb.delete(DbContract.RecipeTable.RECIPE_TABLE, DbContract.RecipeTable._ID + " " +
                            "=?", new String[]{recipe_id + " "});
                    
                    mDb.delete(DbContract.Ingredients.Ingredients_Table, DbContract.Ingredients
                            .Ingredients_Recipe_id + " =?", new String[]{recipe_id + " "});


                    Cursor newCursor = mDb.query(DbContract.Ingredients.Ingredients_Table,
                            null, DbContract.Ingredients.Ingredients_Recipe_id + " = ?",
                            new String[]{recipe_id + " "}, null,
                            null, null);

                    Toast.makeText(getContext(), newCursor.getCount() + " Ingredients Count", Toast
                            .LENGTH_SHORT)
                            .show();
                }
                id = mDb.insert(DbContract.RecipeTable.RECIPE_TABLE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(DbContract.RecipeTable.CONTENT_URI, id);
                }
                break;
            case TABLE_INGREDIENTS:
                id = mDb.insert(DbContract.Ingredients.Ingredients_Table, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(DbContract.RecipeTable.CONTENT_URI, id);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unkownm Uri" + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
