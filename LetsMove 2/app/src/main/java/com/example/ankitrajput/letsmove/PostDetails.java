package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class PostDetails extends AppCompatActivity {

    TextView postDetail_Name;
    ImageView postDetail_imageView;
    TextView postDetail_from_address;
    TextView postDetail_to_address;
    TextView postDetail_pickup_date;
    TextView postDetail_max_amount;
    Button view_user_details_of_post;
    Button bid_for_post;


    UserBean userBean = new UserBean();
    ArrayList<Bitmap> arrayListBitmap = new ArrayList<Bitmap>();
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postDetail_Name = (TextView) findViewById(R.id.textView_postDetails_Name);
        postDetail_imageView = (ImageView) findViewById(R.id.imageView_postDetais_Image);
        postDetail_from_address = (TextView) findViewById(R.id.textView_postDetails_from_address);
        postDetail_to_address = (TextView) findViewById(R.id.textView_postDetails_to_address);
        postDetail_pickup_date = (TextView) findViewById(R.id.pickup_date_view);
        postDetail_max_amount = (TextView) findViewById(R.id.max_ammount_view);


        view_user_details_of_post = (Button)findViewById(R.id.view_user_of_post);
        bid_for_post = (Button) findViewById(R.id.bid_on_post);


        // userBean = (UserBean) ListOfPost.arrayList.get(ListOfPost.clickedList);
        userBean = (UserBean) ImageDownloaderTask.arrayList.get(ListOfPost.clickedList);

        postDetail_Name.setText(userBean.getName());
        postDetail_from_address.setText(userBean.getFrom_address());
        postDetail_to_address.setText(userBean.getTo_address());
        postDetail_imageView.setImageBitmap(bmp);
        postDetail_pickup_date.setText(userBean.getPickup_date());
        postDetail_max_amount.setText(userBean.getMax_amount());

        System.out.println("Post");
        DB.get_user_details_by_id(userBean.getUser_id());

        view_user_details_of_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PostDetails.this);
                builder.setMessage("Name: "+ DB.get_user_name + "\nEmail: "+ DB.get_user_email + "\nMobile: "+ DB.get_user_mobile)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                // do things
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        arrayListBitmap = CustomAdapterPostList.bitmapArray;
        postDetail_imageView.setImageBitmap((Bitmap) ImageDownloaderTask.bitmapArrayList.get(ListOfPost.clickedList));




    }


}



