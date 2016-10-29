package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class ListOfPost extends AppCompatActivity {

    static ArrayList arrayList;
    private ListView listview_post;
    static int clickedList;
    ProgressDialog progress;
    Context context;
    CustomAdapterPostList adapterUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarVisibility(true);

        context = ListOfPost.this;
        progress = new ProgressDialog(ListOfPost.this);
        progress = ProgressDialog.show(this, "Loading...", "Please wait...");

        Task task = new Task(ListOfPost.this);
        task.execute();


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class Task extends AsyncTask<Void, Void, Void> {

        Context context;

        public Task(Context context) {
            this.context = context;

            //constructor for this class
        }

        protected void onPreExecute() {
            //create the progress dialog as

            System.out.println("Pre");
        }

        protected Void doInBackground(Void... JSONArray) {


                arrayList = DB.get_Post_Data();
                System.out.println(arrayList);
                adapterUserList = new CustomAdapterPostList(ListOfPost.this, arrayList);

            return null;
        }

        protected void onPostExecute(Void unused) {
            //dismiss the progressdialog
            setContentView(R.layout.activity_list_of_post);

            System.out.println("Post");
            listview_post = (ListView) findViewById(R.id.listviewPost);

            listview_post.setAdapter(adapterUserList);
            System.out.println("Post after");

            progress.dismiss();

            listview_post.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clickedList = position;
                    Intent intent = new Intent(ListOfPost.this, PostDetails.class);
                    startActivity(intent);
                }
            });

        }
    }
}