package com.careeranna.careeranna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.careeranna.careeranna.data.Category;
import com.careeranna.careeranna.data.Course;
import com.careeranna.careeranna.helper.RecyclerViewCoursesAdapter;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CategoriesSection extends AppCompatActivity implements RecyclerViewCoursesAdapter.OnItemClickListener {

    private ArrayList<String> names;
    private ArrayList<String> urls;
    private ArrayList<Course> courses;

    private String[] imageUrls = new String[] {
            "https://4.bp.blogspot.com/-qf3t5bKLvUE/WfwT-s2IHmI/AAAAAAAABJE/RTy60uoIDCoVYzaRd4GtxCeXrj1zAwVAQCLcBGAs/s1600/Machine-Learning.png",
            "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg",
            "https://www.digitalvidya.com/wp-content/uploads/2016/02/Master_Digital_marketng-1170x630.jpg"
    };

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_section);

        recyclerView = findViewById(R.id.categoriesCourses);

        names = new ArrayList<>();
        urls = new ArrayList<>();

        Category category = (Category) getIntent().getSerializableExtra("Category");
        getSupportActionBar().setTitle(category.getName());

        urls.add(imageUrls[1]);
        names.add("Python");
        urls.add(imageUrls[2]);
        names.add("Marketing");
        urls.add(imageUrls[0]);
        names.add("Machine Learning");
        urls.add(imageUrls[1]);
        names.add("Python");
        urls.add(imageUrls[2]);
        names.add("Marketing");

        initializeCourse();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerViewCoursesAdapter recyclerViewAdapter = new RecyclerViewCoursesAdapter(names, urls, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClicklistener(this);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ExploreCourses.class);
        intent.putExtra("Course", courses.get(position));
        startActivity(intent);
    }

    public void initializeCourse() {

        String desc = "Organizations of all sizes and Industries, be it a financial institution or a small big data start up, everyone is using Python for their business.\n" +
                "Python is among the popular data science programming languages not only in Big data companies but also in the tech start up crowd. Around 46% of data scientists use Python.\n" +
                "Python has overtaken Java as the preferred programming language and is only second to SQL in usage today. \n" +
                "Python is finding Increased adoption in numerical computations, machine learning and several data science applications.\n" +
                "Python for data science requires data scientists to learn the usage of regular expressions, work with the scientific libraries and master the data visualization concepts.";

        courses = new ArrayList<>();
        courses.add(new Course("1",  "Machine Learning", imageUrls[0], "1", "6999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("2",  "Python", imageUrls[1], "2", "4999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("3",  "Marketing", imageUrls[2], "3", "5999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("4",  "Machine Learning", imageUrls[0], "4", "6999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("5",  "Python", imageUrls[1], "5", "3999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));

    }

}
