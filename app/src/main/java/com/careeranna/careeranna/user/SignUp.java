package com.careeranna.careeranna.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.careeranna.careeranna.MyCourses;
import com.careeranna.careeranna.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "SignUp Activity";
    private final static int RC_SIGN_IN = 2;

    TextInputLayout textInputEmail,
            textInputPassword;

    RelativeLayout relativeLayout;

    FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    SignInButton mGoogleBtn;

    private CallbackManager mCallbackManager;

    LoginButton loginButton;

    ProgressBar progressBar;

    AlertDialog.Builder builder;

    Snackbar snackbar;

    AlertDialog alert;

    TextView signUp;

    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        relativeLayout = findViewById(R.id.snackbar);

        mGoogleBtn = findViewById(R.id.google_sign_in_button);

        signUp = findViewById(R.id.signUp);

        signIn = findViewById(R.id.signInAccount);

        textInputEmail = findViewById(R.id.useremailL);
        textInputPassword = findViewById(R.id.userpasswordL);

        progressBar = findViewById(R.id.signUp_progressCircle);

        loginButton =  findViewById(R.id.fb_login_button);

        mGoogleBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        //Hide the circuler progress bar and only show when needed
        progressBar.bringToFront();
        progressBar.setVisibility(View.GONE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        signIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(SignUp.this, gso);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                snackbar = Snackbar.make(relativeLayout, "Sign In Please Wait", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
                progressBar.setVisibility(View.VISIBLE);

                Log.d("facebook", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

                Log.d("facebook", "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {

                Log.d("facebook", "facebook:onError", error);

            }

        });
    }

    @Override
    public void onStart() {

        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

                case R.id.google_sign_in_button:

                    Log.d(TAG, "Google signin clicked ");

                    if(!amIConnect()) {

                        builder = new AlertDialog.Builder(SignUp.this);
                        builder.setTitle("No Internet Connection");
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setCancelable(false);
                        builder.setMessage("Please Connect To The Internet")
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alert.dismiss();
                                    }
                                });
                        alert = builder.create();
                        alert.show();

                    } else {
                        signIn();
                    }
                    break;

            case R.id.signInAccount:

                if(!validateEmailTv() | !validatePassTv()) {
                    return;
                }
                Toast.makeText(SignUp.this, "username/username : " + textInputEmail.getEditText().getText().toString().trim(), Toast.LENGTH_SHORT).show();

                break;

            case R.id.signUp:

                Intent intent = new Intent(SignUp.this, Register.class);
                startActivity(intent);
        }
    }

    private void signIn() {
        Log.d(TAG, "Inside signIn function");
        snackbar = Snackbar.make(relativeLayout, "Sign In Please Wait", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "Inside onActivityResult function");

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                Log.d(TAG, "onActivityResult: Successful sign in");
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("SignUp1", "Google sign in failed", e);
                Toast.makeText(this, "Google sign-in failed!", Toast.LENGTH_SHORT).show() ;
                // ...
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("SignUp1", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUp1", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUp1", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Firebase auth failed!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("facebook", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("facebook", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("facebook", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            snackbar.dismiss();
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(SignUp.this, MyCourses.class);
            startActivity(intent);
            finish();
        }
    }


    private boolean validateEmailTv() {

        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            textInputEmail.setError("UserEmail can't be empty ");
            return false;

        } else {

            textInputEmail.setError(null);
            return true;

        }
    }

    private boolean validatePassTv() {

        String emailInput = textInputPassword.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {

            textInputPassword.setError("UserPassword can't be empty ");
            return false;

        } else {

            textInputPassword.setError(null);
            return true;

        }
    }

    private boolean amIConnect() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
