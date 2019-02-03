package com.example.dell.moviestage2;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.moviestage2.Adapter.MovieAdapter;
import com.example.dell.moviestage2.Database.AppDatabase;
import com.example.dell.moviestage2.Model.AddTaskViewModelFactory;
import com.example.dell.moviestage2.Model.MainViewModel;
import com.example.dell.moviestage2.Model.Movie;
import com.example.dell.moviestage2.Utils.SectionLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>  {

    private static final String LOG_TAG = MovieDetailActivity.class.getName();

    public static final String URL_SCHEME = "https";
    public static final String BASE_URL = "www.youtube.com";
    public static final String PATH2 = "/watch";

    private boolean isFavourite = false;
    private AppDatabase database;


    private TextView vote_average_view, release_date_view, synopsis_view;
    private ImageView poster;
    private ImageButton favourite;
    private Context context;
    private String release_date, synopsis, vote_average, movie_poster;
    private int id;
    private static final int LOADER_ID = 2;
    private static final String PATH = "videos";

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        context = getApplicationContext();

        recyclerView = findViewById(R.id.recyclerView);

        vote_average_view = (TextView)findViewById(R.id.detailAverage);
        release_date_view = findViewById(R.id.detailRelease);
        synopsis_view = findViewById(R.id.detailOverview);
        poster = findViewById(R.id.detailImage);
        favourite = findViewById(R.id.imageButton);

        database = AppDatabase.getsInstance(getApplicationContext());

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID, null, this);



    }

    private String buildUrlWithId(int id) {

        Uri.Builder builder = new Uri.Builder();

        builder.scheme(MainActivity.URL_SCHEME).authority(MainActivity.BASE_URL).path(MainActivity.PATH);
        builder.appendPath(String.valueOf(id));
        builder.appendEncodedPath(PATH);
        builder.appendQueryParameter("api_key", MainActivity.api_key);

        return builder.toString();

    }

    public void showTrailers(View view){
        Intent intent = new Intent(this, TrailerActivity.class);
        startActivity(intent);

    }



    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("result");

        getSupportActionBar().setTitle(movie.getTitle());

        String url = "http://image.tmdb.org/t/p/w185/";
        Picasso.with(context).load(url+movie.getMovie_poster()).into(poster);

        favourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        id = movie.getId();

        if(movie.getRelease_date() == null) {
            release_date_view.setText(R.string.not_known);
        }else{
            release_date = movie.getRelease_date();
            release_date_view.setText(release_date);
        }
        if(movie.getSynopsis() == null) {
            synopsis_view.setText(R.string.not_known);

        }else{
            synopsis = movie.getSynopsis();
            synopsis_view.setText(synopsis);
        }
        vote_average = String.valueOf(movie.getVote_average());
        vote_average_view.setText(vote_average);

        String baseUrl = buildUrlWithId(id);

        return new SectionLoader(context, baseUrl, LOADER_ID);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {

        Log.e(LOG_TAG, String.valueOf(movies));


        adapter = new MovieAdapter(context, movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {

    }

    public void favouriteClicked(View view){
        if(isFavourite == false) {
            favourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            isF