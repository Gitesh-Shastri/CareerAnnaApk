package com.careeranna.careeranna.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.careeranna.careeranna.CategoriesSection;
import com.careeranna.careeranna.ExploreCourses;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.helper.RecyclerViewAdapter;
import com.careeranna.careeranna.helper.RecyclerViewTopAdapter;
import com.careeranna.careeranna.viewPagerAdapter;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragement extends Fragment implements RecyclerViewTopAdapter.OnItemClickListener,RecyclerViewAdapter.OnItemClickListener {

    ArrayList<String> names;
    ArrayList<String> urls;

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
        View view = inflater.inflate(R.layout.fragment_my_courses, container, false);
        names = new ArrayList<>();
        urls = new ArrayList<>();
        urls.add(imageUrls[0]);
        names.add("Machine Learning");
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.categories);
        RecyclerView recyclerView1 = view.findViewById(R.id.top_coursesT);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager1);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(names, urls, getApplicationContext());
        RecyclerViewTopAdapter recyclerViewTopAdapter = new RecyclerViewTopAdapter(names, urls, getApplicationContext());

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView1.setAdapter(recyclerViewTopAdapter);
        recyclerViewAdapter.setOnItemClicklistener(this);
        recyclerViewTopAdapter.setOnItemClicklistener(this);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ExploreCourses.class);
        intent.putExtra("course_name", names.get(position));
        intent.putExtra("course_image", urls.get(position));
        getContext().startActivity(intent);
    }

    @Override
    public void onItemClick1(int position) {
        Intent intent = new Intent(getApplicationContext(), CategoriesSection.class);
        intent.putExtra("category_name", names.get(position));
        intent.putExtra("category_image", urls.get(position));
        getContext().startActivity(intent);
    }
}
