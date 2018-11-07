package com.careeranna.careeranna;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.careeranna.careeranna.adapter.ViewPagerAdapter;
import com.careeranna.careeranna.data.Banner;
import com.careeranna.careeranna.fragement.ExploreFragement;
import com.careeranna.careeranna.fragement.MyCoursesFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
    FragmentManager fragmentManager;
    Button myCourses,myExplorer;

    ArrayList<Banner> mBanners;
    private ArrayList<String> names;
    private ArrayList<String> urls;

    private String[] imageUrls = new String[] {
            "https://4.bp.blogspot.com/-qf3t5bKLvUE/WfwT-s2IHmI/AAAAAAAABJE/RTy60uoIDCoVYzaRd4GtxCeXrj1zAwVAQCLcBGAs/s1600/Machine-Learning.png",
            "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg",
            "https://www.digitalvidya.com/wp-content/uploads/2016/02/Master_Digital_marketng-1170x630.jpg"
    };

    int dotsCount;
    int page = 0;
    private ImageView[] dots;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    private Handler handler;
    private int delay = 5000;
    Runnable runnable;

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_couses);

        //  Initialize Layout Variable
        drawerLayout = (DrawerLayout)findViewById(R.id.drawelayout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
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
        mUsername = user.getDisplayName().toString();
        mEmail = user.getEmail().toString();

        // Initalize Fragements For main container
        myCoursesFragement = new MyCoursesFragment();
        myExplorerFragement = new ExploreFragement();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, myCoursesFragement).commit();

        // Set Navigation View Information
        setNavigationView();

        // Setting Banner Information
        initializeBanner();

        names = new ArrayList<>();
        urls = new ArrayList<>();

        urls.add(imageUrls[0]);
        names.add("Machine Learning");
        urls.add(imageUrls[1]);
        names.add("Python");
        urls.add(imageUrls[2]);
        names.add("Marketing");
        urls.add(imageUrls[0]);
        names.add("Machine Learning");
        urls.add(imageUrls[1]);
        names.add("Python");
        urls.add(imageUrls[2]);
        names.add("Marketing");

        // Runnable For banner for changing in banner
        handler = new Handler();
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

    public void initializeBanner() {
        // Initializing ArrayList
        mBanners = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(MyCourses.this);
        String bannerUrl = "https://api.myjson.com/bins/qzwja";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, bannerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("")) {
                            try {
                                JSONArray bannerArray = new JSONArray(response.toString());
                                for(int i=0;i<bannerArray.length();i++) {
                                    JSONObject bannerObject = bannerArray.getJSONObject(i);
                                    mBanners.add(new Banner(bannerObject.getString("id"),
                                            bannerObject.getString("title"),
                                            bannerObject.getString("image_url")
                                            ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyCourses.this, "Something has happened " + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(stringRequest);

        // Setting Adapter for banner viewPage
        viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), mBanners);
        viewPager.setAdapter(viewPagerAdapter);

        // Initializing dots for swipping banner layout
        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        // Setting Non Active and active dots
        for(int i=0;i<dotsCount;i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_panorama_fish_eye_black_24dp));
            linearLayout.addView(dots[i]);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fiber_manual_record_black_24dp));

        // Changing of active and Non active dots after swipping
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for(int j=0;j< dotsCount;j++) {
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_panorama_fish_eye_black_24dp));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fiber_manual_record_black_24dp));
                page = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

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
        } else if(id == R.id.myCourses) {
            fragmentManager.beginTransaction().replace(R.id.main_content, myCoursesFragement).commit();
            navigationView.setCheckedItem(R.id.myCourses);
            getSupportActionBar().setTitle("My Courses");
        } else if(id == R.id.explore) {
            fragmentManager.beginTransaction().replace(R.id.main_content, myExplorerFragement).commit();
            navigationView.setCheckedItem(R.id.explore);
            getSupportActionBar().setTitle("Explorer");
        } else if(id == R.id.profile) {
            startActivity(new Intent(this, MyProfile.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
