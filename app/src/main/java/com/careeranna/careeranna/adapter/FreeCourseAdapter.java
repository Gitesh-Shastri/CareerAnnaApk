package com.careeranna.careeranna.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.data.FreeVideos;

import java.util.ArrayList;

public class FreeCourseAdapter extends RecyclerView.Adapter<FreeCourseAdapter.ViewHolder> {

    private ArrayList<FreeVideos> freeVideos;
    private Context mContext;

    private ArticleAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick1(int position);
    }

    public void setOnItemClicklistener(ArticleAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public FreeCourseAdapter(ArrayList<FreeVideos> freeVideos, Context mContext) {
        this.freeVideos = freeVideos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_free_course_new, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return freeVideos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
