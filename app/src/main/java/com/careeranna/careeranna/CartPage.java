package com.careeranna.careeranna;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.careeranna.careeranna.adapter.OrderCourseAdapter;
import com.careeranna.careeranna.data.OrderedCourse;

import java.util.ArrayList;

public class CartPage extends AppCompatActivity {

    private ArrayList<OrderedCourse> orderedCourses;
    RecyclerView recyclerView;

    LinearLayout linearLayout;

    OrderCourseAdapter orderCourseAdapter;

    Animation mAnimation;

    Snackbar snackbar;

    OrderedCourse orderedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        linearLayout = findViewById(R.id.layout);

        recyclerView = findViewById(R.id.ordered_rv);

        orderedCourses = new ArrayList<>();
        orderedCourses.add(new OrderedCourse("1", "Python", "999", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));
        orderedCourses.add(new OrderedCourse("3", "Java", "1200", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));
        orderedCourses.add(new OrderedCourse("2", "Android Development", "1999", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));
        orderedCourses.add(new OrderedCourse("4", "CAT", "2500", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        orderCourseAdapter = new OrderCourseAdapter(orderedCourses, this);
        recyclerView.setAdapter(orderCourseAdapter);
        recyclerView.smoothScrollToPosition(0);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = (int) viewHolder.itemView.getTag();
                orderedCourse = orderedCourses.get(pos);
                orderCourseAdapter.remove(pos);
                snackbar = Snackbar.make(linearLayout, "Item Removed !! ", Snackbar.LENGTH_SHORT);
                snackbar.show();
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderedCourses.add(orderedCourse);
                        orderCourseAdapter = new OrderCourseAdapter(orderedCourses, CartPage.this);
                        recyclerView.setAdapter(orderCourseAdapter);
                        recyclerView.smoothScrollToPosition(0);
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);
        mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(200);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);

    }
}
