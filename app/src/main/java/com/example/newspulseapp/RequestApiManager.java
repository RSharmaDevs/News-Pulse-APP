package com.example.newspulseapp;

import android.content.Context; // Importing the Context class from the android.content package
import android.widget.Toast; // Importing the Toast class from the android.widget package

import com.example.newspulseapp.Models.NewsApiResponse; // Importing the NewsApiResponse class from the com.example.newspulseapp.Models package

import retrofit2.Call; // Importing the Call class from the retrofit2 package
import retrofit2.Callback; // Importing the Callback interface from the retrofit2 package
import retrofit2.Response; // Importing the Response class from the retrofit2 package
import retrofit2.Retrofit; // Importing the Retrofit class from the retrofit2 package
import retrofit2.converter.gson.GsonConverterFactory; // Importing the GsonConverterFactory class from the retrofit2.converter.gson package
import retrofit2.http.GET; // Importing the GET annotation from the retrofit2.http package
import retrofit2.http.Query; // Importing the Query annotation from the retrofit2.http package

public class RequestApiManager { // Defining the RequestApiManager class
    Context context; // Declaration of Context
    Retrofit retrofit = new Retrofit.Builder() // Creating a Retrofit instance
            .baseUrl("https://newsapi.org/v2/") // Setting the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Adding Gson converter factory
            .build(); // Building the Retrofit instance

    // Constructor for RequestApiManager class
    public RequestApiManager(Context context) {
        this.context = context; // Initializing the context variable
    }

    // Method to fetch news headlines
    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query) {
        // Creating an instance of the interface
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        // Creating a call to the API
        Call<NewsApiResponse> call = callNewsApi.callHeadlines("us", category, query, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsApiResponse>() { // Asynchronous call to the API
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) { // Handling response
                    if (!response.isSuccessful()) { // Checking if response is successful
                        Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show(); // Displaying an error message
                    }
                    listener.onFetchData(response.body().getArticles(), response.message()); // Invoking onFetchData method of the listener
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable throwable) { // Handling failure
                    listener.onError("Request Failed!!!"); // Invoking onError method of the listener
                }
            });
        } catch (Exception e) { // Catching any exceptions
            e.printStackTrace(); // Printing stack trace
        }
    }

    // Interface to define the API endpoints
    public interface CallNewsApi {
        @GET("top-headlines") // Defining the endpoint

            // Method to call the top headlines endpoint
        Call<NewsApiResponse> callHeadlines(
                @Query("country") String country, // Country parameter
                @Query("category") String category, // Category parameter
                @Query("q") String query, // Query parameter
                @Query("apiKey") String api_key // API key parameter
        );
    }
}
