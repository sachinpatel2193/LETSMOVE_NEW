package com.example.sachinpatel.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MyPostsActivity extends BaseActivity {

    static String user_id, UserRole;
    static ArrayList arrayList;
    ProgressDialog progress;
    ListView listview_my_posts;
    static ArrayList bitmapArrayList;
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyPostsActivity.this);
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
        setContentView(R.layout.activity_my_posts);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        user_id = preferences_email.getString("user_id", null);

        String email_session = preferences_email.getString("login_email", null);
        String google_login_name = preferences_email.getString("login_name", null);
        UserRole = preferences_email.getString("role", null);
        //final String role_of_user = preferences_email.getString("role", null);
        String Login_name_facebook = preferences_email.getString("login_facebook_name", null);
        //TO get the permission to access media files
        ActivityCompat.requestPermissions(MyPostsActivity.this,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        //To start notification service if user role is 1
        if (UserRole.equals("1")) {
            //To start the Notification Service for the users who are customer
            startService(new Intent(getBaseContext(), NotificationService.class));
        } else if (UserRole.equals("2")) {
            //To start the Accepted Notification Service for the users who are transporter
            startService(new Intent(getBaseContext(), AcceptedService.class));
        }

        if (UserRole.equals("2")) {
            //This is to set images for transporter
            //one.setImageResource(R.drawable.mybids);
            //three.setImageResource(R.drawable.posts);
            one.setText("My Bids");
            two.setText("All Posts");
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
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(MyPostsActivity.this, NewPost.class));

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    startActivity(new Intent(MyPostsActivity.this, MyBids.class));

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        two.setBackgroundColor(Color.rgb(153,204,255));
        /*two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, ListOfPost.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });*/
        /*two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(MyPostsActivity.this, MyPostsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Intent intent = new Intent(MyPostsActivity.this, ListOfPost.class);
                    intent.putExtra("posts", "all_posts");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });*/
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPostsActivity.this,ChatList.class));
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        listview_my_posts = (ListView)findViewById(R.id.listview_my_posts);
        arrayList = DB2.get_My_Post_Data();

        if(arrayList.isEmpty()){
            Toast.makeText(MyPostsActivity.this, "You don't have any Posted Ads!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MyPostsActivity.this, NewPost.class));
            finish();
        }
        else {
            Task_myPost task = new Task_myPost(MyPostsActivity.this);
            task.execute();
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class Task_myPost extends AsyncTask<Void, Void, Void> {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_view_my_posts);
        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();
        Context context;

        public Task_myPost(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Void... JSONArray) {

try {
    UserBean userBean = new UserBean();
    String IMAGE_URL = "http://sachinapatel.com/LetsMove/images/";
    Bitmap bmp;

    ArrayList arrayList = new ArrayList();


    arrayList = DB2.get_My_Post_Data();

    bitmapArrayList = new ArrayList();


    for(int i =0 ; i < arrayList.size();i++){
        userBean = (UserBean)arrayList.get(i);

        URL url = new URL(IMAGE_URL + "" + userBean.getPic_name());
        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        bitmapArrayList.add(bmp);
    }
}
catch(Exception e){

}
            return null;
        }

        protected void onPostExecute(Void unused) {
            //dismiss the progressdialog


            CustomAdapterPostList adapterUserList2 = new CustomAdapterPostList(MyPostsActivity.this, arrayList);
            listview_my_posts.setAdapter(adapterUserList2);
            progressBar.setVisibility(View.INVISIBLE);
            listview_my_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MyPostsActivity.this, PostDetails.class);
                    intent.putExtra("pos", position + "");
                    intent.putExtra("pos1", "my");
                    startActivity(intent);
                }
            });
        }
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


