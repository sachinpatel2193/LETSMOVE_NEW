package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ViewTransporterDetails extends AppCompatActivity {

    //public TextView transporter_details_Name, transporter_details_Email, transporter_details_Password, transporter_details_Mobile;
    static String email_session;
    ArrayAdapter<String> adapter;
    ListView listview_transporters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transporter_details);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        email_session = preferences_email.getString("login_email", null);

        listview_transporters = (ListView) findViewById(R.id.listview_transporters);

        GetTranspoterListBackground getTranspoterListBackground = new GetTranspoterListBackground();
        getTranspoterListBackground.execute();


        listview_transporters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTransporterDetails.this);
                builder.setMessage("Name: " + DB.transporterName.get(position) + "\nEmail: " + DB.transporterEmail.get(position) + "\nMobile: " + DB.transporterMobile.get(position))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do things
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Rate this Transporter", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ViewTransporterDetails.this, add_ratings.class);
                        intent.putExtra("Transporter Name",DB.transporterName.get(position).toString());
                        intent.putExtra("Transporter Id", DB.transporterId.get(position).toString());
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        });


    }

    class GetTranspoterListBackground extends AsyncTask<String, Void, Void> {

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar_view_transporter);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {

            DB.get_transporter_details(ViewTransporterDetails.email_session);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);

            adapter = new ArrayAdapter<String>(ViewTransporterDetails.this, android.R.layout.simple_list_item_1, DB.transporterName);
            listview_transporters.setAdapter(adapter);

        }
    }
}
