package com.careeranna.careeranna;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.configuration.PlayerConfig;

public class ExploreCourses extends AppCompatActivity {

    TextView price,desc;
    String course;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_courses);
        price = findViewById(R.id.dollar);
        videoView =  findViewById(R.id.playerView);
        desc = findViewById(R.id.descTextDetails);
        String videoPath = "android.resource://com.careeranna.careeranna/"+R.raw.video;
        Uri uri = Uri.parse(videoPath);

        MediaController mediaController = new MediaController(this);
        videoView.setVideoPath(uri.toString());
        videoView.setMediaController(mediaController);

        videoView.start();
        
        desc.setText("Organizations of all sizes and Industries, be it a financial institution or a small big data start up, everyone is using Python for their business.\n" +
                "Python is among the popular data science programming languages not only in Big data companies but also in the tech start up crowd. Around 46% of data scientists use Python.\n" +
                "Python has overtaken Java as the preferred programming language and is only second to SQL in usage today. \n" +
                "Python is finding Increased adoption in numerical computations, machine learning and several data science applications.\n" +
                "Python for data science requires data scientists to learn the usage of regular expressions, work with the scientific libraries and master the data visualization concepts.");
        price.setText("6999");

        course = getIntent().getStringExtra("course_name");
        getSupportActionBar().setTitle(course);

    }

    public void purchase(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Are You Sure You Want To Buy This Course");
        mBuilder.setCancelable(false);
        mBuilder.setMessage("Course Name : " + course +" \n Price : 6999");
        mBuilder.setPositiveButton("Go To Cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = mBuilder.show();
    }
}
