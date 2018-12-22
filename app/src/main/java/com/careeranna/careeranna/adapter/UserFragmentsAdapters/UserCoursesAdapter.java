package com.careeranna.careeranna.adapter.UserFragmentsAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.data.Course;

import java.util.ArrayList;
import java.util.Random;

public class UserCoursesAdapter extends RecyclerView.Adapter<UserCoursesAdapter.UserCourses> {

    public static final String TAG = "UserCoursesFrag";

    private Context context;
    private ArrayList<String> course_names, course_ids, course_images;

    /*
    TODO: UserCoursesFragment -- All this list is dummy data, change it to show actual progress
     */

    public UserCoursesAdapter(Context context){
        this.context = context;
    }

    public void updateDataset(ArrayList<String> course_images,
                              ArrayList<String> course_names, ArrayList<String> course_ids){
        this.course_images = course_images;
        this.course_names = course_names;
        this.course_ids = course_ids;

        Log.d(TAG, "updateDataset: courses fetched = "+course_ids.size());
    }


    @NonNull
    @Override
    public UserCourses onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user_courses_list, viewGroup, false);

        course_names = new ArrayList<>();
        course_images = new ArrayList<>();
        course_ids = new ArrayList<>();

        return new UserCourses(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCourses userCourses, int i) {
        Random random = new Random();
        int randomProgress = random.nextInt(100);
        Glide.with(context)
                .load(course_images.get(i))
                .into(userCourses.iv_courseImage);
        userCourses.progressBar.setProgress(randomProgress);
        userCourses.tv_progress.setText(randomProgress+"%");
        if(randomProgress <= 20){
            userCourses.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_red));
        } else if(randomProgress <= 50){
            userCourses.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_yellow));
        } else if(randomProgress <= 70){
            userCourses.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular_dark_green));
        } else
        {
            userCourses.progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.circular));
        }
    }

    @Override
    public int getItemCount() {
        return course_names.size();
    }

    public class UserCourses extends RecyclerView.ViewHolder {

        ImageView iv_courseImage;
        ProgressBar progressBar;
        TextView tv_progress, tv_courseName, tv_units_completed;

        public UserCourses(@NonNull View itemView) {
            super(itemView);

            iv_courseImage = itemView.findViewById(R.id.item_user_course_image);
            progressBar = itemView.findViewById(R.id.item_user_course_progress_bar);
            tv_progress = itemView.findViewById(R.id.item_user_course_progress_percentage);
            tv_courseName = itemView.findViewById(R.id.item_user_course_name);
            tv_units_completed = itemView.findViewById(R.id.item_user_course_units_completed);
        }
    }

}
