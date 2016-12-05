package com.example.ankitrajput.letsmove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ProgressDialog progress;
    EditText add1_from, add2_from, city_from, postalcode_from;
    EditText add1_destination, add2_destination, city_destination, postalcode_destination;
    Spinner spinner_province_from, spinner_province_destination;
    Button new_post;
    String email_session;

    String f_add1, f_add2, f_city, f_province, f_postalcode, d_add1, d_add2, d_city, d_province, d_postalcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        email_session = preferences_email.getString("login_email", null);

        add1_from = (EditText) findViewById(R.id.editText_add1_from);
        add2_from = (EditText) findViewById(R.id.editText_add2_from);
        city_from = (EditText) findViewById(R.id.editText_city_from);
        spinner_province_from = (Spinner) findViewById(R.id.spinner_province_from);
        postalcode_from = (EditText) findViewById(R.id.editText_postalcode_from);

        add1_destination = (EditText) findViewById(R.id.editText_add1_destination);
        add2_destination = (EditText) findViewById(R.id.editText_add2_destination);
        city_destination = (EditText) findViewById(R.id.editText_city_destination);
        spinner_province_destination = (Spinner) findViewById(R.id.spinner_province_destination);
        postalcode_destination = (EditText) findViewById(R.id.edittext_postalcode_destination);

        spinner_province_from.setOnItemSelectedListener(this);
        spinner_province_destination.setOnItemSelectedListener(this);
        new_post = (Button) findViewById(R.id.button_new_post);

        /////////////////////////////////////////set_spinner_province//////////////////////////////////////////

        List categories = new ArrayList();
        categories.add("Alberta");
        categories.add("British Columbia");
        categories.add("Manitoba");
        categories.add("New Brunswick");
        categories.add("Newfoundland and Labrador");
        categories.add("Northwest Territories");
        categories.add("Nova Scotia");
        categories.add("Nunavut");
        categories.add("Ontario");
        categories.add("Prince Edward Island");
        categories.add("Quebec");
        categories.add("Saskatchewan");
        categories.add("Yukon");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_province_from.setAdapter(dataAdapter);

        List categories2 = new ArrayList();
        categories.add("Alberta");
        categories.add("British Columbia");
        categories.add("Manitoba");
        categories.add("New Brunswick");
        categories.add("Newfoundland and Labrador");
        categories.add("Northwest Territories");
        categories.add("Nova Scotia");
        categories.add("Nunavut");
        categories.add("Ontario");
        categories.add("Prince Edward Island");
        categories.add("Quebec");
        categories.add("Saskatchewan");
        categories.add("Yukon");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_province_destination.setAdapter(dataAdapter2);

        new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    f_add1 = add1_from.getText().toString();
                    f_add2 = add2_from.getText().toString();
                    f_city = city_from.getText().toString();
                    f_province = spinner_province_from.getSelectedItem().toString();
                    f_postalcode = postalcode_from.getText().toString();

                    d_add1 = add1_destination.getText().toString();
                    d_add2 = add2_destination.getText().toString();
                    d_city = city_destination.getText().toString();
                    d_province = spinner_province_destination.getSelectedItem().toString();
                    d_postalcode = postalcode_destination.getText().toString();


                if(!f_add1.isEmpty() && !f_city.isEmpty() && !f_province.isEmpty() && !f_postalcode.isEmpty() && !d_add1.isEmpty() && !d_city.isEmpty() && !d_province.isEmpty() && !d_postalcode.isEmpty())
                {
                    TaskAddPost taskAddPost = new TaskAddPost();
                    taskAddPost.execute();
                    System.out.println("YOOOooooooo. Execution Started !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);

                    builder.setMessage("One or Multiple field of Address can not be Empty!")
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
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class TaskAddPost extends AsyncTask<Void, Void, Void> {

        RelativeLayout relativeLayout;

        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(AddressActivity.this);
            progress.setMessage("Adding Post");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();

            relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_add_post);
            relativeLayout.setVisibility(View.INVISIBLE);
        }

        protected Void doInBackground(Void... JSONArray) {

            String from_address = f_add1 + " " + f_add2 + " " + f_city + " " + f_province + " " + f_postalcode;
            String to_address = d_add1 + " " + d_add2 + " " + d_city + " " + d_province + " " + d_postalcode;

            DB.user_new_post(NewPost.post_title, NewPost.type_item, NewPost.weight, from_address, to_address, NewPost.max_amount, NewPost.pic_name, NewPost.pickup_Date, email_session);

            ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask();
            imageDownloaderTask.setBitMapImageMethod();

            return null;
        }

        protected void onPostExecute(Void unused) {
            //dismiss the progressdialog


           progress.dismiss();

            Toast.makeText(AddressActivity.this, "Your Post has been added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddressActivity.this,UserHome.class));
        }

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



