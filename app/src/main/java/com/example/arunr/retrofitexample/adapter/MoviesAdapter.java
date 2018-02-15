package com.example.arunr.retrofitexample.adapter;

import android.content.Context;
import android.icu.text.IDNA;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arunr.retrofitexample.R;
import com.example.arunr.retrofitexample.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.http.Query;

/**
 * Created by arun.r on 14-02-2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public static final String TAG = MoviesAdapter.class.getSimpleName();
    public static final String IMAGE_URL_BASE_URL = "http://image.tmdb.org/t/p/w342//";

    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        ImageView imageView;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        public MovieViewHolder(View view){
            super(view);
            moviesLayout = view.findViewById(R.id.movies_layout);
            imageView = view.findViewById(R.id.imageView);
            movieTitle = view.findViewById(R.id.title);
            data = view.findViewById(R.id.subtitle);
            movieDescription = view.findViewById(R.id.description);
            rating = view.findViewById(R.id.rating);
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context){
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position){
        String image_url = IMAGE_URL_BASE_URL + movies.get(position).getPosterPath();

        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
        Log.i(TAG, "Url of Image: " + image_url);
    }

    @Override
    public int getItemCount(){
        return movies.size();
    }
}
