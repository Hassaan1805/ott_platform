package com.hassaan.netflixclone;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private RecyclerView recyclerView1, recyclerView2;
    private Adaptery adaptery1, adaptery2;
    private MovieDataSource dataSource;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager llManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView1 = findViewById(R.id.rv1);
        recyclerView2 = findViewById(R.id.rv2);
        searchEditText = findViewById(R.id.search);

        recyclerView1.setLayoutManager(llManager);
        recyclerView2.setLayoutManager(llManager2);

        adaptery1 = new Adaptery(this, new ArrayList<>());
        adaptery2 = new Adaptery(this, new ArrayList<>());

        recyclerView1.setAdapter(adaptery1);
        recyclerView2.setAdapter(adaptery2);
        dataSource = new MovieDataSource(this);
        String movieData1 = "name : Kung Fu Panda 4\n" +
                "vote_average : 6.937\n" +
                "poster_path : /kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg\n" +
                "-\n" +
                "name : Madame Web\n" +
                "vote_average : 5.629\n" +
                "poster_path : /rULWuutDcN5NvtiZi4FRPzRYWSh.jpg\n" +
                "-\n" +
                "name : Godzilla x Kong: The New Empire\n" +
                "vote_average : 7.448\n" +
                "poster_path : /phmjv93zEwitWLJEOvlXPhtK58o.jpg\n" +
                "-\n" +
                "name : Migration\n" +
                "vote_average : 7.564\n" +
                "poster_path : /ldfCF9RhR40mppkzmftxapaHeTo.jpg\n" +
                "-\n" +
                "name : Dune: Part Two\n" +
                "vote_average : 8.385\n" +
                "poster_path : /czembW0Rk1Ke7lCJGahbOhdCuhV.jpg\n" +
                "-";
        String movieData2 = "name : The Shawshank Redemption\n" +
                "vote_average : 8.705\n" +
                "poster_path : /9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg\n" +
                "-\n" +
                "name : The Godfather\n" +
                "vote_average : 8.695\n" +
                "poster_path : /3bhkrj58Vtu7enYsRolD1fZdja1.jpg\n" +
                "-\n" +
                "name : The Godfather Part II\n" +
                "vote_average : 8.575\n" +
                "poster_path : /hek3koDUyRQk7FIhPXsa6mT2Zc3.jpg\n" +
                "-\n" +
                "name : Schindler's List\n" +
                "vote_average : 8.567\n" +
                "poster_path : /sF1U4EUQS8YHUYjNl3pMGNIQyr0.jpg\n" +
                "-\n" +
                "name : 12 Angry Men\n" +
                "vote_average : 8.541\n" +
                "poster_path : /ow3wq89wM8qd5X7hWKxiRfsFf9C.jpg\n" +
                "-";

        List<String> movieDataList1 = new ArrayList<>();
        movieDataList1.add(movieData1);
        List<String> movieDataList2 = new ArrayList<>();
        movieDataList2.add(movieData2);

//        dataSource.insertMovies1(movieDataList1);
//        dataSource.insertMovies2(movieDataList2);
////        // Set text change listener for search EditText
        searchEditText.addTextChangedListener(this);

        // Fetch top-rated movies from the database
        List<MovieModel> topRatedMovies = dataSource.getAllTopRatedMovies();
        if (topRatedMovies != null && !topRatedMovies.isEmpty()) {
            adaptery1.setData(topRatedMovies);
        } else {
            Log.e("MainActivity", "Top-rated movie data is empty or null");
        }

        // Fetch upcoming movies from the database
        List<MovieModel> upcomingMovies = dataSource.getAllUpcomingMovies();
        if (upcomingMovies != null && !upcomingMovies.isEmpty()) {
            adaptery2.setData(upcomingMovies);
        } else {
            Log.e("MainActivity", "Upcoming movie data is empty or null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public void show(View view) {
        Intent intent = new Intent(getApplicationContext(), profileActivtiy.class);
        startActivity(intent);
        finish();
    }

    // TextWatcher methods
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String query = s.toString().toLowerCase().trim();

        // Filter movie list based on the search query
        List<MovieModel> filteredList1 = new ArrayList<>();
        List<MovieModel> filteredList2 = new ArrayList<>();

        for (MovieModel movie : dataSource.getAllTopRatedMovies()) {
            if (movie.getTitle().toLowerCase().contains(query)) {
                filteredList1.add(movie);
            }
        }

        for (MovieModel movie : dataSource.getAllUpcomingMovies()) {
            if (movie.getTitle().toLowerCase().contains(query)) {
                filteredList2.add(movie);
            }
        }

        // Update adapter data with filtered lists
        adaptery1.setData(filteredList1);
        adaptery2.setData(filteredList2);

        // Show/hide RecyclerViews based on whether the search query is empty or not
        if (query.isEmpty()) {
            recyclerView1.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
        } else {
            recyclerView1.setVisibility(View.GONE);
            recyclerView2.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
    public void openPanda(View view){
        Intent intent = new Intent(getApplicationContext(), pandaFragment.class);
        startActivity(intent);
    }
    public void openWeb(View view){
        Intent intent = new Intent(getApplicationContext(), webFragment.class);
        startActivity(intent);

    }
    public void openMigration(View view){
        Intent intent = new Intent(getApplicationContext(), migrationFragment.class);
        startActivity(intent);

    }
    public void openZilla(View view){
        Intent intent = new Intent(getApplicationContext(), zillaFragment.class);
        startActivity(intent);

    }

}
