package com.example.ankitrajput.letsmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class ChatList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String user_id = preferences_email.getString("user_id", null);

        DB2.getChatListId(user_id);

        ListView listView = (ListView) findViewById(R.id.listview_chat_list);

        ArrayList arrayList = removeDuplicates(DB2.arrayChatListName);
        if(arrayList.isEmpty()){
            Toast.makeText(ChatList.this, "You can't chat with any Transporters yet.",Toast.LENGTH_LONG).show();
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