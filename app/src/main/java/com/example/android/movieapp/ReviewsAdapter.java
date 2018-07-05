package com.example.android.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ReviewsAdapter extends ArrayAdapter<String> {

    List<String> reviewsLinks;


    public ReviewsAdapter(@NonNull Context context, List<String> objects) {

        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //check if the existing view is being reused
        View listItemView = convertView;
        //if it is not reused we inflate it manually
        if(listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.reviews, parent, false);
        }

        LinearLayout scrollable = (LinearLayout) listItemView.findViewById(R.id.scroll);

        TextView view = (TextView) listItemView.findViewById(R.id.review_tv);
       // view.setText("Review");

        //scrollable.addView(view);

        return listItemView;
    }
}
