package com.example.android.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import DataBase.MovieContract;

public class BackDropImage extends AppCompatActivity {

    public static ArrayList<String>  checkedMovie = new ArrayList<String>();

    public static ArrayList<Uri> uriChecked = new ArrayList<Uri>();

    //provider
    //MovieProvider movieProvider;

    //uri for inserted rows

    //this is the uri reference to the movie to be inserted or deleted
    Uri uriOfInserted;

    //this is the row of the movie deleted from the movie database
    int rowsDeleted = 0;

    TextView t_view;
    TextView ratings;
    TextView date;



    ContentValues contentValues;

    VideoAdapter adapterForVideo;

    ReviewsAdapter reviewsAdapter;

    ListView listView;//this is for the you tube videos

    ListView review_list_view;//this is for the reviews

    ScrollView sView;//this scroll view is for the yout ube videos

    ScrollView scrollReviews;// this scroll view is for the reviews



    LinearLayout movieKey;

    List<String> blah = new ArrayList<String>();//this is a list of string key to launch the videos

    List<String> listForTheReviews =  new ArrayList<String>();//this will jold the link for the reviews

    String ha;

    LinearLayout videoReference;

    int j;//index to traverse the list of trailers keys

    int k;//index to traverse the list of link for reiews

    int videoId;

    URL trailersUrls;

    URL reviewsURL;

    String base = "http://api.themoviedb.org/";

    final String API_PARAMETER = "api_key";

    final String Key = ""; //add your api key here

    final String ID = "id";

    Uri trailers;

    Uri reviewUri;

    String follow;

    String result;

    String key;

    int i;

    int position;

    List<Movie>dope;

