package com.careeranna.careeranna;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.careeranna.careeranna.adapter.SlideAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    public static final String TAG = "MainAct";

    private LinearLayout dotsLayout;
    private ViewPager introSlider;
    private SlideAdapter slideAdapter;

    private Button bt_explore;
    private Button bt_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.signIn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        dotsLayout = findViewById(R.id.intro_dots);
        introSlider = findViewById(R.id.intro_viewpager);

        slideAdapter = new SlideAdapter(this);
        introSlider.setAdapter(slideAdapter);
        addDots(0);

        introSlider.addOnPageChangeListener(viewListener);

        bt_explore = findViewById(R.id.bt_explore);
        bt_signin = findViewById(R.id.signIn);

        bt_explore.setOnClickListener(this);
        bt_signin.setOnClickListener(this);
    }

    private void addDots(int i){
        dotsLayout.removeAllViews();
        TextView[] dots = new TextView[3];

        for(int x=0; x<dots.length; x++){
            dots[x] = new TextView(this);
            dots[x].setText(String.valueOf(Html.fromHtml("&#8226")));
            dots[x].setTextSize(40);
            dots[x].setTextColor(getResources().getColor(R.color.intro_dot_dark));

            Log.d(TAG, "addDots: "+x);
            dotsLayout.addView(dots[x]);
        }

        dots[i].setTextColor(getResources().getColor(R.color.intro_dot_light));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signIn:
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;

            case R.id.bt_explore:
                Intent openExplorePage = new Intent(this, ExploreNotSIActivity.class);
                startActivity(openExplorePage);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        if(user != null) {
            String username = user.getDisplayName();
            String email = user.getEmail();
            Uri url = user.getPhotoUrl();
            Intent intent = new Intent(MainActivity.this, MyCourses.class);
            intent.putExtra("username", username);
            intent.putExtra("useremail", email);
            intent.putExtra("pic", url.toString());
            startActivity(intent);
            finish();
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
