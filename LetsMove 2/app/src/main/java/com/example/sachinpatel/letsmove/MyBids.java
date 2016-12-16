package com.example.sachinpatel.letsmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyBids extends BaseActivity {
    String UserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String email_session = preferences_email.getString("login_email", null);
        String google_login_name = preferences_email.getString("login_name", null);
        UserRole = preferences_email.getString("role", null);
        //To get the user id of login user

        //final String role_of_user = preferences_email.getString("role", null);
        String Login_name_facebook = preferences_email.getString("login_facebook_name", null);

        if (UserRole.equals("2")) {
            //This is to set images for transporter
            //one.setImageResource(R.drawable.mybids);
            //three.setImageResource(R.drawable.posts);
            one.setText("My Bids");
            two.setText("All Posts");
            one.setBackgroundColor(Color.rgb(153,204,255));
        }
        //Get Height and width of screen of mobile////////////////////
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        //////////////////////////////////////////////////////////////////////
        //height = height - 500;
        //one.getLayoutParams().height = height / 2;
        //two.getLayoutParams().height = height / 2;
        //three.getLayoutParams().height = height / 2;
        //four.getLayoutParams().height = height / 2;
        /*one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(MyBids.this, NewPost.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    startActivity(new Intent(MyBids.this, MyBids.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });*/
        /*two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, ListOfPost.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });*/

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(MyBids.this, MyPostsActivity.class));

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Intent intent = new Intent(MyBids.this, ListOfPost.class);
                    intent.putExtra("posts", "all_posts");
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBids.this,ChatList.class));

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        String t_id = preferences_email.getString("user_id", null);

        ListView listview_my_bids = (ListView)findViewById(R.id.listview_my_bids);

        //This arraylist returns the my_bids details
        ArrayList arrayList_myBids = DB2.myBids(t_id);
        if(arrayList_myBids.isEmpty()){
            Toast.makeText(MyBids.this, "You didn't bid for any post yet.",Toast.LENGTH_LONG).show();
            finish();
        }

        CustomAdapterMyBids customAdapterMyBids = new CustomAdapterMyBids(MyBids.this, arrayList_myBids);
        listview_my_bids.setAdapter(customAdapterMyBids);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
