package com.careeranna.careeranna.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.careeranna.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener{

    TextInputLayout emailLayout,
            passwordLayout,
            phoneLayout,
            ciytLayout,
            userNameLayout;

    Spinner spinner;

    String email, username, phone, city, password, howtoKnow;

    ProgressDialog progressDialog;

    Button signUp;

    RelativeLayout relativeLayout;

    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        relativeLayout = findViewById(R.id.snackbar_rv);

        emailLayout = findViewById(R.id.useremailTv1);
        passwordLayout = findViewById(R.id.userpasswordTv1);
        phoneLayout = findViewById(R.id.userphoneTv1);
        ciytLayout= findViewById(R.id.usercityTv1);
        userNameLayout = findViewById(R.id.usernameTv1);

        spinner = findViewById(R.id.how_spinner);

        signUp = findViewById(R.id.signUpBtn);

        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.signUpBtn:
                if( !validateEmailTv() |
                        !validateUserCityTv() |
                        !validateUserNameTv() |
                        !validateUserNmberTv() |
                        !validateUserPasswordTv()) {
                return;
            } else {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Creating USer Please Wait!!");
                    progressDialog.show();


                    email = emailLayout.getEditText().getText().toString();
                    username = userNameLayout.getEditText().getText().toString();
                    phone = phoneLayout .getEditText().getText().toString();
                    city = ciytLayout.getEditText().getText().toString();
                    password = passwordLayout.getEditText().getText().toString();
                    howtoKnow = spinner.getSelectedItem().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            "http://careeranna.in/signUp.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("url_response", "Register Response: " + response);
                            snackbar = Snackbar.make(relativeLayout, response, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            snackbar = Snackbar.make(relativeLayout, error.getMessage(), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            progressDialog.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting params to login url
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", password);
                            params.put("username", username);
                            params.put("phone", phone);
                            params.put("city", city);
                            params.put("howtoknow", howtoKnow);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                }
                break;
        }

    }

    private boolean validateEmailTv() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailInput = emailLayout.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            emailLayout.setError("UserEmail can't be emplty ");
            return false;

        } else if(!emailInput.matches(emailPattern)) {

            emailLayout.setError("UserEmail is not valid");
            return false;

        } else {

            emailLayout.setError(null);
            return true;

        }
    }


    private boolean validateUserNameTv() {

        String emailInput = userNameLayout.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            userNameLayout.setError("UserName can't be emplty ");
            return false;

        }else if(emailInput.length() > 30) {

            userNameLayout.setError("Username can't be greater than 30");
            return false;

        } else {

            userNameLayout.setError(null);
            return true;

        }
    }


    private boolean validateUserPasswordTv() {

        String emailInput = passwordLayout.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            passwordLayout.setError("Password can't be emplty ");
            return false;

        } else {

            passwordLayout.setError(null);
            return true;

        }
    }

    private boolean validateUserCityTv() {

        String emailInput = ciytLayout.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            ciytLayout.setError("City can't be emplty ");
            return false;

        } else {

            ciytLayout.setError(null);
            return true;

        }
    }

    private boolean validateUserNmberTv() {

        String MobilePattern = "[0-9]{10}";
        String emailInput = phoneLayout.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            phoneLayout.setError("Phone Number can't be emplty ");
            return false;

        } else if(!emailInput.matches(MobilePattern)) {

            phoneLayout.setError("Enter a Valid Phone Number ");
            return false;

        }else {

            phoneLayout.setError(null);
            return true;

        }
    }
}
