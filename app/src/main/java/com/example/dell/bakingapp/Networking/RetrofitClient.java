package com.example.dell.bakingapp.Networking;

import android.net.Uri;
import android.util.Log;

import com.example.dell.bakingapp.Model.Fields;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    static final String URL_SCHEME = "https";
    static final String BASE_URL = "d17h27t6h515a5.cloudfront.net";
    static final String PATH = "/topher/2017/May/59121517_baking/";

    private static final String LOG_TAG = RetrofitClient.class.getSimpleName();


    public static Retrofit retrofit;


    public static Retrofit getApiClient(){

        if(retrofit == null){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Gson gson = new Gson();



            retrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }

        return retrofit;

    }

    private static String getBaseUrl() {
        Uri uri = buildUri();
        return uri.toString();
    }

    private static Uri buildUri() {
        Uri.Builder builder = new Uri.Builder()
                .scheme(URL_SCHEME)
                .authority(BASE_URL)
                .path(PATH);

        Log.e(LOG_TAG, builder.toString());

        return builder.build();


    }
}
