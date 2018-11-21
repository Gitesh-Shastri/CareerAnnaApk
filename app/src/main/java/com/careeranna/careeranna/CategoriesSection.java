package com.careeranna.careeranna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.careeranna.data.Category;
import com.careeranna.careeranna.data.Course;
import com.careeranna.careeranna.helper.RecyclerViewCoursesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesSection extends AppCompatActivity implements RecyclerViewCoursesAdapter.OnItemClickListener {

    private ArrayList<String> names;
    private ArrayList<String> urls;
    private ArrayList<Course> courses;

    RecyclerViewCoursesAdapter recyclerViewAdapter;

    ProgressDialog progressDialog;

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

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Category Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        courses = new ArrayList<>();

        String id = category.getId();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://careeranna.in/courseByCategory.php?category="+id;
        Log.d("url_res", url);
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("url_response", response.toString());
                            JSONArray CategoryArray = new JSONArray(response.toString());
                            for(int i=0;i<CategoryArray.length();i++) {
                                JSONObject Category = CategoryArray.getJSONObject(i);
                                names.add(Category.getString("course_name"));
                                urls.add("https://www.careeranna.com/"+Category.getString("product_image").replace("\\",""));
                                courses.add(new Course(Category.getString("product_id"),
                                        Category.getString("course_name"),
                                        "https://www.careeranna.com/"+Category.getString("product_image").replace("\\",""),
                                        Category.getString("category_id"),
                                        Category.getString("price")
                                        , Category.getString("description"),
                                        Category.getString("video_url").replace("\\","")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewCoursesAdapter(names, urls, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClicklistener(this);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), PurchaseCourses.class);
        intent.putExtra("Course", courses.get(position));
        startActivity(intent);
    }

}
