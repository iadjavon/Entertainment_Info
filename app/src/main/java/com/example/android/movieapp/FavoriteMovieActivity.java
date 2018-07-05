package com.example.android.movieapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import DataBase.MovieContract;

public class FavoriteMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    //reference to the movie cursor
    Cursor cursor_of_favorite_movies;
    MovieCursorAdapter cursorAdapter;
    GridView fav_list_view;
    final static int LOADER_ID= 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        setTitle("Favorite Movie");
        //get a reference to the favorite list view
       fav_list_view = (GridView) findViewById(R.id.favorite_list_view);
       //instantiate a new cursor adapter
       cursorAdapter = new MovieCursorAdapter(this,null);
        //attach the cursor adapter to the list view
        fav_list_view.setAdapter(cursorAdapter);
        //here we will kick off the loader
        getLoaderManager().initLoader(LOADER_ID,null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        //define a projection that return a cursor with the column that we need
        String[] projection ={MovieContract.MovieEntries._ID,
                MovieContract.MovieEntries.COLUMN_NAME_ID,
                MovieContract.MovieEntries.COLUMN_NAME_VIDEO_LINK,
                MovieContract.MovieEntries.COLUMN_NAME_RATE
        };
        //next the loader that we define here will be executed on the background thread
        return new CursorLoader(this,
                MovieContract.MovieEntries.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //the cursor return from the background thread is used here
        cursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader loader) {

        //this is needed anytime the cursor change and we need to notify UI and avoid
        //memory leak
        cursorAdapter.swapCursor(null);

    }

}
