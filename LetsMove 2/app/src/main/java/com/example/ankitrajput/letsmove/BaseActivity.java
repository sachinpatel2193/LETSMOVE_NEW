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
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class BaseActivity extends AppCompatActivity {

    String userRole;
    String user_id;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        userRole = preferences_email.getString("role",null);
        user_id=preferences_email.getString("user_id",null);

        if(userRole.equals("1")){
            menu.findItem(R.id.transporter_list).setVisible(true);
            menu.findItem(R.id.view_as).setTitle("View as Transporter");
        }
        else {
            menu.findItem(R.id.transporter_list).setVisible(false);
            menu.findItem(R.id.view_as).setTitle("View as Regular User");
        }
        return true;
    }

    public void logout_fromapp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("Do you want to Logout ?");

        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sharedPreferences = getSharedPreferences("login_data", 0);
                sharedPreferences.edit().remove("login_email").commit();
                sharedPreferences.edit().remove("login_name").commit();
                sharedPreferences.edit().remove("user_id").commit();
                sharedPreferences.edit().remove("role").commit();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("sign_in_with_google", true);
                editor.commit();


               // SharedPreferences sharedPreferences2 = getSharedPreferences("login_user_name", 0);
               // sharedPreferences2.edit().remove("login_name").commit();

               // SharedPreferences sharedPreferences3 = getSharedPreferences("login_user_name", 0);
               // sharedPreferences2.edit().remove("login_name").commit();

                // Logout from facebook/////////////////////////////
                LoginManager.getInstance().logOut();

                //Logout from Google //////////////////////////////
                //if(UserLogin.mGoogleApiClient.isConnected() && UserLogin.mGoogleApiClient != null){
                //UserLogin.mGoogleApiClient.disconnect();
                //Auth.GoogleSignInApi.signOut(UserLogin.mGoogleApiClient);
                //}

                stopService(new Intent(getBaseContext(), NotificationService.class));
                stopService(new Intent(getBaseContext(), AcceptedService
                        .class));


                finish();
                startActivity(new Intent(BaseActivity.this, UserLogin.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_as:
                if (userRole.equals("1")) {
                    Toast.makeText(BaseActivity.this, "You have to login again for that.", Toast.LENGTH_LONG).show();

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

                            ///////////////code to restart app //////////////////////////////
                            DB2.changeRole(user_id, "transporter");
                            Intent i =getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
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

                    break;
                } else if(userRole.equals("2")) {
                    Toast.makeText(BaseActivity.this, "You have to login again for that.", Toast.LENGTH_LONG).show();

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

                            ///////////////code to restart app //////////////////////////////
                            DB2.changeRole(user_id, "user");
                            Intent i =getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
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
                    break;
                }
            case R.id.view_profile:
                startActivity(new Intent(BaseActivity.this, ViewUserDetails.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.transporter_list:
                startActivity(new Intent(BaseActivity.this, ViewTransporterDetails.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.logout_shortcut:
                logout_fromapp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
}
