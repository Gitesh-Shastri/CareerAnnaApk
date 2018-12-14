package com.careeranna.careeranna.fragement.ui_fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.careeranna.careeranna.R;
import com.careeranna.careeranna.user.SignUp;

/**
 * A simple {@link Fragment} subclass.
 */
public class signInFields extends Fragment implements View.OnClickListener {

    TextInputLayout et_usermail, et_userpw;
    Button bt_signIn;

    public signInFields() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in_fields, container, false);
        et_usermail = view.findViewById(R.id.useremailL);
        et_userpw = view.findViewById(R.id.userpasswordL);
        bt_signIn = view.findViewById(R.id.signInAccount);

        bt_signIn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInAccount:
                String emailInput = et_usermail.getEditText().getText().toString().trim();
                String pwInput = et_userpw.getEditText().getText().toString().trim();
                if(validateUsernameAndPW(emailInput, pwInput)){
                    ((SignUp)getActivity()).loginWithEmailPw(emailInput, pwInput);
                }
                break;
        }
    }

    public boolean validateUsernameAndPW(String emailInput, String pwInput){
        //For username/email
        if(emailInput.isEmpty()) {
            et_usermail.setError("Username/Email cannot be empty");
            return false;
        }

        //For password
        if(pwInput.isEmpty()) {
            et_usermail.setError("Password cannot be empty");
            return false;
        }

        return true;
    }
}
