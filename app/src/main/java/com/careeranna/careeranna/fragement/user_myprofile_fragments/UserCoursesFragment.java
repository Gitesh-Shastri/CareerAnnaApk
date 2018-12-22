package com.careeranna.careeranna.fragement.user_myprofile_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.UserFragmentsAdapters.UserCoursesAdapter;
import com.careeranna.careeranna.data.User;
import com.careeranna.careeranna.user.MyProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCoursesFragment extends Fragment {

    RecyclerView rv_user_courses;
    UserCoursesAdapter adapter;

//    private ArrayList<String> course_names, course_ids, course_images;

    public UserCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_courses, container, false);
//        course_ids = new ArrayList<>();
//        course_images = new ArrayList<>();
//        course_names = new ArrayList<>();

        adapter = new UserCoursesAdapter(getContext());

        rv_user_courses = view.findViewById(R.id.user_courses);
        rv_user_courses.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv_user_courses.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    public void addUserCourses(ArrayList<String> course_names, ArrayList<String> course_ids ,ArrayList<String> course_images){
//        this.course_ids = course_ids;
//        this.course_images = course_images;
//        this.course_names = course_names;

        adapter.updateDataset(course_images, course_names, course_ids);
    }

}
