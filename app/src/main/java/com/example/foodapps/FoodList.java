package com.example.foodapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodapps.Database.Database;
import com.example.foodapps.Model.Food;
import com.example.foodapps.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference foodList;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> mAdapter;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;

    String categoryID = "";
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    Database localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
    }
}