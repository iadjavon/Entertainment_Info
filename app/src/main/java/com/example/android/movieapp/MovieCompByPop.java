package com.example.android.movieapp;

import java.util.Comparator;
/*
    This class aloow us to compare movie from most popular
 */
public class MovieCompByPop implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {

        double  test = (o1.getAverage() - o2.getAverage());

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
