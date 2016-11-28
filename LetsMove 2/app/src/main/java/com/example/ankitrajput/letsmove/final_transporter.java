package com.example.ankitrajput.letsmove;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.HashMap;
import java.util.Map;

public class final_transporter extends BaseActivity {

    TextView final_transporter_Name;
    TextView final_transporter_Email;
    TextView final_transporter_Mobile;
    Button Rate_To_transporter;


    Map<String, String> final_transporter_details = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_transporter);
        final_transporter_Name = (TextView)findViewById(R.id.final_transporter_name);
        final_transporter_Email = (TextView)findViewById(R.id.final_transporter_email);
        final_transporter_Mobile = (TextView)findViewById(R.id.final_transporter_mobile);
        Rate_To_transporter = (Button)findViewById(R.id.btn_rate_transporter);


        String current_post_id = getIntent().getExtras().getString("current_post_id");


        final_transporter_details= DB2.get_final_transporter_detail(current_post_id);


        final_transporter_Name.setText(final_transporter_details.get("name"));
        final_transporter_Email.setText(final_transporter_details.get("email"));
        final_transporter_Mobile.setText(final_transporter_details.get("mobile"));


        Rate_To_transporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(final_transporter.this, add_ratings.class);
                intent.putExtra("Transporter Name", final_transporter_details.get("name"));
                intent.putExtra("Transporter Id", final_transporter_details.get("id"));
                startActivity(intent);
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
