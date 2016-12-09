package com.example.ankitrajput.letsmove;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewBids extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bids);

        ListView listView = (ListView)findViewById(R.id.listview_view_bids);

        Bundle bundle = getIntent().getExtras();
        String post_id = bundle.getString("post_id");

        final ArrayList arrayList = DB2.get_bid_detail(post_id);
        if(arrayList.isEmpty()){
            Toast.makeText(ViewBids.this, "There is no any Bids on your Posted Ad yet.", Toast.LENGTH_LONG).show();
            finish();
        }
        CustomAdapterViewBids customAdapterViewBids = new CustomAdapterViewBids(ViewBids.this,arrayList);

        listView.setAdapter(customAdapterViewBids);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final UserBean2 userBean2 = (UserBean2)arrayList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewBids.this);
                builder.setTitle("Transporter Detail:");
                builder.setMessage("Name: " + userBean2.getBidder_name() + "\nEmail: " + userBean2.getBidder_email() + "\nMobile: " + userBean2.getBidder_mobile())
                        .setPositiveButton("Accept Bid", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                AlertDialog.Builder builder2 = new AlertDialog.Builder(ViewBids.this);
                                builder2.setTitle("Do you want to accept this proposal?:");

                                        builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        DB2.bid_accepted_done(userBean2.getPost_id(), userBean2.getUser_bidder_id());
                                                        dialog.cancel();
                                                        startActivity(new Intent(ViewBids.this,UserHome.class));
                                                    }
                                                });

                                            builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                                builder2.show();
                                AlertDialog alert2 = builder2.create();
                                alert2.show();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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
