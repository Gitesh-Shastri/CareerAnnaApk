package com.careeranna.careeranna.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.data.Article;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private ArrayList<Article> mArticles;
    private Context mContext;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick1(int position);
    }

    public void setOnItemClicklistener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ArticleAdapter(ArrayList<Article> mArticles, Context mContext) {
        this.mArticles = mArticles;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_article, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {
        articleViewHolder.articleTitle.setText(mArticles.get(i).getName());
        articleViewHolder.articleAuthor.setText(mArticles.get(i).getAuthor());
        articleViewHolder.articleCreated.setText(mArticles.get(i).getCreated_at());
        articleViewHolder.articleAuthor.setText(mArticles.get(i).getAuthor());
        articleViewHolder.articleContent.setText(mArticles.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView articleTitle, articleAuthor, articleCreated, articleContent;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            articleTitle = itemView.findViewById(R.id.article_name);
            articleAuthor = itemView.findViewById(R.id.article_author);
            articleCreated = itemView.findViewById(R.id.article_created);
            articleContent = itemView.findViewById(R.id.article_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick1(position);
                        }
                    }
                }
            });
        }
    }
}
