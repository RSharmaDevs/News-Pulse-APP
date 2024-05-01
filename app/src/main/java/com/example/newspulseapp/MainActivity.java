package com.example.newspulseapp;

import androidx.appcompat.app.AppCompatActivity; // Importing the AppCompatActivity class from the androidx.appcompat.app package
import androidx.appcompat.widget.SearchView; // Importing the SearchView class from the androidx.appcompat.widget package
import androidx.recyclerview.widget.GridLayoutManager; // Importing the GridLayoutManager class from the androidx.recyclerview.widget package
import androidx.recyclerview.widget.RecyclerView; // Importing the RecyclerView class from the androidx.recyclerview.widget package

import android.content.Intent; // Importing the Intent class from the android.content package
import android.os.Bundle; // Importing the Bundle class from the android.os package
import android.view.View; // Importing the View class from the android.view package
import android.widget.Button; // Importing the Button class from the android.widget package
import android.widget.Toast; // Importing the Toast class from the android.widget package

import com.example.newspulseapp.Models.NewsApiResponse; // Importing the NewsApiResponse class from the com.example.newspulseapp.Models package
import com.example.newspulseapp.Models.NewsHeadlines; // Importing the NewsHeadlines class from the com.example.newspulseapp.Models package

import java.util.List; // Importing the List interface from the java.util package

// Defining the MainActivity class, which extends AppCompatActivity and implements SelectListener and OnClickListener interfaces
public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView; // Declaration of RecyclerView variable
    CustomAdapter customAdapter; // Declaration of CustomAdapter variable

    Button button1, button2, button3, button4, button5, button6, button7; // Declaration of Button variables

    SearchView searchView; // Declaration of SearchView variable

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Overriding the onCreate method
        super.onCreate(savedInstanceState); // Calling the superclass onCreate method
        setContentView(R.layout.activity_main); // Setting the content view to activity_main layout

        searchView = findViewById(R.id.search_view); // Finding the SearchView by its ID
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // Setting a listener for searchView
            @Override
            public boolean onQueryTextSubmit(String query) { // Handling search submission
                Toast.makeText(MainActivity.this, "Getting News Articles by Searching category" + query, Toast.LENGTH_SHORT).show(); // Displaying a toast message
                RequestApiManager manager = new RequestApiManager(MainActivity.this); // Creating a new RequestApiManager instance
                manager.getNewsHeadlines(listener, "general", query); // Calling getNewsHeadlines method
                return true; // Returning true to indicate the query has been handled
            }

            @Override
            public boolean onQueryTextChange(String newText) { // Handling search text change
                return false; // Returning false to indicate the query text has not been handled
            }
        });

        // Finding all buttons by their IDs
        button1 = findViewById(R.id.btn_1);
        button2 = findViewById(R.id.btn_2);
        button3 = findViewById(R.id.btn_3);
        button4 = findViewById(R.id.btn_4);
        button5 = findViewById(R.id.btn_5);
        button6 = findViewById(R.id.btn_6);
        button7 = findViewById(R.id.btn_7);

        // Setting click listeners for all buttons
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

        // Creating a new RequestApiManager instance
        RequestApiManager manager = new RequestApiManager(this);
        // Calling getNewsHeadlines method with listener and parameters
        manager.getNewsHeadlines(listener, "general", null);
    }

    // Defining the listener for fetching news data
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) { // Handling data fetch
            if (list.isEmpty()) { // Checking if the list is empty
                Toast.makeText(MainActivity.this, "No data Found!!!", Toast.LENGTH_SHORT).show(); // Displaying a toast message
            } else { // If list is not empty
                showsNews(list); // Calling showsNews method
            }
        }

        @Override
        public void onError(String message) { // Handling error
            Toast.makeText(MainActivity.this, "an error based upon the api request", Toast.LENGTH_SHORT).show(); // Displaying a toast message
        }
    };

    // Method to show news headlines
    private void showsNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main); // Finding the RecyclerView by its ID
        recyclerView.setHasFixedSize(true); // Setting the RecyclerView to have fixed size
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // Setting the layout manager for RecyclerView
        customAdapter = new CustomAdapter(this, list, this); // Creating a new CustomAdapter instance
        recyclerView.setAdapter(customAdapter); // Setting the adapter for RecyclerView
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) { // Implementing method from SelectListener interface
        startActivity(new Intent(MainActivity.this, DetailsActivity.class).putExtra("data", headlines)); // Starting DetailsActivity and passing data
    }

    @Override
    public void onClick(View v) { // Implementing method from OnClickListener interface
        Button button = (Button) v; // Casting the View to Button
        String category = button.getText().toString(); // Getting the text of the button
        Toast.makeText(this, "Fetching news of articles by " + category, Toast.LENGTH_SHORT).show(); // Displaying a toast message
        RequestApiManager manager = new RequestApiManager(this); // Creating a new RequestApiManager instance
        manager.getNewsHeadlines(listener, "general", null); // Calling getNewsHeadlines method
    }
}
