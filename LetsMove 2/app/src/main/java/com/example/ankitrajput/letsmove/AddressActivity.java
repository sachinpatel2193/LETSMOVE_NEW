package com.example.ankitrajput.letsmove;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        final String email_session = preferences_email.getString("login_email", null);

        final EditText add1_from = (EditText) findViewById(R.id.editText_add1_from);
        final EditText add2_from = (EditText) findViewById(R.id.editText_add2_from);
        final EditText city_from = (EditText) findViewById(R.id.editText_city_from);
        final Spinner spinner_province_from = (Spinner) findViewById(R.id.spinner_province_from);
        final EditText postalcode_from = (EditText) findViewById(R.id.editText_postalcode_from);

        final EditText add1_destination = (EditText) findViewById(R.id.editText_add1_destination);
        final EditText add2_destination = (EditText) findViewById(R.id.editText_add2_destination);
        final EditText city_destination = (EditText) findViewById(R.id.editText_city_destination);
        final Spinner spinner_province_destination = (Spinner) findViewById(R.id.spinner_province_destination);
        final EditText postalcode_destination = (EditText) findViewById(R.id.edittext_postalcode_destination);

        spinner_province_from.setOnItemSelectedListener(this);
        spinner_province_destination.setOnItemSelectedListener(this);
        Button new_post = (Button) findViewById(R.id.button_new_post);

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
                String f_add1 = add1_from.getText().toString();
                String f_add2 = add2_from.getText().toString();
                String f_city = city_from.getText().toString();
                String f_province = spinner_province_from.getSelectedItem().toString();
                String f_postalcode = postalcode_from.getText().toString();

                String d_add1 = add1_destination.getText().toString();
                String d_add2 = add2_destination.getText().toString();
                String d_city = city_destination.getText().toString();
                String d_province = spinner_province_destination.getSelectedItem().toString();
                String d_postalcode = postalcode_destination.getText().toString();


                String from_address = f_add1 + " " + f_add2 + " " + f_city + " " + f_province + " " + f_postalcode;
                String to_address = d_add1 + " " + d_add2 + " " + d_city + " " + d_province + " " + d_postalcode;

                DB.user_new_post(NewPost.post_title, NewPost.type_item, NewPost.weight, from_address, to_address, NewPost.max_amount, NewPost.pic_name, NewPost.pickup_Date, email_session);
                startActivity(new Intent(AddressActivity.this, UserHome.class));

                Toast.makeText(AddressActivity.this,"Your Post has been added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



