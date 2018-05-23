package com.example.android.movieapp;

import java.util.Comparator;

/*
    allow us to compare movie by most favorite
 */
public class MovieComparatorByFav implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {

        double  test = (o2.getPopularity() - o1.getPopularity());

        if(test < 0 ){

            return 2;
        }

        else if(test > 0){

            return -2;
        }
        else
            return 0;




    }

}
