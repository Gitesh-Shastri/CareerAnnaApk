package com.careeranna.careeranna.fragement.dashboard_fragements;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.careeranna.careeranna.CategoriesSection;
import com.careeranna.careeranna.ExamPrepActivity;
import com.careeranna.careeranna.PurchaseCourses;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.data.Category;
import com.careeranna.careeranna.data.Course;
import com.careeranna.careeranna.data.ExamPrep;
import com.careeranna.careeranna.helper.RecyclerViewAdapter;
import com.careeranna.careeranna.helper.RecyclerViewExamAdapter;
import com.careeranna.careeranna.helper.RecyclerViewTopAdapter;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ExploreFragement extends Fragment implements RecyclerViewTopAdapter.OnItemClickListener,
        RecyclerViewAdapter.OnItemClickListener,
        RecyclerViewExamAdapter.OnItemClickListener{

    ArrayList<Category> categories;
    ArrayList<Course> courses;
    ArrayList<ExamPrep> examPreps;

    RecyclerView recyclerViewCourses, recyclerViewCategory, recyclerViewExamp;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewTopAdapter recyclerViewTopAdapter;
    RecyclerViewExamAdapter recyclerViewExamAdapter;

    private String[] imageUrls = new String[] {
            "https://4.bp.blogspot.com/-qf3t5bKLvUE/WfwT-s2IHmI/AAAAAAAABJE/RTy60uoIDCoVYzaRd4GtxCeXrj1zAwVAQCLcBGAs/s1600/Machine-Learning.png",
            "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg",
            "https://www.digitalvidya.com/wp-content/uploads/2016/02/Master_Digital_marketng-1170x630.jpg"
    };

    public ExploreFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_explore, container, false);

        recyclerViewCategory = view.findViewById(R.id.categories);
        recyclerViewCourses = view.findViewById(R.id.top_coursesT);
        recyclerViewExamp = view.findViewById(R.id.exam_prep_rv);

        initializeCategory();
        initializeCourse();
        initializeExamprep();

        return view;
    }

    private void initializeCategory() {

        categories = new ArrayList<>();
        categories.add(new Category("1", "Machine Learning", imageUrls[0]));
        categories.add(new Category("2", "Python", imageUrls[1]));
        categories.add(new Category("3", "Marketing", imageUrls[2]));
        categories.add(new Category("4", "Machine Learning", imageUrls[0]));
        categories.add(new Category("5", "Python", imageUrls[1]));
        categories.add(new Category("6", "Maeketing", imageUrls[2]));

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(linearLayoutManager1);

        recyclerViewAdapter = new RecyclerViewAdapter(categories, getApplicationContext());
        recyclerViewCategory.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClicklistener(this);

    }

    private void initializeCourse() {

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
        courses.add(new Course("6",  "Marketing", imageUrls[2], "1", "7999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("7",  "Machine Learning", imageUrls[0], "2", "8999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("8",  "Python", imageUrls[1], "2", "3999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        courses.add(new Course("8",  "Marketing", imageUrls[2], "1", "4999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCourses.setLayoutManager(linearLayoutManager);

        recyclerViewTopAdapter = new RecyclerViewTopAdapter(courses, getApplicationContext());
        recyclerViewCourses.setAdapter(recyclerViewTopAdapter);

        recyclerViewTopAdapter.setOnItemClicklistener(this);
    }

    private void initializeExamprep() {


        String desc = "Organizations of all sizes and Industries, be it a financial institution or a small big data start up, everyone is using Python for their business.\n" +
                "Python is among the popular data science programming languages not only in Big data companies but also in the tech start up crowd. Around 46% of data scientists use Python.\n" +
                "Python has overtaken Java as the preferred programming language and is only second to SQL in usage today. \n" +
                "Python is finding Increased adoption in numerical computations, machine learning and several data science applications.\n" +
                "Python for data science requires data scientists to learn the usage of regular expressions, work with the scientific libraries and master the data visualization concepts.";

        examPreps = new ArrayList<>();

        examPreps.add(new ExamPrep("1",  "Machine Learning", imageUrls[0], "1", "6999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("2",  "Python", imageUrls[1], "2", "4999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("3",  "Marketing", imageUrls[2], "3", "5999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("4",  "Machine Learning", imageUrls[0], "4", "6999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("5",  "Python", imageUrls[1], "5", "3999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("6",  "Marketing", imageUrls[2], "1", "7999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("7",  "Machine Learning", imageUrls[0], "2", "8999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("8",  "Python", imageUrls[1], "2", "3999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));
        examPreps.add(new ExamPrep("8",  "Marketing", imageUrls[2], "1", "4999",
                desc, "android.resource://com.careeranna.careeranna/"+R.raw.video));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerViewExamp.setLayoutManager(linearLayoutManager);

        recyclerViewExamAdapter = new RecyclerViewExamAdapter(examPreps, getApplicationContext());
        recyclerViewExamp.setAdapter(recyclerViewExamAdapter);

        recyclerViewExamAdapter.setOnItemClicklistener(this);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getApplicationContext(), PurchaseCourses.class);
        intent.putExtra("Course", courses.get(position));
        getContext().startActivity(intent);

    }

    @Override
    public void onItemClick1(int position) {

        Intent intent = new Intent(getApplicationContext(), CategoriesSection.class);
        intent.putExtra("Category", categories.get(position));
        getContext().startActivity(intent);

    }

    @Override
    public void onItemClickExamp(int position) {

        Intent intent = new Intent(getApplicationContext(), ExamPrepActivity.class);
        intent.putExtra("Examp", examPreps.get(position));
        getContext().startActivity(intent);
    }
}
