package com.example.ankitrajput.letsmove;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;

import com.facebook.login.LoginManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditPost extends BaseActivity {

    EditText post_title, from_address, to_address, max_amount;
    Button btn_update_save;
    String new_post_title, new_from_address, new_to_address, new_max_amount;

    public static String selected_Date;
    Calendar myCalendar = Calendar.getInstance();
    private DatePicker datePicker;
    private Calendar calendar;
    static final int DATE_PICKER_ID = 1111;
    Button btn_setDate;
    private int year, month, day;
    Boolean dateSelected=false;

    UserBean userBean = PostDetails.userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        post_title = (EditText)findViewById(R.id.edit_post_Name);
        from_address = (EditText)findViewById(R.id.edit_post_from_address);
        to_address = (EditText)findViewById(R.id.edit_post_to_address);
        max_amount = (EditText)findViewById(R.id.edit_max_ammount);
        btn_setDate = (Button)findViewById(R.id.button_new_setDate);

        post_title.setText(userBean.getName());
        from_address.setText(userBean.getFrom_address());
        to_address.setText(userBean.getTo_address());
        max_amount.setText(userBean.max_amount);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //  .setText(sdf.format(myCalendar.getTime()));
        /*String old_selected_Date = sdf.format(userBean.getPickup_date());
        btn_setDate.setText(old_selected_Date);*/

        btn_update_save = (Button)findViewById(R.id.btn_update_post);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        btn_setDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dateSelected=true;
                DatePickerDialog dpd = new DatePickerDialog(EditPost.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            }
        });


        btn_update_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_post_title = post_title.getText().toString();
                new_from_address = from_address.getText().toString();
                new_to_address = to_address.getText().toString();
                new_max_amount = max_amount.getText().toString();

                if(!new_post_title.equals("") && !new_from_address.equals("") && !new_to_address.equals("") && !new_max_amount.equals("") && !dateSelected){
                    DB2.update_post(userBean.getPost_id(), new_post_title, new_from_address, new_to_address, new_max_amount, selected_Date);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPost.this);
                    builder.setMessage("One or Two fields of an Ad can not be Empty!")
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

    private void updateLabel(){

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //  .setText(sdf.format(myCalendar.getTime()));
        selected_Date = sdf.format(myCalendar.getTime());
        btn_setDate.setText(selected_Date);
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
