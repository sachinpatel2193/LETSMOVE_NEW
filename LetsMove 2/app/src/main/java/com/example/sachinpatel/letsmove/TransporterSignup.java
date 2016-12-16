package com.example.sachinpatel.letsmove;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TransporterSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporter_signup);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        //To set image icon on Action Bar Android Activity
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setIcon(R.drawable.iconactionbar);

        menu.setTitle("   Transporter Registration");

        final EditText company_name = (EditText) findViewById(R.id.editText_company_name);
        final EditText company_email = (EditText) findViewById(R.id.editText_company_email);
        final EditText company_p1 = (EditText) findViewById(R.id.editText_company_password);
        final EditText company_p2 = (EditText) findViewById(R.id.editText_company_password2);
        final EditText company_mobile = (EditText) findViewById(R.id.editText_company_mobile);

        final Button btn_company_signup = (Button) findViewById(R.id.button_Signup_company);

        btn_company_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = company_name.getText().toString();
                String e = company_email.getText().toString();
                String pass1 = company_p1.getText().toString();
                String pass2 = company_p2.getText().toString();

                String m = company_mobile.getText().toString();
                String user_type = "transporter";

                String EMAIL_REGEX =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                Boolean b = e.matches(EMAIL_REGEX);

                if (name.equals("") || e.equals("") || pass1.equals("") || pass2.equals("") || m.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransporterSignup.this);
                    builder.setTitle("Invalid Data ! Please Insert all details");

                    // Set up the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                } else if (b == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransporterSignup.this);
                    builder.setTitle("Invalid Email ! Please Insert correct Email");

                    // Set up the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else if (!(pass1.equals(pass2))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransporterSignup.this);
                    builder.setTitle("Both password does not match ! Please Try Again");

                    // Set up the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else if (!(m.length() == 10)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransporterSignup.this);
                    builder.setTitle("Invalid Mobile number ! Please Try Again");

                    // Set up the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    try {

                        String s = DB.check_email(e);
                        if (s.equals("1")) {
                            btn_company_signup.setClickable(false);
                            DB.user_signup(e, name, pass1, m, user_type);
                            finish();
                            startActivity(new Intent(TransporterSignup.this, UserLogin.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            Toast.makeText(TransporterSignup.this, "Signup Successfull", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(TransporterSignup.this);
                            builder.setTitle("Email Id already exist ! Try another");

                            // Set up the buttons
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        }

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

            }
        });

    }
}
