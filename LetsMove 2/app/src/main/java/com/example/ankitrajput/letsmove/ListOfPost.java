package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.facebook.login.LoginManager;

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

    ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();


    @Override
    public void onBackPressed() {

        // imageDownloaderTask.setBitMapImageMethod();

        finish();

    }
    UserHome userHome=new UserHome();

    public static String posts_detail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_post);

        Bundle bundle = getIntent().getExtras();
        posts_detail = bundle.getString("posts");

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        user_id = preferences_email.getString("user_id", null);

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