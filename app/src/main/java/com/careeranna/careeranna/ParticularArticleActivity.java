package com.careeranna.careeranna;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.careeranna.careeranna.data.Article;
import com.careeranna.careeranna.data.ISO8601Parse;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

import static com.google.ads.interactivemedia.v3.impl.w.c.error;


public class ParticularArticleActivity extends AppCompatActivity {

    Article article;
    TextView articleName, articleAuthor, articleContent, articleCategory;
    ImageView articleImage;

    ProgressBar progressBar;

    TextView articleCreated;

    WebView webview;

    WebSettings webSettings;

    boolean showandhiden;

    FloatingActionButton fab, fab_google, fab_facebook, fab_linkedin;

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_article);

        fab = findViewById(R.id.fab_menu);
        fab_google = findViewById(R.id.fab_google);
        fab_facebook = findViewById(R.id.fab_facebook);
        fab_linkedin = findViewById(R.id.fab_twitter);

        relativeLayout = findViewById(R.id.relative);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        articleName = findViewById(R.id.particle_name);
        articleAuthor = findViewById(R.id.particle_author);
        articleCreated = findViewById(R.id.particle_created);
        articleCategory = findViewById(R.id.particle_category);
        articleImage = findViewById(R.id.particle_image);
        webview = findViewById(R.id.webview);

        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);


        Snackbar.make(relativeLayout, "Loading Content", Snackbar.LENGTH_SHORT).show();

        article = (Article) getIntent().getSerializableExtra("article");

        String desc = "Organizations of all sizes and Industries, be it a financial institution or a small big data start up, everyone is using Python for their business.\n" +
                "Python is among the popular data science programming languages not only in Big data companies but also in the tech start up crowd. Around 46% of data scientists use Python.\n" +
                "Python has overtaken Java as the preferred programming language and is only second to SQL in usage today. \n" +
                "Python is finding Increased adoption in numerical computations, machine learning and several data science applications.\n" +
                "Python for data science requires data scientists to learn the usage of regular expressions, work with the scientific libraries and master the data visualization concepts.";


        Glide.with(this).load(article.getImage_url()).into(articleImage);
        articleName.setText(article.getName());
        articleAuthor.setText(article.getAuthor());
        articleCreated.setText(article.getCreated_at().substring(0, 10));
        articleCategory.setText("Category : " + article.getCategory());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://careeranna.in/particularArticle.php?id="+article.getId();
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("url_response", response.toString());
                        String html = "<h3>"+article.getName()+"</h3><hr/><h5>By : "+article.getAuthor()+"</h5>"+response.toString();
                        webview.loadData(html, "text/html", null);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        requestQueue.add(stringRequest);


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
