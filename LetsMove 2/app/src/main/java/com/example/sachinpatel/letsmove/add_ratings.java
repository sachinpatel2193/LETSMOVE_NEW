package com.example.sachinpatel.letsmove;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add_ratings extends BaseActivity {

    TextView Trans_name_rating;
    RatingBar Rating_points;
    EditText Rating_description;
    Button Rating_submit;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ratings);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        user_id = preferences_email.getString("user_id", null);

        Intent in =getIntent();
        String transporter_name = in.getStringExtra("Transporter Name");
        final String transporter_id = in.getStringExtra("Transporter Id");

        Trans_name_rating = (TextView)findViewById(R.id.rating_transporter_name);
        Rating_points = (RatingBar)findViewById(R.id.ratingBar_transporter);
        Rating_description = (EditText)findViewById(R.id.rating_description);
        Rating_submit = (Button)findViewById(R.id.btn_submit_rating);

        Trans_name_rating.setText("Rate to "+transporter_name+":");

        final String date_of_rating = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Rating_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating_points= String.valueOf(Rating_points.getRating());
                String rating_description = Rating_description.getText().toString();


                if(!rating_points.isEmpty() && !rating_points.equals("0.0") && !rating_description.isEmpty() && !rating_description.equals("")) {
                    System.out.println("going into if");
                    System.out.println("Data for Rating -==============" + rating_points + " " + rating_description + " " + date_of_rating + " To ==" + transporter_id + "by==" + user_id);

                    DB2.add_rating(user_id, transporter_id, rating_points, rating_description, date_of_rating);
                    startActivity(new Intent(add_ratings.this, UserHome.class));
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(add_ratings.this);

                    builder.setMessage("One or Multiple fields can not be Empty!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // do things
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    System.out.println("In else");
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_shortcut:
                AlertDialog.Builder builder = new AlertDialog.Builder(add_ratings.this);
                builder.setTitle("Do you want to Logout ?");

                // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = getSharedPreferences("login_data", 0);
                        sharedPreferences.edit().remove("login_email").commit();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("sign_in_with_google", true);
                        editor.commit();


                        SharedPreferences sharedPreferences2 = getSharedPreferences("login_user_name", 0);
                        sharedPreferences2.edit().remove("login_name").commit();

                        // Logout from facebook/////////////////////////////
                        LoginManager.getInstance().logOut();

                        //Logout from Google //////////////////////////////
                        //if(UserLogin.mGoogleApiClient.isConnected() && UserLogin.mGoogleApiClient != null){
                        //UserLogin.mGoogleApiClient.disconnect();
                        //Auth.GoogleSignInApi.signOut(UserLogin.mGoogleApiClient);
                        System.out.println("user Logged out from the app");
                        //}

                        stopService(new Intent(getBaseContext(), NotificationService.class));
                        stopService(new Intent(getBaseContext(), AcceptedService
                                .class));


                        finish();
                        startActivity(new Intent(add_ratings.this, HomeActivity.class));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
