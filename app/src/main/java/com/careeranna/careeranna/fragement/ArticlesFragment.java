package com.careeranna.careeranna.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.careeranna.careeranna.ParticularArticleActivity;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.ArticleAdapter;
import com.careeranna.careeranna.data.Article;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ArticlesFragment extends Fragment implements ArticleAdapter.OnItemClickListener {

    ArrayList<Article> mArticles;

    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;

    public ArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_articles, container, false);

        recyclerView = view.findViewById(R.id.article_rv);

        populateArticles();

        return view;
    }

    public void populateArticles() {

        mArticles = new ArrayList<>();

        Article article = new Article("1", "IIM Banglore Admission", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg", "Nishta Sood", "CAT", "IIM banglore has a selection for admission of the students in their Admission Programme", "November 6, 2018");
        Article article1 = new Article("2", "IIM Delhi Admission", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg", "Gitesh Shastri", "CAT", "IIM Delhi has a selection for admission of the students in their Admission Programme", "November 7, 2018");

        mArticles.add(article);
        mArticles.add(article1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        articleAdapter = new ArticleAdapter(mArticles, getApplicationContext());
        recyclerView.setAdapter(articleAdapter);

        articleAdapter.setOnItemClicklistener(this);
    }

    @Override
    public void onItemClick1(int position) {
        Intent intent = new Intent(getApplicationContext(), ParticularArticleActivity.class);
        intent.putExtra("article", mArticles.get(position));
        getContext().startActivity(intent);
        Toast.makeText(getApplicationContext(), "Article Title : " + mArticles.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
