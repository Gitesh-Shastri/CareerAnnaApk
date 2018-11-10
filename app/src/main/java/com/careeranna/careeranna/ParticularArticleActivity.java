package com.careeranna.careeranna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.data.Article;
import com.github.clans.fab.FloatingActionButton;

public class ParticularArticleActivity extends AppCompatActivity {

    Article article;
    TextView articleName, articleAuthor, articleCreated, articleContent, articleCategory;
    ImageView articleImage;

    FloatingActionButton google, facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_article);

        google = findViewById(R.id.google_fab);
        facebook = findViewById(R.id.fb_fab);

        articleName = findViewById(R.id.particle_name);
        articleAuthor = findViewById(R.id.particle_author);
        articleCreated = findViewById(R.id.particle_created);
        articleContent = findViewById(R.id.particle_content);
        articleCategory = findViewById(R.id.particle_category);
        articleImage = findViewById(R.id.particle_image);

        article = (Article) getIntent().getSerializableExtra("article");


        String desc = "Organizations of all sizes and Industries, be it a financial institution or a small big data start up, everyone is using Python for their business.\n" +
                "Python is among the popular data science programming languages not only in Big data companies but also in the tech start up crowd. Around 46% of data scientists use Python.\n" +
                "Python has overtaken Java as the preferred programming language and is only second to SQL in usage today. \n" +
                "Python is finding Increased adoption in numerical computations, machine learning and several data science applications.\n" +
                "Python for data science requires data scientists to learn the usage of regular expressions, work with the scientific libraries and master the data visualization concepts.";

        Glide.with(this).load(article.getImage_url()).into(articleImage);
        articleName.setText(article.getName());
        articleAuthor.setText(article.getAuthor());
        articleCreated.setText(article.getCreated_at());
        articleContent.setText(desc);
        articleCategory.setText("Category : " + article.getCategory());

    }
}
