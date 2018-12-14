package com.careeranna.careeranna;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.careeranna.careeranna.adapter.OrderCourseAdapter;
import com.careeranna.careeranna.data.OrderedCourse;
import com.careeranna.careeranna.data.User;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CartPage extends AppCompatActivity {

    private ArrayList<OrderedCourse> orderedCourses;
    RecyclerView recyclerView;

    User user;

    LinearLayout linearLayout;

    OrderCourseAdapter orderCourseAdapter;

    Animation mAnimation;

    Snackbar snackbar;

    OrderedCourse orderedCourse;

    AlertDialog.Builder mBuilder;

    AlertDialog alertDialog;

    Button checkout;

    Dialog dialog;

    Intent intent;

    float grand_total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        linearLayout = findViewById(R.id.layout);

        Paper.init(this);

        checkout = findViewById(R.id.checkout);

        recyclerView = findViewById(R.id.ordered_rv);

        String cache = Paper.book().read("user");
        if(cache != null && !cache.isEmpty()) {
            user =  new Gson().fromJson(cache, User.class);

        }

        orderedCourses = new ArrayList<>();
        orderedCourses.add(new OrderedCourse("1", "Python", "999", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));
        orderedCourses.add(new OrderedCourse("3", "Java", "1200", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));
        orderedCourses.add(new OrderedCourse("2", "Android Development", "1999", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));
        orderedCourses.add(new OrderedCourse("4", "CAT", "2500", "https://cdn-images-1.medium.com/max/2000/1*SSutxOFoBUaUmgeNWAPeBA.jpeg"));

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(OrderedCourse orderedCourse: orderedCourses) {
                    grand_total += Float.valueOf(orderedCourse.getPrice());
                }

                dialog = new Dialog(CartPage.this);
                dialog.setContentView(R.layout.custom_payment_layout);
                dialog.setTitle("Pay Now ... ");

                Button paytm, payu;

                TextView price = dialog.findViewById(R.id.price);

                TextView email = dialog.findViewById(R.id.email);

                price.setText(String.valueOf(grand_total));

                email.setText(user.getUser_email());

                paytm = (Button) dialog.findViewById(R.id.paytm);
                payu = (Button) dialog.findViewById(R.id.payu);

                paytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intent = new Intent(CartPage.this, PaytmPayment.class);
                        intent.putExtra("price", grand_total);
                        startActivity(intent);
                    }
                });

                payu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intent = new Intent(CartPage.this, Payment.class);
                        intent.putExtra("price", grand_total);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        });

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
                removeAlert(pos);
            }
        }).attachToRecyclerView(recyclerView);
        mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(200);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE);

    }

    private void removeAlert(final int pos) {

        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Course Deletion");
        mBuilder.setCancelable(false);
            mBuilder.setMessage("Are you sure you want remove from cart ?");
        mBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                orderCourseAdapter = new OrderCourseAdapter(orderedCourses, CartPage.this);
                recyclerView.setAdapter(orderCourseAdapter);
                recyclerView.smoothScrollToPosition(0);
            }
        });
        alertDialog = mBuilder.show();

    }
}
