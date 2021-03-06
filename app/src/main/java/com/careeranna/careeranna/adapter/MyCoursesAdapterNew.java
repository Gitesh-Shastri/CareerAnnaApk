package com.careeranna.careeranna.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.helper.RecyclerViewCoursesAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MyCoursesAdapterNew extends RecyclerView.Adapter<MyCoursesAdapterNew.ViewHolder>{

    private Context mContext;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();

    public MyCoursesAdapterNew(Context mContext, ArrayList<String> names, ArrayList<String> urls) {
        this.mContext = mContext;
        this.names = names;
        this.urls = urls;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClicklistener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_course_new, viewGroup, false);
        return new ViewHolder(view);
    }

    int delayAnimate = 300; //global variable

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //set view to INVISIBLE before animate
        Random rand = new Random();
        int random  = rand.nextInt(100);
        Glide.with(mContext)
                .load(urls.get(position))
                .into(viewHolder.imageView);
        viewHolder.textView.setText(names.get(position));
        viewHolder.progressBar.setProgress(random);
        viewHolder.tv.setText(random+"%");
        if(random <= 20) {
            viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.circular_red));
        } else if(random <= 50){
            viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.circular_yellow));
        } else if(random <=70) {
            viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.circular_dark_green));
        } else {
            viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.circular));
        }

    }

    private void setAnimation(final View view) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
                if(view!=null){
                    view.startAnimation(animation);
                    view.setVisibility(View.VISIBLE);
                }
            }
        }, delayAnimate);
        delayAnimate+=300;
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, tv;

        Button go_inside;

        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.name);
            progressBar = itemView.findViewById(R.id.progress);
            tv = itemView.findViewById(R.id.tv);

            go_inside = itemView.findViewById(R.id.continu);

            go_inside.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mListener != null) {
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick(position);
                            }
                        }
                    }
                });
            }
        }
    }

