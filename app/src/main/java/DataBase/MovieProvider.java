package DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static DataBase.MovieContract.CONTENT_AUTHORITY;
import static DataBase.MovieContract.PATH_MOVIE;

public class MovieProvider extends ContentProvider {


    public static final  int  MOVIE_TABLE = 100;

    public static final int  MOVIE_ID = 101;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    long number;

    int deletedRow;

    //Movie sqlite helper
    public MovieHelper help;
    //here we making  reference to a cursor in case we query one of the table in database
    public Cursor cursor;

   SQLiteDatabase database;

    Uri uriReturned;//for insert

    static{

        sURIMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE, MOVIE_TABLE);

        sURIMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIE + "/#", MOVIE_ID);

    }


    @Override
    public boolean onCreate() {

        help = new MovieHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //the match return one of the int code
        int code = sURIMatcher.match(uri);

        //here I am getting a readable database
         database = help.getReadableDatabase();

        switch (code){

            //in case that we are quering the whole table
            case MOVIE_TABLE:

                 cursor  = database.query(MovieContract.MovieEntries.TABLE_NAME, null,
                         null,
                         null,
                         null,
                         null,
                         null);

                 break;

            case MOVIE_ID:

               cursor =  database.query(MovieContract.MovieEntries.TABLE_NAME, null,
                       null,
                       null,
                       null,
                       null,
                       null);

               break;


           default:

               throw new IllegalArgumentException("query is wrong in this case " + uri);

        }

        //this is to notify that anchange has occured
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        //the match return one of the int code
        int code = sURIMatcher.match(uri);

        //here I am getting a writable
        database = help.getWritableDatabase();

        switch (code){

            //we can only insert in the directory
            case MOVIE_TABLE:

               number = database.insert(MovieContract.MovieEntries.TABLE_NAME, null, values);

               if(number > 0){
                   //here the insertion was succesfull

                   uriReturned = ContentUris.withAppendedId(MovieContract.MovieEntries.CONTENT_URI, number);

               }
               else{

                   throw new android.database.SQLException("some went wrong");      //SQLiteException("Invalid operation");
               }

                break;

            default:

                throw new IllegalArgumentException("query is wrong in this case " + uri);

        }

        getContext().getContentResolver().notifyChange(uri,null);

        return uriReturned;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // Get writeable database
        SQLiteDatabase database = help.getWritableDatabase();

        int code = sURIMatcher.match(uri);

        switch (code) {

            case MOVIE_TABLE:
                // Delete all rows that match the selection and selection args
                deletedRow =  database.delete(MovieContract.MovieEntries.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIE_ID:
                // Delete a single row given by the ID in the URI
                selection = MovieContract.MovieEntries._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                deletedRow =  database.delete(MovieContract.MovieEntries.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }//end of switch statement

        getContext().getContentResolver().notifyChange(uri,null);

        return  deletedRow;



    }//end of delete method


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
