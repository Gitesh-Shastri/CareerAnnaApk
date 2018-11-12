package com.careeranna.careeranna;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.data.Article;
import com.careeranna.careeranna.data.ISO8601Parse;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.text.ParseException;
import java.util.Date;


public class ParticularArticleActivity extends AppCompatActivity {

    Article article;
    TextView articleName, articleAuthor, articleContent, articleCategory;
    ImageView articleImage;

    RelativeTimeTextView articleCreated;

    boolean showandhiden;

    FloatingActionButton fab, fab_google, fab_facebook, fab_linkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_article);

        fab = findViewById(R.id.fab_menu);
        fab_google = findViewById(R.id.fab_google);
        fab_facebook = findViewById(R.id.fab_facebook);
        fab_linkedin = findViewById(R.id.fab_twitter);

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


        Date date = null;

        try {
            date = ISO8601Parse.parse(article.getCreated_at());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(this).load(article.getImage_url()).into(articleImage);
        articleName.setText(article.getName());
        articleAuthor.setText(article.getAuthor());
        articleCreated.setReferenceTime(date.getTime());
        articleContent.setText(desc);
        articleCategory.setText("Category : " + article.getCategory());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showandhiden) {
                    hiden();
                    showandhiden = false;
                } else {
                    shown();
                    showandhiden = true;
                }
            }
        });
    }

    public void hiden() {

        fab_facebook.show();
        fab_google.show();
        fab_linkedin.show();
    }

    public void shown() {

        fab_facebook.hide();
        fab_google.hide();
        fab_linkedin.hide();
    }
}
