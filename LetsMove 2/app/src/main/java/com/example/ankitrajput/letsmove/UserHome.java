package com.example.ankitrajput.letsmove;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class UserHome extends AppCompatActivity {

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(UserHome.this);
        builder.setTitle("Exit ?");

        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_user_home);


        ImageButton one = (ImageButton)findViewById(R.id.one);
        ImageButton two = (ImageButton)findViewById(R.id.two);
        ImageButton three = (ImageButton)findViewById(R.id.three);
        ImageButton four = (ImageButton)findViewById(R.id.four);
        TextView textView_name = (TextView)findViewById(R.id.welcome_user_textview);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String email_session = preferences_email.getString("login_email", null);

        final String role_of_user = preferences_email.getString("role", null);
        String Login_name_facebook = preferences_email.getString("login_facebook_name", null);


        SharedPreferences preferences_name = getSharedPreferences("login_user_name", MODE_PRIVATE);
        String name_session = preferences_name.getString("login_name", null);


        SharedPreferences role_name = getSharedPreferences("user_role", MODE_PRIVATE);
        final String UserRole = role_name.getString("role", null);
        System.out.println("Role =======" + UserRole);

        if(name_session!=null) {
            textView_name.setText("Welcome " + name_session);
        }
        else {
            textView_name.setText("Welcome " + Login_name_facebook);
        }

        //Get Height and width of screen of mobile////////////////////
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        //////////////////////////////////////////////////////////////////////
        height = height - 250;

        one.getLayoutParams().height=height/2;
        two.getLayoutParams().height=height/2;
        three.getLayoutParams().height=height/2;
        four.getLayoutParams().height=height/2;


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, NewPost.class));
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(UserHome.this, ViewUserDetails.class));
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserRole.equals("1")){
                    startActivity(new Intent(UserHome.this, ViewTransporterDetails.class));
                }
                else {
                    startActivity(new Intent(UserHome.this, ListOfPost.class));
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserHome.this);
                builder.setTitle("Do you want to Logout ?");

                // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = getSharedPreferences("login_data",0);
                        sharedPreferences.edit().remove("login_email").commit();

                        SharedPreferences sharedPreferences2 = getSharedPreferences("login_user_name",0);
                        sharedPreferences2.edit().remove("login_name").commit();

                        // Logout from facebook/////////////////////////////
                        LoginManager.getInstance().logOut();
                        finish();
                        startActivity(new Intent(UserHome.this, HomeActivity.class));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }



  /*  public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(group1Id, homeId, homeId, "").setIcon(R.drawable.home_menu);
        menu.add(group1Id, profileId, profileId, "").setIcon(R.drawable.profile_menu);
        menu.add(group1Id, searchId, searchId, "").setIcon(R.drawable.search_menu);
        menu.add(group1Id, dealsId, dealsId, "").setIcon(R.drawable.deals_menu);
        menu.add(group1Id, helpId, helpId, "").setIcon(R.drawable.help_menu);
        menu.add(group1Id, contactusId, contactusId, "").setIcon(R.drawable.contactus_menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                // write your code here
                Toast msg = Toast.makeText(MainHomeScreen.this, "Menu 1", Toast.LENGTH_LONG);
                msg.show();
                return true;

            case 2:
                // write your code here
                return true;

            case 3:
                // write your code here
                return true;

            case 4:
                // write your code here
                return true;

            case 5:
                // write your code here
                return true;

            case 6:
                // write your code here
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }*/

}
