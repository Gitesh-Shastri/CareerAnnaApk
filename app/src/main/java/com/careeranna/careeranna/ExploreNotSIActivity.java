package com.careeranna.careeranna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ExploreNotSIActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout freeVidLayout, trendingVidLayout, topCoursesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_not_si);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(
                    Html.fromHtml("<font color='#FFFFFF'> Featured </font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        topCoursesLayout = findViewById(R.id.ll_top);
        freeVidLayout = findViewById(R.id.ll_free_vid);
        trendingVidLayout = findViewById(R.id.ll_trending);

        LayoutInflater layoutInflater = LayoutInflater.from(this);

        /*
        Only for testing purpose, to check how the activity will look like when the actual video
        thumbnails will be shown.
        ********************************************************************************************
         */
        for(int i=0; i<10; i++){
            View view = layoutInflater.inflate(R.layout.video_thumbnail, topCoursesLayout, false);

            TextView title = view.findViewById(R.id.tv_vid_thumbnail_title);
            ImageView imageView = view.findViewById(R.id.iv_vid_thumbnail_img);

            title.setText("Video "+i);
            imageView.setImageResource(R.mipmap.ic_launcher);

            topCoursesLayout.addView(view);
        }

        for(int i=0; i<10; i++){
            View view = layoutInflater.inflate(R.layout.video_thumbnail, freeVidLayout, false);

            TextView title = view.findViewById(R.id.tv_vid_thumbnail_title);
            ImageView imageView = view.findViewById(R.id.iv_vid_thumbnail_img);

            title.setText("Video "+i);
            imageView.setImageResource(R.mipmap.ic_launcher);

            freeVidLayout.addView(view);
        }

        for(int i=0; i<10; i++){
            View view = layoutInflater.inflate(R.layout.video_thumbnail,trendingVidLayout, false);

            TextView title = view.findViewById(R.id.tv_vid_thumbnail_title);
            ImageView imageView = view.findViewById(R.id.iv_vid_thumbnail_img);

            title.setText("Video "+i);
            imageView.setImageResource(R.mipmap.ic_launcher);

            trendingVidLayout.addView(view);
        }

        // *************ONLY FOR TESTING PURPOSE CODE, ENDS HERE***********************************
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signin_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_signin:
                Intent signInActivity = new Intent(this, SignUp.class);
                startActivity(signInActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
