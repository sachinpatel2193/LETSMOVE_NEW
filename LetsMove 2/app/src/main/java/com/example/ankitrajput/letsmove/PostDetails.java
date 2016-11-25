package com.example.ankitrajput.letsmove;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.TaskStackBuilder;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PostDetails extends AppCompatActivity {

    Map<String,String> user_info = new HashMap<String, String>();

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
    String user_poster_id;

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

                    user_info = DB.get_user_details_by_id(userBean.getUser_id());


                    builder.setMessage("Name: " + user_info.get("name") + "\nEmail: " + user_info.get("email") + "\nMobile: " + user_info.get("mobile"))
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                        user_poster_id = userBean.getUser_id();

                        System.out.println("IDs =================" + user_poster_id + DB.getId);

                        String bidAmount = bid_amount.getText().toString();
                        String bidDescription = bid_description.getText().toString();
                        if(!bidAmount.isEmpty() && !bidDescription.isEmpty()){
                            DB.send_bid_info(userBean.post_id, user_poster_id, DB.getId, bidAmount, bidDescription);
                            Toast.makeText(PostDetails.this,"Bid Done",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(PostDetails.this,ListOfPost.class));
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PostDetails.this);

                            builder.setMessage("One or Two fields for the Bid can not be Empty!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // do things
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
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
            if(userBean.getStatus().equals("0")){
                view_user_details_of_post.setText("View Details");

                Drawable listlogo = getResources().getDrawable(R.drawable.list);
                view_user_details_of_post.setCompoundDrawablesWithIntrinsicBounds(listlogo, null, null, null);
                bid_for_post.setVisibility(View.GONE);

            }
            else {
                view_user_details_of_post.setText("Edit");
                Drawable editlogo = getResources().getDrawable(R.drawable.editbtn);
                view_user_details_of_post.setCompoundDrawablesWithIntrinsicBounds(editlogo, null, null, null);


                bid_for_post.setText("View Bids");
                Drawable listlogo = getResources().getDrawable(R.drawable.list);
                bid_for_post.setCompoundDrawablesWithIntrinsicBounds(listlogo, null, null, null);

                view_user_details_of_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(PostDetails.this, EditPost.class));
                    }
                });

                System.out.println("bbbbb  === = " + userBean.getPost_id());
                bid_for_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PostDetails.this, ViewBids.class);
                        intent.putExtra("post_id", userBean.getPost_id());
                        startActivity(intent);
                    }
                });
            }
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
    /*public void addBidNotification() {
        android.support.v4.app.NotificationCompat.Builder builder =
                new android.support.v4.app.NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Transporter did bid on your post.")
                        .setContentText("Check it by click here.");

        Intent notificationIntent = new Intent(this, ViewBids.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onResume() { // TODO Auto-generated method stub
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("data"));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String num = intent.getStringExtra("data");
            addBidNotification();
        }

    };*/

}



