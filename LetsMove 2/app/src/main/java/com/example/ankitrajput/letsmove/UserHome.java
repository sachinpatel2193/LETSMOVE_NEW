package com.example.ankitrajput.letsmove;

import android.*;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

public class UserHome extends BaseActivity {

    String UserRole = " ";
    static String user_id;

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

        ImageButton one = (ImageButton) findViewById(R.id.one);
        ImageButton two = (ImageButton) findViewById(R.id.two);
        ImageButton three = (ImageButton) findViewById(R.id.three);
        ImageButton four = (ImageButton) findViewById(R.id.four);
        TextView textView_name = (TextView) findViewById(R.id.welcome_user_textview);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String email_session = preferences_email.getString("login_email", null);
        String google_login_name = preferences_email.getString("login_name", null);
        UserRole = preferences_email.getString("role", null);

        //To get the user id of login user
        user_id = preferences_email.getString("user_id", null);

        //final String role_of_user = preferences_email.getString("role", null);
        String Login_name_facebook = preferences_email.getString("login_facebook_name", null);

        //TO get the permission to access media files
        ActivityCompat.requestPermissions(UserHome.this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        //To start notification service if user role is 1
        if (UserRole.equals("1")) {
            //To start the Notification Service for the users who are customer
            startService(new Intent(getBaseContext(), NotificationService.class));
        } else if (UserRole.equals("2")) {
            //To start the Accepted Notification Service for the users who are transporter
            startService(new Intent(getBaseContext(), AcceptedService.class));

        }

        //SharedPreferences preferences_name = getSharedPreferences("login_user_name", MODE_PRIVATE);
        String name_session = preferences_email.getString("login_name", null);


        if (name_session != null) {
            System.out.println("user role ========== " + UserRole);
            System.out.println("Session name --------------------");
            textView_name.setText("Welcome " + name_session);
        } else if (google_login_name != null) {
            System.out.println("user role ========== " + UserRole);
            textView_name.setText("Welcome " + google_login_name);
            System.out.println("Google name --------------------");
        } else {
            System.out.println("user role ========== " + UserRole);
            System.out.println("Facebook Name ---------------------");
            textView_name.setText("Welcome " + Login_name_facebook);
        }


        if (UserRole.equals("2")) {
            //This is to set images for transporter
            one.setImageResource(R.drawable.mybids);
            three.setImageResource(R.drawable.posts);
        }
        //Get Height and width of screen of mobile////////////////////
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        //////////////////////////////////////////////////////////////////////
        height = height - 500;

        one.getLayoutParams().height = height / 2;
        two.getLayoutParams().height = height / 2;
        three.getLayoutParams().height = height / 2;
        four.getLayoutParams().height = height / 2;


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(UserHome.this, NewPost.class));
                } else {
                    startActivity(new Intent(UserHome.this, MyBids.class));
                }
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
                if (UserRole.equals("1")) {
                    startActivity(new Intent(UserHome.this, ViewTransporterDetails.class));
                } else {
                    Intent intent = new Intent(UserHome.this, ListOfPost.class);
                    intent.putExtra("posts", "all_posts");
                    startActivity(intent);
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this,ChatList.class ));
            }
        });
    }

    public void logout_app(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserHome.this);
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




    private void addNotification() {
        //Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = (android.support.v7.app.NotificationCompat.Builder)new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic)
                        .setContentTitle(""+NotificationService.notification_data.get(0)+"  Bid Amount = "+NotificationService.notification_data.get(3))
                        .setContentText(""+NotificationService.notification_data.get(4))
                        .setSound(soundUri);

        Intent notificationIntent = new Intent(this, UserHome.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    protected void onResume() { // TODO Auto-generated method stub
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("data"));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String num = intent.getStringExtra("data");
            addNotification();
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //This method allows to access the READ_EXTERNAL_STORAGE permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
              //      Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
