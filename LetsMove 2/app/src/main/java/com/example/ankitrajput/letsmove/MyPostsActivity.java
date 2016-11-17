package com.example.ankitrajput.letsmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPostsActivity extends AppCompatActivity {

    static String user_id;
    static ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        user_id = preferences_email.getString("user_id", null);

        ListView listview_my_posts = (ListView)findViewById(R.id.listview_my_posts);
        arrayList = DB2.get_My_Post_Data();
        if(arrayList.isEmpty()){
            Toast.makeText(MyPostsActivity.this, "You don't have any Posted Ads, or They are accepted by Transporter", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            CustomAdapterPostList adapterUserList2 = new CustomAdapterPostList(MyPostsActivity.this, arrayList);
            listview_my_posts.setAdapter(adapterUserList2);

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
}

