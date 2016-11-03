package com.example.ankitrajput.letsmove;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UserLogin extends AppCompatActivity {

    CallbackManager callbackManager;
    LoginButton loginButton;
    Button Signup_button;

    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN= 9001;
    private static final String TAG = "UserLogin";
    //int user_type_id = 1;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserLogin.this , HomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_user_login);

        //homeActivity = UserLogin.this;

        loginButton = (LoginButton) findViewById(R.id.login_button_facebook);
        Signup_button = (Button) findViewById(R.id.signUp_button);
        //////////////////////////on sign up button sending on sign up page/////////////////////////////////////////
        Signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, OptionActivity.class));
            }
        });

        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(UserLogin.this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final EditText login_email = (EditText) findViewById(R.id.editText_loginemail);
        final EditText login_password = (EditText) findViewById(R.id.editText_loginpassword);
        Button btn_login_done = (Button) findViewById(R.id.button_loginuser);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));


        /////////////////////////////////For getting Key Hash code////////////////////////////////////////////////////
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.ankitrajput.letsmove",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        ///////////////////////////////////////////////////sign_up_facebook_data////////////////////////////////
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {

                loginButton.setClickable(false);
                System.out.println("Facebbbbooooookkkk   =  succces ");

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        //Bundle bFacebookData = getFacebookData(object);
                        System.out.println("EEEE::::  " + object.optString("email"));
                        System.out.println("EEEE::::  " + object.optString("first_name"));
                        System.out.println("EEEE::::  " + object.optString("last_name"));


                        String email_facebook = object.optString("email");
                        String first_name_facebook = object.optString("first_name");
                        String last_name_facebook = object.optString("last_name");

                        String status_facebook_email = DB.check_email(email_facebook);

                        String name_facebook = first_name_facebook + " " + last_name_facebook;
                        if (status_facebook_email.equals("1")) {


                            DB.user_signup(email_facebook, name_facebook, "", "", ""/*user_type*/);
                            System.out.println("Login successfull with sending data to server");

                        } else {
                            System.out.println("Login successfull, without sending data to server, because data is already exist in server.");
                        }
                        SharedPreferences.Editor editor = getSharedPreferences("login_data", MODE_PRIVATE).edit();
                        editor.putString("login_email", email_facebook);
                        editor.commit();

                        SharedPreferences.Editor editor2 = getSharedPreferences("login_user_name", MODE_PRIVATE).edit();
                        editor2.putString("login_name", name_facebook);
                        editor2.commit();

                        finish();
                        startActivity(new Intent(UserLogin.this, UserHome.class));

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

                System.out.println("Facebbbbooooookkkk   =  onCancel ");
            }

            @Override
            public void onError(FacebookException e) {

                System.out.println("Facebbbbooooookkkk   =  onError  " + e);
            }
        });


        /////////////////////////////////// Login Code ////////////////////////////////////////////////////////////

        btn_login_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_email = login_email.getText().toString();
                String l_password = login_password.getText().toString();

                String EMAIL_REGEX =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                Boolean b = l_email.matches(EMAIL_REGEX);

                if (l_email.equals("") || l_password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserLogin.this);
                    builder.setTitle("Email and password cannot be empty !");

                    // Set up the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else if (b == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserLogin.this);
                    builder.setTitle("Inavalid Email ! Try Again");

                    // Set up the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    String status = DB.check_user(l_email, l_password);

                    if (status.equals("0")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(UserLogin.this);
                        builder.setTitle("Invalid Data ! Try Again");

                        // Set up the buttons
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();

                    } else if (status.equals("1")) {
                        SharedPreferences.Editor editor = getSharedPreferences("login_data", MODE_PRIVATE).edit();
                        editor.putString("login_email", l_email);
                        editor.commit();

                        SharedPreferences.Editor editor_role = getSharedPreferences("user_role", MODE_PRIVATE).edit();
                        editor_role.putString("role", DB.getRole);
                        editor_role.commit();

                        SharedPreferences.Editor editor2 = getSharedPreferences("login_user_name", MODE_PRIVATE).edit();
                        editor2.putString("login_name", DB.getname);
                        editor2.commit();

                        startActivity(new Intent(UserLogin.this, UserHome.class));

                        Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }
            }
        });

        /////////////////////////////////////////////// Google login code start from here /////////////////////////////


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        //signInButton.setScopes(gso.getScopeArray());

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

        private void handleSignInResult(GoogleSignInResult result) {
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                String email_google=acct.getEmail().toString();
                String name_google=acct.getDisplayName().toString();
                System.out.println("Google Login:" + "name:" + name_google + "  email:" + email_google + " Id:" + acct.getId());
                ///////////////// checking that email is already exist in database or not/////////////
                String status_google_email = DB.check_email(email_google);

                if (status_google_email.equals("1")) {
                    ////////////////  sending data to the database ////////////////////
                    DB.user_signup(email_google, name_google, "", "", "user");
                    System.out.println("Login successfull with sending data to server");

                } else {
                    System.out.println("Login successfull, without sending data to server, because data is already exist in server.");
                }
                SharedPreferences.Editor editor = getSharedPreferences("login_data", MODE_PRIVATE).edit();
                editor.putString("login_email", email_google);
                editor.putString("login_google_name", name_google);
                editor.putBoolean("sign_in_with_google", true);
                editor.commit();

                //SharedPreferences.Editor editor2 = getSharedPreferences("login_user_name", MODE_PRIVATE).edit();


                finish();
                startActivity(new Intent(UserLogin.this, UserHome.class));
                //updateUI(true);
            } else {
                // Signed out, show unauthenticated UI.
                System.out.println("google login else");
                //updateUI(false);
            }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
    }
}

