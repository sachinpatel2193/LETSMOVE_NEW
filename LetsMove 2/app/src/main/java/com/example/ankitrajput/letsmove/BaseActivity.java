package com.example.ankitrajput.letsmove;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

public class BaseActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String userRole = preferences_email.getString("Role",null);
        String user_id=preferences_email.getString("user_id",null);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_shortcut:
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
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
                        startActivity(new Intent(BaseActivity.this, HomeActivity.class));
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
