package com.example.ankitrajput.letsmove;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewTransporterDetails extends AppCompatActivity {

    public TextView transporter_details_Name, transporter_details_Email, transporter_details_Password, transporter_details_Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transporter_details);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        String email_session = preferences_email.getString("login_email", null);

        DB.get_transporter_details(email_session);

        transporter_details_Name=(TextView)findViewById(R.id.transporter_details_name);
        transporter_details_Email=(TextView)findViewById(R.id.transporter_details_email);
        transporter_details_Password=(TextView)findViewById(R.id.transporter_details_password);
        transporter_details_Mobile=(TextView)findViewById(R.id.transporter_details_mobile);

        transporter_details_Name.setText(DB.get_transporter_name);
        transporter_details_Email.setText(DB.get_transporter_Email);
        transporter_details_Password.setText(DB.get_transporter_password);
        transporter_details_Mobile.setText(DB.get_transporter_phoneNumber);
    }
}
