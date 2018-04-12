package example.george.mina.themoviedb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by minageorge on 4/12/18.
 */

public class MovieHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "themovie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieContract.FavListEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieContract.FavListEntry.SQL_DROP_TABLE);
        onCreate(db);
    }
}
