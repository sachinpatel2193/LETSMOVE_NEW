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
import android.widget.Toast;

import java.util.ArrayList;

public class View_my_ratings extends BaseActivity {

    String user_id;
    ArrayList list_of_rater_id = new ArrayList();
    ArrayList list_of_rating_points = new ArrayList();
    ArrayList list_of_description = new ArrayList();
    ArrayList list_of_date = new ArrayList();

    ArrayList list_of_rater_names = new ArrayList();

    ListView listView_for_ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_ratings);
        listView_for_ratings=(ListView)findViewById(R.id.my_ratings);


        try {
            final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
            user_id = preferences_email.getString("user_id", null);


            DB2 db = new DB2();
            db.get_My_ratings(user_id);
            list_of_rater_id = db.list_of_raters_id;
            list_of_rating_points = db.list_of_rating_points;
            list_of_description = db.list_of_description;
            list_of_date = db.list_of_date;
            list_of_rater_names = db.list_of_raters_name;

            if(list_of_rater_id.isEmpty()){
                Toast.makeText(View_my_ratings.this, "You didn't get any Ratings yet.",Toast.LENGTH_LONG).show();
                finish();
            }
            else {
                listView_for_ratings.setAdapter(new ArrayAdapter<String>(View_my_ratings.this,
                        android.R.layout.simple_list_item_1, list_of_rater_names));


                listView_for_ratings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(View_my_ratings.this);
                        builder.setMessage("Name: " + list_of_rater_names.get(position) + "\nRating Points: " + list_of_rating_points.get(position) + "\nDescription: " + list_of_description.get(position))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                    }
                });
            }
        }
        catch (Exception e){
            System.out.println("Error = "+e);
        }

    }
}
