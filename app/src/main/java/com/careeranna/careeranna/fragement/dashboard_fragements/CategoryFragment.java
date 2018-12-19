package com.careeranna.careeranna.fragement.dashboard_fragements;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.careeranna.CategoriesSection;
import com.careeranna.careeranna.FilterOfCategory;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.SortByCourse;
import com.careeranna.careeranna.adapter.CoursesSectionAdapter;
import com.careeranna.careeranna.adapter.FreeCourseAdapter;
import com.careeranna.careeranna.data.Course;
import com.careeranna.careeranna.data.FreeVideos;
import com.careeranna.careeranna.data.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CategoryFragment extends Fragment {

    ProgressDialog progressDialog;

    Snackbar snackbar;

    ProgressBar progressBar;


    private ArrayList<String> names;
    private ArrayList<String> urls;
    private ArrayList<Course> courses;

    private ArrayList<Course> tempCourse;

    ArrayList<SubCategory> subCategories;

    RecyclerView recyclerView;

    ArrayList<FreeVideos> freeVideos;

    String id;

    Button filterSub, sortSub;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public void addSubCategory(String id) {

        this.id = id;

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.categoriesCourses);

        filterSub = view.findViewById(R.id.filterSub);

        sortSub = view.findViewById(R.id.sort);

        names = new ArrayList<>();
        urls = new ArrayList<>();

        courses = new ArrayList<>();

        tempCourse = new ArrayList<>();

        subCategories = new ArrayList<>();

        filterSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FilterOfCategory.class);
                intent.putExtra("categories", subCategories);
                startActivityForResult(intent, 111);
            }
        });


        sortSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SortByCourse.class);
                intent.putExtra("categories", subCategories);
                startActivityForResult(intent, 111);
            }
        });

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Sub Category Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final String url = "http://careeranna.in/subCategory.php?id="+id;
        Log.d("url_res", url);
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<String> subcategories = new ArrayList<>();
                        try {
                            Log.i("url_response", response.toString());
                            JSONArray SubCategoryArray = new JSONArray(response.toString());
                            for(int i=0;i<SubCategoryArray.length();i++) {
                                JSONObject Category = SubCategoryArray.getJSONObject(i);
                                subcategories.add(Category.getString("EXAM_NAME"));
                                subCategories.add(new SubCategory(Category.getString("EXAM_NAME_ID"),
                                        Category.getString("EXAM_NAME"),
                                        Category.getString("CATEGORY_ID"),
                                        Category.getString("ACTIVE_STATUS")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();

                        populateCourse();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();

                    }
                }
        );

        requestQueue.add(stringRequest);


        freeVideos = new ArrayList<>();

        freeVideos.add(new FreeVideos());
        freeVideos.add(new FreeVideos());
        freeVideos.add(new FreeVideos());
        freeVideos.add(new FreeVideos());
        freeVideos.add(new FreeVideos());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));

        FreeCourseAdapter freeCourseAdapter = new FreeCourseAdapter(courses, getApplicationContext());

        recyclerView.setAdapter(freeCourseAdapter);

        return view;
    }

    private void populateCourse() {

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Courses Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());
        String url1 = "http://careeranna.in/getAllCourse.php";
        Log.d("url_res", url1);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            courses = new ArrayList<>();
                            names = new ArrayList<>();
                            urls = new ArrayList<>();

                            Log.i("url_response", response.toString());
                            JSONArray CategoryArray = new JSONArray(response.toString());
                            for (int i = 0; i < CategoryArray.length(); i++) {
                                JSONObject Category = CategoryArray.getJSONObject(i);
                                names.add(Category.getString("course_name"));
                                urls.add("https://www.careeranna.com/" + Category.getString("product_image").replace("\\", ""));
                                courses.add(new Course(Category.getString("product_id"),
                                        Category.getString("course_name"),
                                        "https://www.careeranna.com/" + Category.getString("product_image").replace("\\", ""),
                                        Category.getString("exam_id"),
                                        Category.getString("price")
                                        , "",
                                        Category.getString("video_url").replace("\\", "")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();

                        for(SubCategory subCategory: subCategories) {
                            for (Course course: courses) {
                                if(course.getCategory_id().equals(subCategory.getEXAM_NAME_ID())) {
                                    tempCourse.add(course);
                                }
                            }
                        }

                        FreeCourseAdapter freeCourseAdapter = new FreeCourseAdapter(tempCourse, getApplicationContext());

                        recyclerView.setAdapter(freeCourseAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue1.add(stringRequest1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 111) {

            if(resultCode == Activity.RESULT_OK) {
                tempCourse = new ArrayList<>();
                SubCategory subCategory = (SubCategory) data.getSerializableExtra("category");
                for (Course course: courses) {
                    if(course.getCategory_id().equals(subCategory.getEXAM_NAME_ID())) {
                        tempCourse.add(course);
                    }
                }

                FreeCourseAdapter freeCourseAdapter = new FreeCourseAdapter(tempCourse, getApplicationContext());

                recyclerView.setAdapter(freeCourseAdapter);

            }
        }
    }
}
