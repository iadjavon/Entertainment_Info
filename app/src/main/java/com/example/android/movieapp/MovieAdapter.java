package com.example.android.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/*
       This is a custom adapter to display movie object
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    List<Movie> theList;//source of data

    String image = " http://image.tmdb.org/t/p";//endpoint where images will be fecth from

    ImageView images;//image view

    public MovieAdapter(Context context, List<Movie> list){

        super(context,0,list);
        theList = list;

    }

    /*
           here we inflate a view or recycle it appropriately
           and display the property we need
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //check if the existing view is being reused
        View listItemView = convertView;
        //if it is not reused we inflate it manually
        if(listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item, parent, false);

        }

        ImageView images = (ImageView) listItemView.findViewById(R.id.tentative);

        Movie movie = theList.get(position);

        String str = image + movie.getPath() ;

        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500/" + movie.getPath()).into(images);
        //"https://upload.wikimedia.org/wikipedia/commons/1/15/Vada_Paav-The_Mumbai_Burger.jpg"

        return listItemView;
    }

    /*
        this method is callled if we need and update version of the
        data source
     */
    public void refreshEvents(List<Movie> events) {
        theList.clear();//clear the data soiurce
        theList.addAll(events);//update it with a new list
        notifyDataSetChanged();//notify the adapter of the update
    }


}
