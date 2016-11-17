package com.example.ankitrajput.letsmove;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    static Context context;
    ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //To set image icon on Action Bar Android Activity
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setIcon(R.drawable.iconactionbar);

        menu.setTitle("   LestMove Home");


        context = HomeActivity.this;
        ImageButton first = (ImageButton)findViewById(R.id.first);
        ImageButton second = (ImageButton)findViewById(R.id.second);
        ImageButton third = (ImageButton)findViewById(R.id.third);
        ImageButton forth = (ImageButton)findViewById(R.id.forth);

        //Get Height and width of screen of mobile////////////////////
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        //////////////////////////////////////////////////////////////////////
        height = height - 400;

        first.getLayoutParams().height=height/2;
        second.getLayoutParams().height=height/2;
        third.getLayoutParams().height=height/2;
        forth.getLayoutParams().height=height/2;

        //////////////////////////////////////////// on click Listener//////////////////////////////

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this, OptionActivity.class));
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(HomeActivity.this, UserLogin.class));

            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ViewTransporterDetails.class));

            }
        });

        forth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
