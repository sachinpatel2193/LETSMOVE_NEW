package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;

public class ListOfPost extends BaseActivity {

    static ArrayList arrayList = null;
    static ListView listview_post = null;
    static int clickedList;
    ProgressDialog progress;
    Context context;
    CustomAdapterPostList adapterUserList;
    static String user_id = null;
    String UserRole;
    ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();
    TextView textView_name;

    @Override
    public void onBackPressed() {

        // imageDownloaderTask.setBitMapImageMethod();


        AlertDialog.Builder builder = new AlertDialog.Builder(ListOfPost.this);
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
    UserHome userHome=new UserHome();

    public static String posts_detail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_post);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);
        //textView_name=(TextView)findViewById(R.id.welcome_user_textview);

        Bundle bundle = getIntent().getExtras();
        posts_detail = bundle.getString("posts");

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        user_id = preferences_email.getString("user_id", null);

        String email_session = preferences_email.getString("login_email", null);
        String google_login_name = preferences_email.getString("login_name", null);
        UserRole = preferences_email.getString("role", null);

        //final String role_of_user = preferences_email.getString("role", null);
        String Login_name_facebook = preferences_email.getString("login_facebook_name", null);

        // To get permission from user to upload image (to read external storage)
        ActivityCompat.requestPermissions(ListOfPost.this,
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
            two.setBackgroundColor(Color.rgb(153,204,255));
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
                    startActivity(new Intent(ListOfPost.this, NewPost.class));

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    startActivity(new Intent(ListOfPost.this, MyBids.class));

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

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
                    startActivity(new Intent(ListOfPost.this, MyPostsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Intent intent = new Intent(ListOfPost.this, ListOfPost.class);
                    intent.putExtra("posts", "all_posts");
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });*/
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListOfPost.this,ChatList.class));

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //SharedPreferences preferences_name = getSharedPreferences("login_user_name", MODE_PRIVATE);
        String name_session = preferences_email.getString("login_name", null);


       /* if (name_session != null) {
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
        }*/


        Task task = new Task(ListOfPost.this);
        task.execute();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class Task extends AsyncTask<Void, Void, Void> {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_view_posts);
        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();
        Context context;

        public Task(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        protected Void doInBackground(Void... JSONArray) {

            //imageDownloaderTask.setBitMapImageMethod();
            //arrayList = DB.get_Post_Data();

            return null;
        }

        protected void onPostExecute(Void unused) {
            //dismiss the progressdialog


            listview_post = (ListView) findViewById(R.id.listviewPost);

            /*if(ImageDownloaderTask.arrayList != null){
                for(int i=0;i<ImageDownloaderTask.arrayList.size(); i++)
                    if(ImageDownloaderTask.arrayList.get(i) == null){
                        ImageDownloaderTask.arrayList.set(i, R.drawable.logo);
                    }
            }*/

            adapterUserList = new CustomAdapterPostList(ListOfPost.this, ImageDownloaderTask.arrayList);
            listview_post.setAdapter(adapterUserList);

            progressBar.setVisibility(View.INVISIBLE);

            listview_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickedList = position;
                    Intent intent = new Intent(ListOfPost.this, PostDetails.class);
                    intent.putExtra("pos", position + "");
                    intent.putExtra("pos1", "all");
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