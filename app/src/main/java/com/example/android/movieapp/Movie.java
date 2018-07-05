package com.example.android.movieapp;


/*
    This class model a movie with the propertie that we need
 */
public class Movie {

    int imageDetail;
    String movieTitlte;
    String plotSummary;
    int ratings;
    String releaseDate;
    String posterPath;
    String backPath;
    double popularity;
    double average_vote;
    int id;


    /*
            Set up a movie object
     */
    public Movie(String description, String title, String release, int ratings, int imageresources,
    String poster, String back, double popularity, double average,int id){

        plotSummary = description;
        movieTitlte = title;
        releaseDate = release;
        this.ratings = ratings;
        imageDetail = imageresources;
        posterPath = poster;
        backPath = back;
        this.popularity = popularity;
        average_vote = average;
        this.id = id;

    }


    //below are the getters and setters that we need to display
    //info in our grid
    public void setImage(int image){

        imageDetail = image;
    }
    public int getImage(){

        return imageDetail;
    }
    public void setTitle(String title){

         movieTitlte = title;
    }

    public String getTitle(){

        return movieTitlte;

    }

    public String getDescription(){

        return plotSummary;
    }

    public void  setDescription(String description){

        plotSummary = description;


    }

    public String getPath(){

        return posterPath;
    }

    public String getBackPath(){

        return backPath;
    }

    public void setBackPath(String str){

        backPath = str;
    }

    public int getRatings(){

        return ratings;
    }

    public String getReleaseDate(){

        return releaseDate;
    }

    public double getPopularity(){

        return popularity;
    }

    public double getAverage(){

        return average_vote;
    }

    public int getId(){
        return id;
    }


}
