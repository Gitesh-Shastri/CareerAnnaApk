package com.careeranna.careeranna.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.MainActivity;
import com.careeranna.careeranna.R;
import com.careeranna.careeranna.adapter.MyCerti_Adapter;
import com.careeranna.careeranna.adapter.MyCourses_Adapter;
import com.careeranna.careeranna.dummy_data.ProfileCerti;
import com.careeranna.careeranna.dummy_data.ProfileCourses;
import com.careeranna.careeranna.misc.AnimationEffect;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_profilePic;
    TextView tv_profileName;
    LinearLayout ll_myCoursesHeading, ll_myCertiHeading;
    String username, profile_pic_url, email;
    Button bt_signOut;

    RecyclerView rv_myCourses, rv_myCertis;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

       iv_profilePic = findViewById(R.id.iv_profileImage);
       tv_profileName = findViewById(R.id.tv_profileName);
       ll_myCoursesHeading = findViewById(R.id.ll_myCoursesHeading);
       ll_myCertiHeading = findViewById(R.id.ll_myCertiHeading);
       bt_signOut = findViewById(R.id.bt_profileSignOut);
       rv_myCourses = findViewById(R.id.rv_myCourses);
       rv_myCertis = findViewById(R.id.rv_myCerti);

       ll_myCoursesHeading.setOnClickListener(this);
       ll_myCertiHeading.setOnClickListener(this);
       bt_signOut.setOnClickListener(this);

       /*
       Dummy data only for testing purpose
        */
        ArrayList<ProfileCourses> listCourses = new ArrayList<>();
        listCourses.add(new ProfileCourses(
                "Python Web Dev Course",
                30,
                ""
        ));
        listCourses.add(new ProfileCourses(
                "Android with Java",
                70,
                ""
        ));
        listCourses.add(new ProfileCourses(
                "Online Marketing Course",
                0,
                ""
        ));

        List<ProfileCerti> listCerti = new ArrayList<>();
        listCerti.add(new ProfileCerti(
                "Web Dev with PHP",
                "15 Sept 2018",
                "",
                ""
        ));
        listCerti.add(new ProfileCerti(
                "Business Management",
                "10 July 2018",
                "",
                ""
        ));
        //********** DUMMY DATA ENDS**************************

        MyCourses_Adapter myCoursesAdapter = new MyCourses_Adapter(this, listCourses);
        rv_myCourses.setLayoutManager(new LinearLayoutManager(this));
        rv_myCourses.setItemAnimator(new DefaultItemAnimator());
        rv_myCourses.setAdapter(myCoursesAdapter);

        MyCerti_Adapter myCertiAdapter = new MyCerti_Adapter(this, listCerti);
        rv_myCertis.setLayoutManager(new LinearLayoutManager(this));
        rv_myCertis.setItemAnimator(new DefaultItemAnimator());
        rv_myCertis.setAdapter(myCertiAdapter);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MyProfile.this, MainActivity.class));
                }
            }
        };

        FirebaseUser user = mAuth.getCurrentUser();
        profile_pic_url = user.getPhotoUrl().toString();
        username = user.getDisplayName().toString();

        /*
        Fill all the views with the data derived from the firebase
        user element.
        Fill the profile pic, the user's name.
         */
        Glide.with(MyProfile.this)
                .load(profile_pic_url)
                .into(iv_profilePic);

        tv_profileName.setText(username);
        rv_myCourses.setVisibility(View.GONE);
        rv_myCertis.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_myCoursesHeading:
                if(!rv_myCourses.isShown()){
                    AnimationEffect.slide_down(this, rv_myCourses);
                    rv_myCourses.setVisibility(View.VISIBLE);
                } else {
                    AnimationEffect.slide_up(this, rv_myCourses);
                }
                break;

            case R.id.ll_myCertiHeading:
                if(!rv_myCertis.isShown()){
                    AnimationEffect.slide_down(this, rv_myCertis);
                    rv_myCertis.setVisibility(View.VISIBLE);
                } else {
                    AnimationEffect.slide_up(this, rv_myCertis);
                }
                break;

            case R.id.bt_profileSignOut:
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(MyProfile.this, MainActivity.class));
                finish();
                break;
        }
    }
}
