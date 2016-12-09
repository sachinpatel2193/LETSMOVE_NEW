package com.example.ankitrajput.letsmove;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatActivity extends BaseActivity {
    ListView listview_messages;
    TextView textView_name;
    int position;
    static String user_id;
    static String to_id;
    static ArrayList arrayList_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");


        final EditText edittext_message = (EditText)findViewById(R.id.editText_message);
        Button button_send_message = (Button)findViewById(R.id.button_send_message);
        listview_messages = (ListView)findViewById(R.id.listview_chat);
        textView_name = (TextView)findViewById(R.id.textView_chat_name);

        textView_name.setText(DB2.arrayChatListName.get(position).toString());

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        user_id = preferences_email.getString("user_id", null);

        to_id = DB2.arrayChatListId.get(position).toString();
        setChatView();

        //To start the GetMessageService
        startService(new Intent(getBaseContext(), GetMessageService.class));

        button_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edittext_message.getText().toString();
                DB2.send_Message(message,user_id,to_id);
                edittext_message.setText("");
                setChatView();
            }
        });
    }

    void setChatView(){
        arrayList_message = DB2.get_Message(user_id, to_id);
        CustomAdapterMessage adapterMessage = new CustomAdapterMessage(ChatActivity.this, arrayList_message);
        listview_messages.setAdapter(adapterMessage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(getBaseContext(), GetMessageService.class));
        finish();
    }

    @Override
    protected void onResume() { // TODO Auto-generated method stub
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("data_message"));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String num = intent.getStringExtra("data_message");
            setChatView();
        }

    };
}
