package io.git.movies.popularmovies.contentProvider;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QueryHelper {
    private FavoritesDB dbHelper;

    public boolean dataCheck(String table, String rowAttribute, String fieldValue, Context context) {
        dbHelper = new FavoritesDB(context);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        String Query = "Select * from " + table + " where " + rowAttribute + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
