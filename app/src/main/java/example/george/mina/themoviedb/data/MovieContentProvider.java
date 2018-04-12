package example.george.mina.themoviedb.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by minageorge on 4/12/18.
 */

public class MovieContentProvider extends ContentProvider {

    private static final int FAVORITES = 100;
    private static final int FAVORITES_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = MovieContentProvider.class.getSimpleName();
    private MovieHelper movieHelper = null;

    public static UriMatcher buildUriMatcher() {

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.PROVIDER_AUTH, MovieContract.PATH_FAV, FAVORITES);
        matcher.addURI(MovieContract.PROVIDER_AUTH, MovieContract.PATH_FAV + "/*", FAVORITES_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = sUriMatcher.match(uri);
        Cursor mCursor;
        final SQLiteDatabase database = movieHelper.getReadableDatabase();

        switch (match) {
            case FAVORITES:
                mCursor = database.query(MovieContract.FavListEntry.TABLE_NAME
                        , projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVORITES_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = MovieContract.FavListEntry.COL_ID + "=?";
                String mSelectionArgs[] = new String[]{id};

                mCursor = database.query(MovieContract.FavListEntry.TABLE_NAME
                        , projection, mSelection, mSelectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }

        mCursor.setNotificationUri(getContext().getContentResolver(), uri);

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
        final SQLiteDatabase database = movieHelper.getWritableDatabase();
        Long rowId = database.insert(MovieContract.FavListEntry.TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri uri1 = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(uri1, null);
            Log.d(TAG, String.valueOf(rowId));
            return uri1;
        }
        throw new SQLException("failed to insert record" + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        final SQLiteDatabase database = movieHelper.getWritableDatabase();
        int deleted = 0;
        switch (match) {
            case FAVORITES:
                deleted = database.delete(MovieContract.FavListEntry.TABLE_NAME, null, null);
                break;
            case FAVORITES_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = MovieContract.FavListEntry.COL_ID + "=?";
                String mSelectionArgs[] = new String[]{id};
                deleted = database.delete(MovieContract.FavListEntry.TABLE_NAME, mSelection, mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
        if (deleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
