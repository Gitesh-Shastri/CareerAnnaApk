package com.careeranna.careeranna.fragement.ui_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.careeranna.careeranna.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class signIn_buttons extends Fragment implements View.OnClickListener {

    Button bt_signInWithEmail, bt_signUp;
    TextView tv_forgotPw;
    FragmentManager fragmentManager;

    public signIn_buttons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in_buttons, container, false);
        bt_signInWithEmail = view.findViewById(R.id.signin_with_email);
        bt_signUp = view.findViewById(R.id.signUp);
        tv_forgotPw = view.findViewById(R.id.forgot);

        bt_signInWithEmail.setOnClickListener(this);
        bt_signUp.setOnClickListener(this);
        tv_forgotPw.setOnClickListener(this);

        fragmentManager = getFragmentManager();


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin_with_email:
                FragmentTransaction changeFrag = fragmentManager.beginTransaction();
                    changeFrag.setCustomAnimations(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    changeFrag.replace(R.id.fragment_btAndFields, new signInFields());
                    changeFrag.commit();
                break;

            case R.id.signUp:
                break;

            case R.id.forgot:
                break;
        }
    }
}
