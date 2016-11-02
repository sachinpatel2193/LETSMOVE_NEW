package com.example.ankitrajput.letsmove;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

            final ListView listview_transporters = (ListView) findViewById(R.id.listview_transporters);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DB.transporterName);
            listview_transporters.setAdapter(adapter);

            listview_transporters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewTransporterDetails.this);
                    builder.setMessage("Name: " + DB.transporterName.get(position) + "\nEmail: " + DB.transporterEmail.get(position) + "\nMobile: " + DB.transporterMobile.get(position))
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
    }
}
