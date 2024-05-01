package com.example.newspulseapp;

import android.content.Context; // Importing the Context class from the android.content package
import android.view.LayoutInflater; // Importing the LayoutInflater class from the android.view package
import android.view.View; // Importing the View class from the android.view package
import android.view.ViewGroup; // Importing the ViewGroup class from the android.view package

import androidx.annotation.NonNull; // Importing the NonNull annotation from the androidx.annotation package
import androidx.recyclerview.widget.RecyclerView; // Importing the RecyclerView class from the androidx.recyclerview.widget package

import com.example.newspulseapp.Models.NewsHeadlines; // Importing the NewsHeadlines class from the com.example.newspulseapp.Models package
import com.squareup.picasso.Picasso; // Importing the Picasso class from the com.squareup.picasso package

import java.util.List; // Importing the List interface from the java.util package

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> { // Defining the CustomAdapter class, which extends RecyclerView.Adapter<CustomViewHolder>
    private Context context; // Declaration of Context variable
    private List<NewsHeadlines> headlines; // Declaration of List variable

    private SelectListener listener; // Declaration of SelectListener variable

    public CustomAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) { // Constructor with parameters
        this.context = context; // Initializing the context variable
        this.headlines = headlines; // Initializing the headlines variable
        this.listener = listener; // Initializing the listener variable
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Overriding onCreateViewHolder method
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false)); // Inflating the layout and returning a new CustomViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) { // Overriding onBindViewHolder method
        holder.text_title.setText(headlines.get(position).getTitle()); // Setting the title text
        holder.text_source.setText(headlines.get(position).getSource().getName()); // Setting the source text

        if (headlines.get(position).getUrlToImage() != null) { // Checking if urlToImage is not null
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline); // Loading image using Picasso library
        }

        final int currentPosition = position; // Declare position as final or effectively final

        holder.cardView.setOnClickListener(new View.OnClickListener() { // Setting onClickListener for cardView
            @Override
            public void onClick(View v) { // Handling click event
                listener.OnNewsClicked(headlines.get(currentPosition)); // Invoking OnNewsClicked method of the listener
            }
        });
    }

    @Override
    public int getItemCount() { // Overriding getItemCount method
        return headlines.size(); // Returning the size of headlines list
    }
}
