package example.george.mina.themoviedb.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by minageorge on 4/12/18.
 */

public class MovieContract {

    public static final String PROVIDER_AUTH = "example.george.mina.themoviedb";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER_AUTH);
    public static final String PATH_FAV = "favorites";

    public static class FavListEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        public static final String TABLE_NAME = "favList";
        public static final String COL_ID = "id";
        public static final String COL_TITLE = "title";
        public static final String COL_POSTER = "poster";
        public static final String COL_DATE = "date";
        public static final String COL_RATE = "rate";
        public static final String COL_LANGUAGE = "language";
        public static final String COL_OVERVIEW = "overview";
        public static final String COL_BACKDROP = "backdrop";
        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ID + " TEXT NOT NULL, " +
                COL_TITLE + " TEXT NOT NULL, " +
                COL_POSTER + " TEXT NOT NULL, " +
                COL_DATE + " TEXT NOT NULL, " +
                COL_RATE + " TEXT NOT NULL, " +
                COL_BACKDROP + " TEXT NOT NULL, " +
                COL_OVERVIEW + " TEXT NOT NULL, " +
                COL_LANGUAGE + " TEXT NOT NULL " +
                ");";
    }
}
