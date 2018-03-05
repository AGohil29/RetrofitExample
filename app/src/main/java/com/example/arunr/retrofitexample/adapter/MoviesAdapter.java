package com.example.arunr.retrofitexample.adapter;

import android.content.Context;
import android.os.Handler;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arunr.retrofitexample.R;
import com.example.arunr.retrofitexample.activity.MovieDetails;
import com.example.arunr.retrofitexample.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

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
    private SwipeRefreshLayout swipeRefreshLayout;


    public class MovieViewHolder extends RecyclerView.ViewHolder {
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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movies.get(pos);
                        Intent intent = new Intent(context, MovieDetails.class);
                        intent.putExtra("poster_path", IMAGE_URL_BASE_URL + movies.get(pos).getPosterPath());
                        intent.putExtra("original_title", movies.get(pos).getOriginalTitle());
                        intent.putExtra("overview", movies.get(pos).getOverview());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context, SwipeRefreshLayout swipeRefreshLayout){
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public int getItemCount(){
        return movies.size();
    }

    // When swiped up to refresh it loads a random item from the movies list
    private void refresh(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               movies.add(0, movies.get(new Random().nextInt(movies.size())));
               MoviesAdapter.this.notifyDataSetChanged();
               swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
