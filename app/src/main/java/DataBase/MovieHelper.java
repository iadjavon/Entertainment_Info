package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static DataBase.MovieContract.SQL_CREATE_ENTRIES;

public class MovieHelper extends SQLiteOpenHelper {


    public  static  final String DATABASENAME = "movie_db";

    public static final int  DATABASEVERSION = 1;


    public MovieHelper(Context context) {

        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
