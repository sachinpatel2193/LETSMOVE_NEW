package com.example.ankitrajput.letsmove;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SplashActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();
        imageDownloaderTask.execute();

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        final String email_session = preferences_email.getString("login_email", null);


        boolean netStatus = isNetworkAvailable();

        if (netStatus == false) {
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("No Internet Connection !");
            builder.setCancelable(false);

            // Set up the buttons
            builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(new Intent(SplashActivity.this, SplashActivity.class));
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    progressBar.setVisibility(View.GONE);

                    if (email_session == null) {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        finish();

                    } else {
                        startActivity(new Intent(SplashActivity.this, UserHome.class));
                        finish();

                    }


                }
            }, 1500);
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}

class ImageDownloaderTask extends AsyncTask<String, Void, Void> {

    UserBean userBean = new UserBean();
    static String IMAGE_URL = "http://sachinapatel.com/LetsMove/images/";
    public static Bitmap bmp;
    static public ArrayList bitmapArrayList;
    static ArrayList arrayList = null;

    @Override
    protected Void doInBackground(String... params) {
        setBitMapImageMethod();
        return null;
    }


    public void setBitMapImageMethod(){
        try {
            arrayList = new ArrayList();
            arrayList =  DB.get_Post_Data();

            bitmapArrayList = new ArrayList();


            for(int i =0 ; i < arrayList.size();i++){
                userBean = (UserBean)arrayList.get(i);
                System.out.println("  img gg g     == ="+userBean.getPic_name());

                System.out.println("Inside Asynch    =======  "+i+"  "+userBean.getName()+"  "+userBean.getPic_name());
                URL url = new URL(IMAGE_URL + "" + userBean.getPic_name());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                bitmapArrayList.add(bmp);
            }

            }
        catch (Exception e){
            System.out.println("Error = "+e);
        }

    }
}
