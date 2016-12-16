package com.example.sachinpatel.letsmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class ChatList extends BaseActivity {
    String UserRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String email_session = preferences_email.getString("login_email", null);
        String google_login_name = preferences_email.getString("login_name", null);
        UserRole = preferences_email.getString("role", null);
        //To get the user id of login user
        user_id = preferences_email.getString("user_id", null);
        //final String role_of_user = preferences_email.getString("role", null);
        String Login_name_facebook = preferences_email.getString("login_facebook_name", null);

        if (UserRole.equals("2")) {
            //This is to set images for transporter
            //one.setImageResource(R.drawable.mybids);
            //three.setImageResource(R.drawable.posts);
            one.setText("My Bids");
            two.setText("All Posts");
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

        three.setBackgroundColor(Color.rgb(153,204,255));
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(ChatList.this, NewPost.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    startActivity(new Intent(ChatList.this, MyBids.class));
                    finish();
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
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserRole.equals("1")) {
                    startActivity(new Intent(ChatList.this, MyPostsActivity.class));

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    Intent intent = new Intent(ChatList.this, ListOfPost.class);
                    intent.putExtra("posts", "all_posts");
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
        /*three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatList.this,ChatList.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });*/

        DB2.getChatListId(user_id);

        ListView listView = (ListView) findViewById(R.id.listview_chat_list);

        ArrayList arrayList = removeDuplicates(DB2.arrayChatListName);
        if(arrayList.isEmpty()){
            if(UserRole.equals("1")) {
                Toast.makeText(ChatList.this, "No Transporter yet.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ChatList.this, "No Customer Yet.", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        else {
            ArrayAdapter adapter = new ArrayAdapter<String>(ChatList.this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(ChatList.this, ChatActivity.class);
                    intent.putExtra("position", i);
                    startActivity(intent);
                }
            });
        }
    }

    static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        // Store unique items in result.
        ArrayList<String> result = new ArrayList<>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }
}