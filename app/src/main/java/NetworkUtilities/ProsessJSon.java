package NetworkUtilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.movieapp.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ProsessJSon {

    private static final String Key =  "";//insert your api key here

    private static final String API_PARAMETER = "api_key";

    private static final String base = "http://api.themoviedb.org/";;

    private static final String Q_PARAMETER = "q";

    public static Uri request;

    public static java.net.URL url;

    public static String networkResponse;

    public static List<Movie> theMovieList = new ArrayList<Movie>();



    public  static java.net.URL buildUrl(){

        //building the Uri
        request = Uri.parse(base).buildUpon().path("3/discover/movie").
                appendQueryParameter(API_PARAMETER, Key).
                build();

        try {

            url = new URL(request.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String fetchResult(URL url)throws IOException{

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        try{

            InputStream in = connection.getInputStream();

            StringBuilder output = new StringBuilder();
            if (in != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }

            networkResponse = output.toString();




        }finally {

            connection.disconnect();
        }

        return networkResponse;



    }

    public static List<Movie> extractJson(String  json){

        // Create an empty ArrayList that we can start adding earthquakes to


        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray movieArray = baseJsonResponse.getJSONArray("results");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < movieArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentMovie = movieArray.getJSONObject(i);

                // get the movie id
                int movieId = currentMovie.getInt("id");

                // Extract the movie title
                String  title = currentMovie.getString("title");

                // Extract the average voting
                int  ratings = currentMovie.getInt("vote_average");

                // movie summary
                String  plot = currentMovie.getString("overview");

                // Extract the value for the release date
                String release = currentMovie.getString("release_date");
                //value for the poster path

                String posterPic = currentMovie.getString("poster_path");

                //value for the back poster
                String backPic = currentMovie.getString("backdrop_path");

                //get popularity

                double pop = currentMovie.getDouble("popularity");

                //get votes average

                double average = currentMovie.getDouble("vote_average");


                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
                Movie aMovie = new Movie(plot, title, release, ratings, movieId, posterPic,backPic,pop,average);

                // Add the new {@link Earthquake} to the list of earthquakes.
                theMovieList.add(aMovie);
            }

        } catch (JSONException e) {

            Log.e("ProsessJSon.class", "Problem making the HTTP request.", e);


        }

        // Return the list of earthquakes
        return theMovieList;


    }




}
