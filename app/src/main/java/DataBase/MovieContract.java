package DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    //setting up the authority
    public static final String CONTENT_AUTHORITY = "com.example.android.movieapp";

    //concatenating the scheme and the authority to and parsing it to make the base URI

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //next we get the path of each table in the contract

    public static final String PATH_MOVIE = "movie_database";

    //finally I complete the content URI

   // public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIE);


    //This statement create the table
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieEntries.TABLE_NAME + " (" +
                    MovieEntries._ID + " INTEGER PRIMARY KEY," +
                    MovieEntries.COLUMN_NAME_RELEASE + " TEXT," +
                    MovieEntries.COLUMN_NAME_PLOT+ " TEXT," +
                    MovieEntries.COLUMN_NAME_VIDEO_LINK+ " TEXT," +
                    MovieEntries.COLUMN_NAME_RATE + " TEXT," +
                    MovieEntries.COLUMN_NAME_ID + " TEXT)";


    private MovieContract(){

        //empty costructor
    }



    //this class model the movie database table structure
    //we have the columns and their title
    public static class  MovieEntries implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIE);


        public static final String TABLE_NAME = "movie_database";

        public  static final String COLUMN_NAME_RELEASE = "release_date";

        public static final  String COLUMN_NAME_RATE = "rate";

        public static final String COLUMN_NAME_PLOT = "plot";

        public static final String COLUMN_NAME_VIDEO_LINK = "video_link";

        public static final String COLUMN_NAME_ID = "movie_id";




    }


}
