package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
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

        userBean = (UserBean) ListOfPost.arrayList.get(ListOfPost.clickedList);

        postDetail_Name.setText(userBean.getName());
        postDetail_from_address.setText(userBean.getFrom_address());
        postDetail_to_address.setText(userBean.getTo_address());
        postDetail_imageView.setImageBitmap(bmp);
        System.out.println("Post");

        arrayListBitmap = CustomAdapterPostList.bitmapArray;
        postDetail_imageView.setImageBitmap(arrayListBitmap.get(ListOfPost.clickedList));




    }


}



