package com.example.android.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BackDropImage extends AppCompatActivity {

    TextView t_view;
    TextView ratings;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_drop_image);


        // get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");

        List<Movie> dope = MainActivity.getList();

        MovieAdapter imageAdapter = new MovieAdapter(this,dope);

        ImageView imageView = (ImageView) findViewById(R.id.detail_view);

        t_view = (TextView) findViewById(R.id.synopy);

        ratings = (TextView) findViewById(R.id.rate);

        date = (TextView) findViewById(R.id.release);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w780/" + dope.get(position).getBackPath()).into(imageView);

        t_view.append("\n \n" + dope.get(position).getDescription());

        ratings.append(" " + dope.get(position).getRatings());

        date.append(" " + dope.get(position).getReleaseDate());

        setTitle(dope.get(position).getTitle());
    }
}
