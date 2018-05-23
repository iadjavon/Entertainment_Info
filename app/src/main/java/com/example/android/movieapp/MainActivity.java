package com.example.android.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    TextView textView;
    URL param;
    MovieAdapter gridAdapter;
    SwipeRefreshLayout swipe ;
    static List<Movie> container;
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        param = NetworkUtilities.ProsessJSon.buildUrl();

        swipe = (SwipeRefreshLayout) findViewById(R.id.refresh);




        // set the grid view reference
        GridView grid = (GridView) findViewById(R.id.grid_view);

        // Create a new adapter that takes an empty list of earthquakes as input
        gridAdapter = new MovieAdapter(getApplicationContext(), new ArrayList<Movie>());

        // Set the adapter on the grid view
        // so the list can be populated in the user interface

        grid.setAdapter(gridAdapter);

        //here we handle the click event on the grid view
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(),
                        BackDropImage.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });

        //network call via background task
        new MovieTask().execute(param);


    }

    /*
       inflate menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by_popularity, menu);
        return true;
    }

    /*
         handle click of menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:

                Collections.sort(container, new MovieCompByPop());
                //afer a click get read of the progress bar
                swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        gridAdapter.refreshEvents(container);

                        if(swipe.isRefreshing()){

                            swipe.setRefreshing(false);

                        }

                    }
                });

                Toast.makeText(this,"sort by favorite",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.popularity:

                Collections.sort(container, new MovieComparatorByFav());

                swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        gridAdapter.refreshEvents(container);

                        if(swipe.isRefreshing()){

                            swipe.setRefreshing(false);

                        }

                    }
                });
                gridAdapter.refreshEvents(container);


                Toast.makeText(this,container.get(1).getTitle(),Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public static List<Movie> getList(){

        return container;
    }

    public void sortList(){

        Collections.sort(container, new MovieComparatorByFav());

    }

    /*
        This class will handle the network call and update
        the UI with the appropriate data
     */
    public class MovieTask extends AsyncTask<URL, Void, List<Movie>> {

        List<Movie> temp;//hold the movie list
        String fromNetwork;//will hold the raw json response

        @Override
        protected List<Movie> doInBackground(URL... urls) {

            URL url = urls[0];

            try{

                fromNetwork =  NetworkUtilities.ProsessJSon.fetchResult(url) ;
              //  container = new ArrayList<>(5);
                temp =  NetworkUtilities.ProsessJSon.extractJson(fromNetwork);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return temp;

        }

        @Override
        protected void onPostExecute(List<Movie> resultFromNetwork){

            //gridAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            container = resultFromNetwork;
            if (resultFromNetwork.size()!=0) {

                gridAdapter.addAll(resultFromNetwork);//the adapter is updated with the movie list

            }

        }

        /*
             this will be call if we lost connectivity
         */
        public boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }


        }
    }



