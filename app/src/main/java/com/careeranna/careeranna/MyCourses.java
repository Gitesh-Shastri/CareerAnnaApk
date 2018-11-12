package com.careeranna.careeranna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.careeranna.careeranna.adapter.ViewPagerAdapter;
import com.careeranna.careeranna.data.Banner;
import com.careeranna.careeranna.fragement.dashboard_fragements.ExamPrepFragment;
import com.careeranna.careeranna.user.MyProfile;
import com.careeranna.careeranna.fragement.dashboard_fragements.ArticlesFragment;
import com.careeranna.careeranna.fragement.dashboard_fragements.ExploreFragement;
import com.careeranna.careeranna.fragement.dashboard_fragements.MyCoursesFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyCourses extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mToggle;

    LinearLayout linearLayout;

    CircleImageView imageView;
    TextView username;

    FirebaseAuth mAuth;

    String mUsername, profile_pic_url, mEmail;

    ExploreFragement myExplorerFragement;
    MyCoursesFragment myCoursesFragement;
    ArticlesFragment myArticleFragment;
    ExamPrepFragment myExamPrepFragment;

    FragmentManager fragmentManager;

    ArrayList<Banner> mBanners;

    Button myCourses;

    int page = 0;

    ViewPager viewPager;

    ProgressDialog progressDialog;

    ViewPagerAdapter viewPagerAdapter;

    private int currentPage;
    private Handler handler;
    private int delay = 5000;
    Runnable runnable;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_couses);

        //  Initialize Layout Variable
        drawerLayout = findViewById(R.id.drawelayout);
        navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.sliderDots);

        // Binding NavigationView To Toolbar
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        profile_pic_url = user.getPhotoUrl().toString();
        mUsername = user.getDisplayName();
        mEmail = user.getEmail();

        // Initalize Fragements For main container
        myCoursesFragement = new MyCoursesFragment();
        myExplorerFragement = new ExploreFragement();
        myArticleFragment = new ArticlesFragment();
        myExamPrepFragment = new ExamPrepFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, myCoursesFragement).commit();

        // Set Navigation View Information
        setNavigationView();

        // Setting Banner Information
        getBanner();

        // Runnable For banner for changing in banner
        handler = new Handler();
    }

    public void getBanner() {

        mBanners = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Banner Please Wait ... ");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.com/bins/te3gu";
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("url_response", response.toString());
                            JSONObject bannerObject = new JSONObject(response.toString());
                            JSONArray bannerArray = bannerObject.getJSONArray("banner");
                            for(int i=0;i<bannerArray.length();i++) {
                                JSONObject banner = bannerArray.getJSONObject(i);
                                mBanners.add(new Banner(banner.getString("id"),
                                        banner.getString("title"),
                                        banner.getString("image_url")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), mBanners);
                        viewPager.setAdapter(viewPagerAdapter);
                        // Initializing dots for swipping banner layout
                        viewPager.addOnPageChangeListener(bannerListener);
                        currentPage = 0;
                        addDots(0);

                        makeRunnable();

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    public void makeRunnable() {
        runnable = new Runnable() {
            public void run() {
                if (viewPagerAdapter.getCount() == page) {
                    page = 0;
                } else {
                    page++;
                }
                viewPager.setCurrentItem(page, true);
                handler.postDelayed(this, delay);
            }
        };
    }

    private void addDots(int i){

        linearLayout.removeAllViews();
        TextView[] dots = new TextView[viewPagerAdapter.getCount()];

        for(int x=0; x<dots.length; x++){
            dots[x] = new TextView(this);
            dots[x].setText(String.valueOf(Html.fromHtml("&#8226")));
            dots[x].setTextSize(40);
            dots[x].setTextColor(getResources().getColor(R.color.intro_dot_dark));

            linearLayout.addView(dots[x]);
        }

        dots[i].setTextColor(getResources().getColor(R.color.intro_dot_light));
    }

    ViewPager.OnPageChangeListener bannerListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
            currentPage = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    public void setNavigationView() {

        // Initialize Views for navigation
        TextView username, useremail;
        CircleImageView profile;

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        profile = headerView.findViewById(R.id.navImage);
        username = headerView.findViewById(R.id.navUsername);
        useremail = headerView.findViewById(R.id.navUseremail);

        Glide.with(this).load(profile_pic_url).into(profile);
        username.setText(mUsername);
        useremail.setText(mEmail);

        navigationView.setCheckedItem(R.id.myCourses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {

        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public void signOut(View view) {

        mAuth.signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if(id == R.id.signOut) {

            mAuth.signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else if(id == R.id.particularCourses) {

            Intent intent = new Intent(MyCourses.this, ParticularCourse.class);
            intent.putExtra("category_name",
                    "Machine Learning");
            intent.putExtra("category_image",
                    "https://4.bp.blogspot.com/-qf3t5bKLvUE/WfwT-s2IHmI/AAAAAAAABJE/RTy60uoIDCoVYzaRd4GtxCeXrj1zAwVAQCLcBGAs/s1600/Machine-Learning.png");;
            startActivity(intent);
        } else if(id == R.id.myCourses) {

            fragmentManager.beginTransaction().replace(R.id.main_content, myCoursesFragement).commit();
            navigationView.setCheckedItem(R.id.myCourses);
            getSupportActionBar().setTitle("My Courses");

        } else if(id == R.id.examprep) {

            fragmentManager.beginTransaction().replace(R.id.main_content, myExamPrepFragment).commit();
            navigationView.setCheckedItem(R.id.examprep);
            getSupportActionBar().setTitle("Examp Prep");
        }else if(id == R.id.explore) {

            fragmentManager.beginTransaction().replace(R.id.main_content, myExplorerFragement).commit();
            navigationView.setCheckedItem(R.id.explore);
            getSupportActionBar().setTitle("Explorer");

        } else if(id == R.id.article) {

            fragmentManager.beginTransaction().replace(R.id.main_content, myArticleFragment).commit();
            navigationView.setCheckedItem(R.id.article);
            getSupportActionBar().setTitle("Articles");

        }else if(id == R.id.profile) {

            startActivity(new Intent(this, MyProfile.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
