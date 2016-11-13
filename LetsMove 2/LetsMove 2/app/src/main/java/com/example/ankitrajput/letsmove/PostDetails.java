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
import android.widget.EditText;
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
    EditText bid_amount;
    EditText bid_description;
    String clickedListPosition;
    String pos1;

    static UserBean userBean = new UserBean();
    ArrayList<Bitmap> arrayListBitmap = new ArrayList<Bitmap>();
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Bundle bundle = getIntent().getExtras();
        clickedListPosition = bundle.getString("pos");
        pos1 = bundle.getString("pos1");

        int clickedListPosition2 = Integer.parseInt(clickedListPosition);

        postDetail_Name = (TextView) findViewById(R.id.textView_postDetails_Name);
        postDetail_imageView = (ImageView) findViewById(R.id.imageView_postDetais_Image);
        postDetail_from_address = (TextView) findViewById(R.id.textView_postDetails_from_address);
        postDetail_to_address = (TextView) findViewById(R.id.textView_postDetails_to_address);
        postDetail_pickup_date = (TextView) findViewById(R.id.pickup_date_view);
        postDetail_max_amount = (TextView) findViewById(R.id.max_ammount_view);
        bid_amount = (EditText)findViewById(R.id.edittext_bid_amount);
        bid_description = (EditText)findViewById(R.id.edit_text_bid_description);


        System.out.println("444444444444  =  "+clickedListPosition+"   "  +pos1);
        view_user_details_of_post = (Button)findViewById(R.id.view_user_of_post);
        bid_for_post = (Button) findViewById(R.id.bid_on_post);


        // userBean = (UserBean) ListOfPost.arrayList.get(ListOfPost.clickedList);
        /////////////////////
        if(pos1.equals("all")){
            userBean = (UserBean) ImageDownloaderTask.arrayList.get(ListOfPost.clickedList);

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

            bid_for_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(bid_for_post.getText().toString().equals("Done")){

                        String bidAmount = bid_amount.getText().toString();
                        String bidDescription = bid_description.getText().toString();
                        DB.send_bid_info(userBean.post_id, DB.getId, bidAmount, bidDescription);

                        Toast.makeText(PostDetails.this,"Bid Done",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(PostDetails.this,ListOfPost.class));
                    }
                    else{

                        bid_amount.setVisibility(View.VISIBLE);
                        bid_description.setVisibility((View.VISIBLE));
                        bid_for_post.setText("Done");

                       }

                }
            });

        }


        else{
            userBean = (UserBean) MyPostsActivity.arrayList.get(clickedListPosition2);

            view_user_details_of_post.setText("Edit");
            bid_for_post.setText("View Bids");

            view_user_details_of_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(PostDetails.this, EditPost.class));
                }
            });

            System.out.println("bbbbb  === = "+userBean.getPost_id());
            bid_for_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PostDetails.this, ViewBids.class);
                    intent.putExtra("post_id",userBean.getPost_id());
                    startActivity(intent);
                }
            });
        }

        postDetail_Name.setText(userBean.getName());
        postDetail_from_address.setText(userBean.getFrom_address());
        postDetail_to_address.setText(userBean.getTo_address());
        postDetail_imageView.setImageBitmap(bmp);
        postDetail_pickup_date.setText(userBean.getPickup_date());
        postDetail_max_amount.setText(userBean.getMax_amount());

        DB.get_user_details_by_id(userBean.getUser_id());


        arrayListBitmap = CustomAdapterPostList.bitmapArray;
        postDetail_imageView.setImageBitmap((Bitmap) ImageDownloaderTask.bitmapArrayList.get(Integer.parseInt(clickedListPosition)));

        /////////////////method to visible the bid options and make the bid on post

    }


}



