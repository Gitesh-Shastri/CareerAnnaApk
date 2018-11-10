package com.careeranna.careeranna.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.ParticularCourse;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.helper.RecyclerViewCoursesAdapter;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCoursesFragment extends Fragment implements RecyclerViewCoursesAdapter.OnItemClickListener {

    private ArrayList<String> names;
    private ArrayList<String> urls;
    ImageView imageView;
    TextView textView;

    private String[] imageUrls = new String[] {
            "https://4.bp.blogspot.com/-qf3t5bKLvUE/WfwT-s2IHmI/AAAAAAAABJE/RTy60uoIDCoVYzaRd4GtxCeXrj1zAwVAQCLcBGAs/s1600/Machine-Learning.png",
            "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg",
            "https://www.digitalvidya.com/wp-content/uploads/2016/02/Master_Digital_marketng-1170x630.jpg"
    };

    public MyCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_course, container, false);
        names = new ArrayList<>();
        urls = new ArrayList<>();
        imageView = view.findViewById(R.id.categoryImageC);
        textView = view.findViewById(R.id.categoriesTC);
        Glide.with(getApplicationContext())
                .load(imageUrls[0])
                .into(imageView);
        textView.setText("Machine Learning");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParticularCourse.class);
                intent.putExtra("category_name", imageUrls[0]);
                intent.putExtra("category_image", textView.getText().toString());
                getContext().startActivity(intent);
            }
        });
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView recyclerView = view.findViewById(R.id.my_courses);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewCoursesAdapter recyclerViewAdapter = new RecyclerViewCoursesAdapter(names, urls, getApplicationContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClicklistener(this);
        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ParticularCourse.class);
        intent.putExtra("category_name", names.get(position));
        intent.putExtra("category_image", urls.get(position));
        getContext().startActivity(intent);
    }
}
