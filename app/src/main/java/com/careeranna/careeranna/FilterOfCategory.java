package com.careeranna.careeranna;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.careeranna.careeranna.adapter.SubCategoryAdapter;
import com.careeranna.careeranna.data.SubCategory;

import java.util.ArrayList;

public class FilterOfCategory extends AppCompatActivity implements SubCategoryAdapter.OnItemClickListener{

    RecyclerView recyclerView;

    ArrayList<SubCategory> subCategories;

    Button free, paid, cancel, apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_of_category);

        recyclerView = findViewById(R.id.subCategory);

        subCategories = (ArrayList<SubCategory>) getIntent().getSerializableExtra("categories");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(subCategories, this);

        subCategoryAdapter.setOnItemClicklistener(this);
        recyclerView.setAdapter(subCategoryAdapter);

        free = findViewById(R.id.free);

        paid = findViewById(R.id.paid);

        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FilterOfCategory.this, "free", Toast.LENGTH_SHORT).show();

            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FilterOfCategory.this, "paid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, subCategories.get(position).getEXAM_NAME(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("category", subCategories.get(position));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
