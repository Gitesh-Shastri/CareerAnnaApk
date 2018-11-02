package com.careeranna.careeranna;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.careeranna.fragement.CertificateFragment;
import com.careeranna.careeranna.fragement.NotesFragment;
import com.careeranna.careeranna.fragement.TestFragment;
import com.careeranna.careeranna.fragement.TutorialFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParticularCourse extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    LinearLayout linearLayout;
    CircleImageView imageView;
    TextView username;
    FirebaseAuth mAuth;
    NavigationView navigationView;
    TutorialFragment tutorialFragment;
    NotesFragment notesFragment;
    TestFragment testFragment;
    CertificateFragment certificateFragment;

    String mUsername, profile_pic_url, mEmail;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_course);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawelayout1);

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        profile_pic_url = user.getPhotoUrl().toString();
        mUsername = user.getDisplayName().toString();
        String email = user.getEmail().toString();

        navigationView = (NavigationView)findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        CircleImageView profile = headerView.findViewById(R.id.navImage);
        TextView username, useremail;
        username = headerView.findViewById(R.id.navUsername);
        useremail = headerView.findViewById(R.id.navUseremail);
        Glide.with(this).load(profile_pic_url).into(profile);
        username.setText(mUsername);
        useremail.setText(email);

        getSupportActionBar().setTitle("Tutorial");
        navigationView.setCheckedItem(R.id.tutorial);

        tutorialFragment = new TutorialFragment();
        notesFragment = new NotesFragment();
        testFragment = new TestFragment();
        certificateFragment = new CertificateFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, tutorialFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.signout) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if(id == R.id.tutorial) {
            navigationView.setCheckedItem(R.id.tutorial);
            fragmentManager.beginTransaction().replace(R.id.main_content,tutorialFragment).commit();
            getSupportActionBar().setTitle("Tutorial");
        } else if(id == R.id.notes) {
            navigationView.setCheckedItem(R.id.notes);
            fragmentManager.beginTransaction().replace(R.id.main_content,notesFragment).commit();
            getSupportActionBar().setTitle("Notes");
        } else if(id == R.id.test) {
            navigationView.setCheckedItem(R.id.test);
            fragmentManager.beginTransaction().replace(R.id.main_content,testFragment).commit();
            getSupportActionBar().setTitle("Test");
        } else if(id == R.id.certificate) {
            navigationView.setCheckedItem(R.id.certificate);
            fragmentManager.beginTransaction().replace(R.id.main_content,certificateFragment).commit();
            getSupportActionBar().setTitle("Certificate");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
