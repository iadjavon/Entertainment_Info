package com.example.android.movieapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import DataBase.MovieContract;

public class MovieCursorAdapter extends CursorAdapter{



    public MovieCursorAdapter(Context context, Cursor c) {

        super(context, c, 0);
    }

    //this return a blank list view that will be set on the favorite movie activity
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.favorite_poster,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        //find the image view from the list item view of the favorite activity
        ImageView favoriteImage =  (ImageView) view.findViewById(R.id.favorite_poster_view);

        //extract the image from the cursor
        String imageUrl = "" + cursor.getString(cursor.getColumnIndexOrThrow(MovieContract.MovieEntries.COLUMN_NAME_VIDEO_LINK));
        //get the priority
       // int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        //next I have to populate the image view

        //Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + imageUrl).into(favoriteImage);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/" + imageUrl).into(favoriteImage);


    }
}
