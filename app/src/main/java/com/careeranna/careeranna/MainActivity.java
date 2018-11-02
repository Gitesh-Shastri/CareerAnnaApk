package com.careeranna.careeranna;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.careeranna.careeranna.fragement.ExploreFragement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.signIn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signIn: Intent intent = new Intent(this, SignUp.class);
                              startActivity(intent);
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
}
