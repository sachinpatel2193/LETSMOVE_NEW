package com.example.ankitrajput.letsmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewUserDetails extends AppCompatActivity {

    public TextView user_details_Name, user_details_Email, user_details_Password, user_details_Mobile;
    public Button edit_user_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String email_session = preferences_email.getString("login_email", null);

        DB.get_user_details(email_session);

        user_details_Name=(TextView)findViewById(R.id.user_details_name);
        user_details_Email=(TextView)findViewById(R.id.user_details_email);
        user_details_Password=(TextView)findViewById(R.id.user_details_password);
        user_details_Mobile=(TextView)findViewById(R.id.user_details_mobile);

        user_details_Name.setText(DB.get_user_name);
        user_details_Email.setText(DB.get_user_email);
        user_details_Password.setText(DB.get_user_password);
        user_details_Mobile.setText(DB.get_user_mobile);

        edit_user_button=(Button)findViewById(R.id.btn_edit_user_details);

        edit_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(ViewUserDetails.this, edit_user_details.class));
            }
        });

    }
}
