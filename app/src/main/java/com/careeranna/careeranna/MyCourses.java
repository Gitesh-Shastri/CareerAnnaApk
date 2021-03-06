package com.careeranna.careeranna;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.careeranna.careeranna.adapter.FreeCourseAdapter;
import com.careeranna.careeranna.adapter.ListViewAdapter;
import com.careeranna.careeranna.adapter.ViewPagerAdapter;
import com.careeranna.careeranna.data.Article;
import com.careeranna.careeranna.data.Banner;
import com.careeranna.careeranna.data.Category;
import com.careeranna.careeranna.data.Course;
import com.careeranna.careeranna.data.ExamPrep;
import com.careeranna.careeranna.data.SubCategory;
import com.careeranna.careeranna.data.User;
import com.careeranna.careeranna.fragement.dashboard_fragements.CategoryFragment;
import com.careeranna.careeranna.fragement.dashboard_fragements.ExamPrepFragment;
import com.careeranna.careeranna.fragement.dashboard_fragements.ExploreNew;
import com.careeranna.careeranna.helper.CountDrawable;
import com.careeranna.careeranna.user.MyProfile;
import com.careeranna.careeranna.fragement.dashboard_fragements.ArticlesFragment;
import com.careeranna.careeranna.fragement.dashboard_fragements.ExploreFragement;
import com.careeranna.careeranna.fragement.dashboard_fragements.MyCoursesFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyCourses extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MyCourses";

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mToggle;

    Menu menu;

    LinearLayout linearLayout;

    CircleImageView imageView;
    TextView username;

    FirebaseAuth mAuth;

    String mUsername, profile_pic_url, mEmail;

    ExploreNew exploreNew;
    ExploreFragement myExplorerFragement;
    MyCoursesFragment myCoursesFragement;
    ArticlesFragment myArticleFragment;
    ExamPrepFragment myExamPrepFragment;
    CategoryFragment categoryFragment;


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

    User user;

    ArrayList<Category> categories;
    ArrayList<Course> courses, freecourse;
    ArrayList<ExamPrep> examPreps;
    ArrayList<Article> mArticles;

    private long backPressed;

    NavigationView navigationView;

    ArrayList<String> names, urls, ids;

    ListView listView;

    @Override
    public void onBackPressed() {

        if(backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Please Click Again To Exit !", Toast.LENGTH_SHORT).show();
        }

        backPressed = System.currentTimeMillis();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        this.menu = menu;

        String cart = Paper.book().read("cart");

        if(cart != null && !cart.isEmpty()) {
            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<String>>() {}.getType();

            ArrayList<String> arrayList = gson.fromJson(cart, type);

            setCount(this, arrayList.size()+"");

        } else {
            setCount(this, "0");
        }
        return true;
    }


    public void setCount(Context context, String count) {
        MenuItem menuItem = menu.findItem(R.id.add_to_cart);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_couses);

        Log.d(TAG, "onCreate: ");
        //  Initialize Layout Variable
        drawerLayout = findViewById(R.id.drawelayout);
        navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.sliderDots);

        Paper.init(this);


        listView = findViewById(R.id.list_menu_items);


        final ArrayList<String> your_array_list = new ArrayList<String>();
        your_array_list.add("MBA");
        your_array_list.add("Banking");
        your_array_list.add("Professional Development");
        your_array_list.add("General Knowledge");
        your_array_list.add("Govt. Jobs");
        your_array_list.add("Law");
        your_array_list.add("Finance");
        your_array_list.add("Marketing");
        your_array_list.add("Certificate Courses");
        your_array_list.add("My Courses");
        your_array_list.add("Explore");
        your_array_list.add("Articles");
        your_array_list.add("Our Mentors");
        your_array_list.add("My Profile");
        your_array_list.add("Sign Out");

        ListViewAdapter adapter = new ListViewAdapter(this, your_array_list);
        //
        //// Attach the adapter to a ListView
        //
        //ListView listView = (ListView) findViewById(R.id.lvItems);
        //
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        categoryFragment = new CategoryFragment();
                        categoryFragment.addSubCategory("1", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("MBA");
                        break;
                    case 1:
                        categoryFragment = new CategoryFragment();

                        categoryFragment.addSubCategory("2", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("Banking");
                        break;
                    case 2:
                        categoryFragment = new CategoryFragment();

                        categoryFragment.addSubCategory("5", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("Professional Development");
                        break;
                        case 3:
                        categoryFragment = new CategoryFragment();

                        categoryFragment.addSubCategory("6", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("General Knowledge");
                            break;
                        case 4:
                        categoryFragment = new CategoryFragment();

                        categoryFragment.addSubCategory("9", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("Govt. Jobs");
                            break;
                    case 5:
                        categoryFragment = new CategoryFragment();
                        categoryFragment.addSubCategory("12", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("Law");
                        break;
                    case 6:
                        categoryFragment = new CategoryFragment();
                        categoryFragment.addSubCategory("13", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("Finance");
                        break;
                    case 7:
                        categoryFragment = new CategoryFragment();
                        categoryFragment.addSubCategory("14", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        navigationView.setCheckedItem(R.id.marketing);
                        getSupportActionBar().setTitle("Marketing");
                        break;
                    case 8:
                        categoryFragment = new CategoryFragment();
                        categoryFragment.addSubCategory("15", user.getUser_id());

                        fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
                        getSupportActionBar().setTitle("Certificate Courses");
                        break;
                    case 9:
                        myCourse();
                        fragmentManager.beginTransaction().replace(R.id.main_content, myCoursesFragement).commit();
                        getSupportActionBar().setTitle("My Courses");
                        break;
                    case 10:
                        fragmentManager.beginTransaction().replace(R.id.main_content, exploreNew).commit();
                        getSupportActionBar().setTitle("Explorer");

                        addExam();
                        break;
                    case 11:
                        initArticle();
                        fragmentManager.beginTransaction().replace(R.id.main_content, myArticleFragment).commit();
                        getSupportActionBar().setTitle("Articles");
                        break;
                    case 13:
                        startActivity(new Intent(MyCourses.this, MyProfile.class));
                        break;
                    case 14:
                        mAuth = FirebaseAuth.getInstance();
                        if(mAuth != null) {
                            mAuth.signOut();
                            LoginManager.getInstance().logOut();
                        }
                        Paper.delete("user");
                        startActivity(new Intent(MyCourses.this, MainActivity.class));
                        finish();
                        break;
                }
                String cart = Paper.book().read("cart");

                if(cart != null && !cart.isEmpty()) {
                    Gson gson = new Gson();

                    Type type = new TypeToken<ArrayList<String>>() {}.getType();

                    ArrayList<String> arrayList = gson.fromJson(cart, type);

                    setCount(MyCourses.this, arrayList.size()+"");

                } else {
                    setCount(MyCourses.this, "0");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Binding NavigationView To Toolbar
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String cache = Paper.book().read("user");
        if(cache != null && !cache.isEmpty()) {
            user =  new Gson().fromJson(cache, User.class);

            profile_pic_url = user.getUser_photo().replace("\\", "");
            mUsername = user.getUser_username();
            mEmail = user.getUser_email();
        }

        myCoursesFragement = new MyCoursesFragment();
        myExplorerFragement = new ExploreFragement();
        myArticleFragment = new ArticlesFragment();
        myExamPrepFragment = new ExamPrepFragment();
        exploreNew = new ExploreNew();
        categoryFragment = new CategoryFragment();

        names = new ArrayList<>();
        urls = new ArrayList<>();
        ids = new ArrayList<>();
/*

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyCourses.this);
        final String url1 = "https://careeranna.com/api/getMyCourse.php?user="+user.getUser_id()+"&category=15";
        final String url1 = "http://careeranna.com/apigetMyCourse.php?user="+user.getUser_id()+"&category=15";
        Log.d("url_res", url1);
        StringRequest stringRequest1  = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("url_response", response.toString());
                            JSONArray CategoryArray = new JSONArray(response);
                            for(int i=0;i<10;i++) {
                                JSONObject Category = CategoryArray.getJSONObject(i);
                                ids.add(Category.getString("product_id"));
                                names.add(Category.getString("product_name"));
                                urls.add(Category.getString("product_image").replace("\\",""));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        myCoursesFragement.add(names, urls, ids);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });

        requestQueue1.add(stringRequest1);
*/

        getSupportActionBar().setTitle("Explore");

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, exploreNew).commit();

        addExam();

        // Set Navigation View Information
        setNavigationView();

        // Setting Banner Information
        getBanner();


        // Runnable For banner for changing in banner
        handler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cart, menu);
        return true;
    }

    public void getBanner() {

        mBanners = new ArrayList<>();

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

                        makeRunnable();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //               progressDialog.dismiss();
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

    ViewPager.OnPageChangeListener bannerListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
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

        LinearLayout headerView;

        headerView = findViewById(R.id.header_header);

        profile = headerView.findViewById(R.id.navImage);
        username = headerView.findViewById(R.id.navUsername);
        useremail = headerView.findViewById(R.id.navUseremail);

        Glide.with(this).load(profile_pic_url).into(profile);
        username.setText(mUsername);
        useremail.setText(mEmail);

        navigationView.setCheckedItem(R.id.explore);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.add_to_cart) {

            startActivity(new Intent(this, CartPage.class));
        }

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {

        super.onResume();
        handler.postDelayed(runnable, delay);
        String cache = Paper.book().read("user");
        if(cache != null && !cache.isEmpty()) {
            User user =  new Gson().fromJson(cache, User.class);

            profile_pic_url = user.getUser_photo().replace("//", "");
            mUsername = user.getUser_username();
            mEmail = user.getUser_email();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public void signOut(View view) {

        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();


        String cart = Paper.book().read("cart");

        if(cart != null && !cart.isEmpty()) {
            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<String>>() {}.getType();

            ArrayList<String> arrayList = gson.fromJson(cart, type);

            setCount(this, arrayList.size()+"");

        } else {
            setCount(this, "0");
        }

        if(id == R.id.signOut) {

            mAuth = FirebaseAuth.getInstance();
            if(mAuth != null) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
            }
            Paper.delete("user");
            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else if(id == R.id.myCourse) {

            myCourse();
            fragmentManager.beginTransaction().replace(R.id.main_content, myCoursesFragement).commit();
            navigationView.setCheckedItem(R.id.myCourse);
            getSupportActionBar().setTitle("My Courses");

        }  else if(id == R.id.explore) {

            fragmentManager.beginTransaction().replace(R.id.main_content, exploreNew).commit();
            navigationView.setCheckedItem(R.id.explore);
            getSupportActionBar().setTitle("Explorer");

        } else if(id == R.id.article) {

            initArticle();
            fragmentManager.beginTransaction().replace(R.id.main_content, myArticleFragment).commit();
            navigationView.setCheckedItem(R.id.article);
            getSupportActionBar().setTitle("Articles");

        } else if(id == R.id.profile) {

            startActivity(new Intent(this, MyProfile.class));

        } else if(id == R.id.category) {

            startActivity(new Intent(this, DashBoard.class));

        } else if(id == R.id.MBA) {

            categoryFragment = new CategoryFragment();
            categoryFragment.addSubCategory("1", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.MBA);
            getSupportActionBar().setTitle("MBA");

        } else if(id == R.id.Professional) {

            categoryFragment = new CategoryFragment();

            categoryFragment.addSubCategory("5", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.govt);
            getSupportActionBar().setTitle("Professional Development");

        } else if(id == R.id.Gk) {

            categoryFragment = new CategoryFragment();

            categoryFragment.addSubCategory("6", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.Banking);
            getSupportActionBar().setTitle("General Knowledge");

        }  else if(id == R.id.govt) {

            categoryFragment = new CategoryFragment();

            categoryFragment.addSubCategory("9", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.govt);
            getSupportActionBar().setTitle("Govt. Jobs");

        } else if(id == R.id.law) {
            categoryFragment = new CategoryFragment();
            categoryFragment.addSubCategory("12", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.law);
            getSupportActionBar().setTitle("Law");

        } else if(id == R.id.finance) {
            categoryFragment = new CategoryFragment();
            categoryFragment.addSubCategory("13", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.finance);
            getSupportActionBar().setTitle("Finance");

        } else if(id == R.id.marketing) {
            categoryFragment = new CategoryFragment();
            categoryFragment.addSubCategory("14", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.marketing);
            getSupportActionBar().setTitle("Marketing");

        } else if(id == R.id.certificate) {
            categoryFragment = new CategoryFragment();
            categoryFragment.addSubCategory("15", user.getUser_id());

            fragmentManager.beginTransaction().replace(R.id.main_content, categoryFragment).commit();
            navigationView.setCheckedItem(R.id.certificate);
            getSupportActionBar().setTitle("Certificate Courses");

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void myExam() {

        names = new ArrayList<>();
        urls = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading My Courses Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyCourses.this);
        final String url1 = "https://careeranna.com/api/getMyExam.php?user="+user.getUser_id();
        Log.d("url_res", url1);
        StringRequest stringRequest1  = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("url_response", response.toString());
                            JSONArray CategoryArray = new JSONArray(response);
                            for(int i=0;i<10;i++) {
                                JSONObject Category = CategoryArray.getJSONObject(i);
                                names.add(Category.getString("product_name"));
                                urls.add(Category.getString("product_image").replace("\\",""));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        myExamPrepFragment.add(names, urls);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });

        requestQueue1.add(stringRequest1);
    }

    public void myCourse() {

        names = new ArrayList<>();
        urls = new ArrayList<>();
        ids = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading My Courses Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyCourses.this);
        final String url1 = "https://careeranna.com/api/getMyCourse.php?user="+user.getUser_id();
        Log.d("url_res", url1);
        StringRequest stringRequest1  = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("url_response", response.toString());
                                JSONArray coursesArray = new JSONArray(response);
                                for(int i=0;i<coursesArray.length();i++) {
                                    JSONObject Category = coursesArray.getJSONObject(i);
                                    ids.add(Category.getString("product_id"));
                                    names.add(Category.getString("product_name"));
                                    urls.add(Category.getString("product_image").replace("\\",""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            myCoursesFragement.add(names, urls, ids);
                        }
                    },
                            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }
        ) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting params to login url
                            Map<String, String> params = new HashMap<>();
                            params.put("user", user.getUser_id());
                            return params;

                        }
                    };

        requestQueue1.add(stringRequest1);
                }

        public void initArticle() {

            mArticles = new ArrayList<>();

            progressDialog = new ProgressDialog(this);

            progressDialog.setMessage("Loading Articles Please Wait ... ");
            progressDialog.show();

            progressDialog.setCancelable(false);


            final String desc = "Organizations of all sizes and Industries, be it a financial institution or a small big data start up, everyone is using Python for their business.\n" +
                    "Python is among the popular data science programming languages not only in Big data companies but also in the tech start up crowd. Around 46% of data scientists use Python.\n" +
                    "Python has overtaken Java as the preferred programming language and is only second to SQL in usage today. \n" +
                    "Python is finding Increased adoption in numerical computations, machine learning and several data science applications.\n" +
                    "Python for data science requires data scientists to learn the usage of regular expressions, work with the scientific libraries and master the data visualization concepts.";

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://careeranna.com/api/articlewithimage.php";
            StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("url_response", response.toString());
                                JSONArray ArticlesArray = new JSONArray(response.toString());
                                for(int i=0;i<ArticlesArray.length();i++) {
                                    JSONObject Articles = ArticlesArray.getJSONObject(i);
                                    mArticles.add(new Article(Articles.getString("ID"),
                                            Articles.getString("post_title"),
                                            "https://www.careeranna.com/articles/wp-content/uploads/"+Articles.getString("meta_value").replace("\\",""),
                                            Articles.getString("display_name"),
                                            "CAT",
                                            desc,
                                            Articles.getString("post_date")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressDialog.dismiss();
                            myArticleFragment.setmArticles(mArticles);
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

        public void initCategory() {

            categories = new ArrayList<>();

            progressDialog = new ProgressDialog(this);

            progressDialog.setMessage("Loading Paid Please Wait ... ");
            progressDialog.show();

            progressDialog.setCancelable(false);

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://careeranna.com/apicategory.php";
            StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.i("url_response", response.toString());
                                JSONArray CategoryArray = new JSONArray(response.toString());
                                for(int i=0;i<CategoryArray.length();i++) {
                                    JSONObject Category = CategoryArray.getJSONObject(i);
                                    categories.add(new Category(Integer.toString(Category.getInt("CATEGORY_ID")),
                                            Category.getString("CATEGORY_NAME"),
                                            Category.getString("cimage").replace("\\","")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestQueue requestQueue1 = Volley.newRequestQueue(MyCourses.this);
                            String url1 = "http://careeranna.com/api/getCertficateCourse.php";
                            Log.d("url_res", url1);
                            StringRequest stringRequest1  = new StringRequest(Request.Method.GET, url1,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                Log.i("url_response", response.toString());
                                                JSONArray CategoryArray = new JSONArray(response.toString());
                                                for(int i=0;i<10;i++) {
                                                    JSONObject Category = CategoryArray.getJSONObject(i);
                                                    courses.add(new Course(Category.getString("product_id"),
                                                            Category.getString("course_name"),
                                                            "https://www.careeranna.com/"+Category.getString("product_image").replace("\\",""),
                                                            "15",
                                                            Category.getString("discount")
                                                            , Category.getString("description"),
                                                            Category.getString("video_url").replace("\\","")));
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            progressDialog.dismiss();
                                            addExam();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            progressDialog.dismiss();
                                            addExam();
                                        }
                                    }
                            );

                            requestQueue1.add(stringRequest1);
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



    private void addExam() {

        courses = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Paid Courses Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyCourses.this);
        String url1 = "https://careeranna.com/api/getAllCourse.php";
        Log.d("url_res", url1);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            courses = new ArrayList<>();
                            names = new ArrayList<>();
                            urls = new ArrayList<>();

                            Log.i("url_response", response.toString());
                            JSONArray CategoryArray = new JSONArray(response.toString());
                            for (int i = 0; i < 20; i++) {
                                JSONObject Category = CategoryArray.getJSONObject(i);
                                names.add(Category.getString("course_name"));
                                urls.add("https://www.careeranna.com/" + Category.getString("product_image").replace("\\", ""));
                                courses.add(new Course(Category.getString("product_id"),
                                        Category.getString("course_name"),
                                        "https://www.careeranna.com/" + Category.getString("product_image").replace("\\", ""),
                                        Category.getString("exam_id"),
                                        Category.getString("discount")
                                        , "",
                                        Category.getString("video_url").replace("\\", "")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();

                        addFree();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        addFree();
                    }
                }
        );

        requestQueue1.add(stringRequest1);
    }


    private void addFree() {

        freecourse = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Free Courses Please Wait ... ");
        progressDialog.show();

        progressDialog.setCancelable(false);

        RequestQueue requestQueue1 = Volley.newRequestQueue(MyCourses.this);
        String url1 = "https://careeranna.com/api/getFreeCourse.php";
        Log.d("url_res", url1);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("url_response", response.toString());
                            JSONArray CategoryArray = new JSONArray(response.toString());
                            for (int i = 0; i < CategoryArray.length(); i++) {
                                JSONObject Category = CategoryArray.getJSONObject(i);
                                freecourse.add(new Course(Category.getString("course_id"),
                                        Category.getString("name"),
                                        "https://www.careeranna.com/" + Category.getString("image").replace("\\", ""),
                                        Category.getString("eid"),
                                        Category.getString("discount")
                                        , "meta_description",""));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();

                        exploreNew.addPaidCourses(courses, freecourse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        exploreNew.addPaidCourses(courses, freecourse);
                    }
                }
        );

        requestQueue1.add(stringRequest1);
    }
    }

