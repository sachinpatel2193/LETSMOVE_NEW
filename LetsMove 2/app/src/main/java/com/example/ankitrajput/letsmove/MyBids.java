package com.example.ankitrajput.letsmove;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyBids extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
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
