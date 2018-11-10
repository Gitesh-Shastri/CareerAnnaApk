package com.careeranna.careeranna.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.MainActivity;
import com.careeranna.careeranna.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {

    ImageView profile_pic;
    TextView mUserName, mEmail;
    Button signOut;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String username, profile_pic_url, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        profile_pic = findViewById(R.id.profile_pic);
        mUserName = findViewById(R.id.username);
        mEmail = findViewById(R.id.useremail);
        signOut = findViewById(R.id.signOut);

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
        email = user.getEmail().toString();
        Glide.with(MyProfile.this)
            .load(profile_pic_url)
                .into(profile_pic);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(MyProfile.this, MainActivity.class));
                finish();
            }
        });

        mUserName.setText(username);
        mEmail.setText(email);
    }
}