    String id;

    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_back_drop_image);

       // checkedMovie = new ArrayList<String>();

        // get intent data
        Intent i = getIntent();
        // Selected image id
        position = i.getExtras().getInt("id");

        dope = MainActivity.getList();

        MovieAdapter imageAdapter = new MovieAdapter(this, dope);

        ImageView imageView = (ImageView) findViewById(R.id.detail_view);

        t_view = (TextView) findViewById(R.id.synopy);
        t_view.setMovementMethod(ScrollingMovementMethod.getInstance());

        ratings = (TextView) findViewById(R.id.rate);

        date = (TextView) findViewById(R.id.release);

        //instantiating the adapter for the video
        adapterForVideo = new VideoAdapter(getApplicationContext(),new ArrayList<String>());

        //instantiating the adapter for the reviews
        reviewsAdapter = new ReviewsAdapter(getApplicationContext(),new ArrayList<String>());
        //referencing list view for the videos trailers
        listView = (ListView) findViewById(R.id.list_Trailers);



        //referencing list view for the reviews
        review_list_view = (ListView) findViewById(R.id.list_Reviews);
        //setting adapter on the list view for the video
        listView.setAdapter(adapterForVideo);
        //setting the review adapter on the list
        review_list_view.setAdapter(reviewsAdapter);
        //setting the listner on the list view for the videos
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Intent intent = new Intent(getApplicationContext(),
                        //BackDropImage.class);

                //intent.putExtra("id", position);

                String url = "https://www.youtube.com/watch?v=" + blah.get(position);

                         Uri webpage = Uri.parse(url);
                         Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                         if (intent.resolveActivity(getPackageManager()) != null) {
                             startActivity(intent);
                         }
                startActivity(intent);
            }
        });


        //here  I need the array with the righ info for the url in progressssss
        review_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Intent intent = new Intent(getApplicationContext(),
                //BackDropImage.class);

                //intent.putExtra("id", position);

                String url = "https://www.themoviedb.org/review/" + listForTheReviews.get(position);

                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                startActivity(intent);

                
            }
        });






        Picasso.with(this).load("http://image.tmdb.org/t/p/w780/" + dope.get(position).getBackPath()).into(imageView);

        videoId = dope.get(position).getId();

        follow = "" + videoId;




        //this is the uri to fetch the trailers keys
        trailers = Uri.parse(base).buildUpon().path("3/movie/" + follow + "/videos").
                appendQueryParameter(API_PARAMETER, Key).
                build();

        try {
            trailersUrls = new URL(trailers.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //here we will extract the url for the movie reviews

        reviewUri = Uri.parse(base).buildUpon().path("3/movie/" + follow + "/reviews").
                appendQueryParameter(API_PARAMETER, Key).
                build();

        try {
            reviewsURL = new URL(reviewUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        t_view.append("\n \n" + dope.get(position).getDescription());

        ratings.append(" " + dope.get(position).getRatings());

        date.append(" " + dope.get(position).getReleaseDate());

        setTitle(dope.get(position).getTitle());

        //this will aunch the backgroung task for the videos
        new VideoTask().execute(trailersUrls);
        //this will launch the background task for the reviews
        new ReviewTask().execute(reviewsURL);


        

        //setting the listener for the Fab
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //insert in the data base
               // insert();
                contentValues = new ContentValues();

                String rate = "" + dope.get(position).getRatings();
                String description = dope.get(position).getDescription();
                String release = dope.get(position).getReleaseDate();
                String path =dope.get(position).getPath();
                 id = "" + dope.get(position).getId();
                contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_RATE, rate);
                contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_PLOT, description);
                contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_RELEASE, release);
                contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_VIDEO_LINK, path);
                contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_ID, id);

               // uriOfInserted = getContentResolver().insert(MovieContract.MovieEntries.CONTENT_URI,
                        //contentValues);

                if( checkedMovie.size()==0 || !checkedMovie.contains(id)) {

                    uriOfInserted = getContentResolver().insert(MovieContract.MovieEntries.CONTENT_URI,
                            contentValues);

                    checkedMovie.add(id);

                    uriChecked.add(uriOfInserted);

                    Toast.makeText(getApplicationContext(),"movie was added to database",Toast.LENGTH_SHORT).show();

                }
                else{

                   if(checkedMovie.contains(id)){

                        level = checkedMovie.indexOf(id);

                        uriOfInserted = uriChecked.get(level);

                        rowsDeleted = getContentResolver().delete(uriOfInserted, null, null);
                        //rowsDeleted = 0;
                        checkedMovie.remove(id);
                        uriChecked.remove(uriOfInserted);
                        uriOfInserted = null;
                       // checkedMovie.remove(id);
                        Toast.makeText(getApplicationContext(),"The movie was deleted from your favorite list",Toast.LENGTH_SHORT).show();


                    }else{

                        Toast.makeText(getApplicationContext(),"Sorry the movie was not delete from your favorite",Toast.LENGTH_SHORT).show();

                    }

                    //Toast.makeText(getApplicationContext(),"Sorry the movie was not delete from your favorite",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.favorite, menu);
//        return true;
//    }


    public class VideoTask extends AsyncTask<URL, Void, List<String>> {

        List<String> temp = new ArrayList<String>();//hold the list of video keys
        String fromNetwork;//will hold the raw json response

        @Override
        protected List<String> doInBackground(URL... urls) {

            URL url = urls[0];

            try {

                fromNetwork = NetworkUtilities.ProsessJSon.fetchResult(url);
                //  container = new ArrayList<>(5);
                temp = NetworkUtilities.ProsessJSon.extractVideoJson(fromNetwork);

            } catch (IOException e) {

                e.printStackTrace();
            }

            return temp;

        }

        @Override
        protected void onPostExecute(List<String> resultFromNetwork) {

            blah = resultFromNetwork;
            adapterForVideo.addAll(resultFromNetwork);

        }

    }


    public class ReviewTask extends AsyncTask<URL, Void, List<String>> {

        //hold the list of id for the reviews
        List<String> tempReviews = new ArrayList<String>();
        String fromNetworkReviews;//will hold the raw json response

        @Override
        protected List<String> doInBackground(URL... urls) {

            URL url = urls[0];

            try {

                fromNetworkReviews = NetworkUtilities.ProsessJSon.fetchResult(url);
                //  container = new ArrayList<>(5);
                tempReviews = NetworkUtilities.ProsessJSon.extractReviewsJson(fromNetworkReviews);

            } catch (IOException e) {

                e.printStackTrace();
            }

            return tempReviews;

        }

        @Override
        protected void onPostExecute(List<String> resultFromNetwork) {

            listForTheReviews = resultFromNetwork;

            reviewsAdapter.addAll(resultFromNetwork);

        }

    }

    public void insert(){

        Toast.makeText(getApplicationContext(),"in insert method",Toast.LENGTH_SHORT).show();
        String rate = "" + dope.get(position).getRatings();
        contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_RATE, rate);
        contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_PLOT, dope.get(position).getDescription());
        contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_RELEASE, dope.get(position).getReleaseDate());
        contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_VIDEO_LINK, dope.get(position).getPath());
        contentValues.put(MovieContract.MovieEntries.COLUMN_NAME_ID, dope.get(position).getId());

        uriOfInserted = getContentResolver().insert(MovieContract.MovieEntries.CONTENT_URI,
                contentValues);

        //if insertion was sucessful let's print a toast to show the uri
       // Toast.makeText(getBaseContext(),uriOfInserted.toString(),Toast.LENGTH_SHORT).show();

    }









}

