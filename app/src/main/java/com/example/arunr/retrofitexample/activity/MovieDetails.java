package com.example.arunr.retrofitexample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arunr.retrofitexample.R;
import com.squareup.picasso.Picasso;

/**
 * Created by arun.r on 20-02-2018.
 */

public class MovieDetails extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // set views from movie details layout
        ImageView imageMovieDetails = findViewById(R.id.imageMovieDetails);
        TextView movieTitle = findViewById(R.id.movieTitle);
        TextView movieDescription = findViewById(R.id.description);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("original_title")){
            String moviePoster = getIntent().getExtras().getString("poster_path");
            String title = getIntent().getExtras().getString("original_title");
            String description = getIntent().getExtras().getString("overview");

            Picasso.with(this)
                    .load(moviePoster)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageMovieDetails);
            movieTitle.setText(title);
            movieDescription.setText(description);
        }
    }
}
