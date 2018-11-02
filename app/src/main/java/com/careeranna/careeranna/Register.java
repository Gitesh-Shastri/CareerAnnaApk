package com.careeranna.careeranna;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

public class Register extends AppCompatActivity {

    TextInputLayout emailLayout,passwordLayout,phoneLayout,ciytLayout,userNameLayout;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailLayout = findViewById(R.id.useremailTv1);
        passwordLayout = findViewById(R.id.userpasswordTv1);
        phoneLayout = findViewById(R.id.userphoneTv1);
        ciytLayout= findViewById(R.id.usercityTv1);
        userNameLayout = findViewById(R.id.usernameTv1);
        signUp = findViewById(R.id.signUpBtn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmailTv() | !validateUserCityTv() | !validateUserNameTv() | !validateUserNmberTv() | !validateUserPasswordTv()) {
                    return;
                } else {
                    Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, SignUp.class));
                }
            }
        });

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
