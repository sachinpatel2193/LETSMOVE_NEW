package com.example.ankitrajput.letsmove;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

public class edit_user_details extends BaseActivity {

    public TextView Name_View, Email_View, Password_View, Mobile_View;
    public EditText edit_Name, edit_Email, edit_Password, edit_Mobile;
    public Button save_edited_Details;
    String new_name, new_email ,new_password, new_mobile;
    String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        final SharedPreferences preferences_email = getSharedPreferences("login_data", MODE_PRIVATE);
        final String email_session = preferences_email.getString("login_email", null);
        final String user_id = preferences_email.getString("user_id", null);
        UserName = preferences_email.getString("login_name",null);

        Name_View=(TextView)findViewById(R.id.name_view);
        Email_View=(TextView)findViewById(R.id.email_view);
        Password_View=(TextView)findViewById(R.id.password_view);
        Mobile_View=(TextView)findViewById(R.id.mobile_view);

        edit_Name=(EditText)findViewById(R.id.edit_name);
        edit_Email=(EditText)findViewById(R.id.edit_email);
        edit_Password=(EditText)findViewById(R.id.edit_password);
        edit_Mobile=(EditText)findViewById(R.id.edit_mobile);

        edit_Name.setText(DB.get_user_name);
        edit_Email.setText(DB.get_user_email);
        edit_Password.setText(DB.get_user_password);
        edit_Mobile.setText(DB.get_user_mobile);


        /*if(DB.get_user_name.isEmpty()) {
            Name_View.setVisibility(View.INVISIBLE);
            edit_Name.setVisibility(View.INVISIBLE);
        }

        if(DB.get_user_email.isEmpty()) {
            Email_View.setVisibility(View.INVISIBLE);
            edit_Email.setVisibility(View.INVISIBLE);
        }*/

        if(DB.get_user_password.isEmpty()){
            Password_View.setVisibility(View.INVISIBLE);
            edit_Password.setVisibility(View.INVISIBLE);
        }

        if(DB.get_user_mobile.isEmpty()){
            Mobile_View.setVisibility(View.INVISIBLE);
            edit_Mobile.setVisibility(View.INVISIBLE);
        }

        save_edited_Details=(Button)findViewById(R.id.save_edited_details);

        save_edited_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!DB.get_user_name.isEmpty()) {
                    new_name=edit_Name.getText().toString();
                }

                if(!DB.get_user_email.isEmpty()) {
                    new_email=edit_Email.getText().toString();
                }

                if(!DB.get_user_password.isEmpty()){
                    new_password=edit_Password.getText().toString();
                }

                if(!DB.get_user_mobile.isEmpty()){
                    new_mobile=edit_Mobile.getText().toString();
                }


                    AlertDialog.Builder builder = new AlertDialog.Builder(edit_user_details.this);
                    builder.setTitle("Are you sure? Do you want to Edit profile?");

                    // Set up the buttons
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            /*if(TextUtils.isEmpty(new_name) || TextUtils.isEmpty(new_email) || TextUtils.isEmpty(new_password) || TextUtils.isEmpty(new_mobile))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(edit_user_details.this);
                                builder.setTitle("Invalid Data ! Fill all details");

                                // Set up the buttons
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                builder.show();
                            }
                            else*/ if (!email_session.equals(new_email))
                            {
                                String s = DB.check_email(new_email);
                                if (s.equals("0")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(edit_user_details.this);
                                    builder.setTitle("Email Id already used ! Try another");

                                    // Set up the buttons
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                                }
                            } else
                            {
                                DB.edit_user(new_name, new_email, new_password, new_mobile, user_id);
                                Toast.makeText(edit_user_details.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                DB.getname=new_name;
                                preferences_email.edit().putString("login_name",new_name).commit();
                                Intent intent = new Intent(edit_user_details.this, ViewUserDetails.class);
                                startActivity(intent);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(new Intent(edit_user_details.this, ViewUserDetails.class));
                        }
                    });
                    builder.show();

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


