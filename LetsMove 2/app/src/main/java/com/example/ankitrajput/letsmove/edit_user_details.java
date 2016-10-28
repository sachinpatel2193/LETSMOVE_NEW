package com.example.ankitrajput.letsmove;

import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class edit_user_details extends AppCompatActivity {

    public EditText edit_Name, edit_Email, edit_Password, edit_Mobile;
    public Button save_edited_Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        edit_Name=(EditText)findViewById(R.id.edit_name);
        edit_Email=(EditText)findViewById(R.id.edit_email);
        edit_Password=(EditText)findViewById(R.id.edit_password);
        edit_Mobile=(EditText)findViewById(R.id.edit_mobile);

        edit_Name.setText(DB.get_user_name);
        edit_Email.setText(DB.get_user_email);
        edit_Password.setText(DB.get_user_password);
        edit_Mobile.setText(DB.get_user_mobile);

        save_edited_Details=(Button)findViewById(R.id.save_edited_details);

        save_edited_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String new_name=edit_Name.getText().toString();
                final String new_email=edit_Email.getText().toString();
                final String new_password=edit_Password.getText().toString();
                final String new_mobile=edit_Mobile.getText().toString();

                System.out.println("new data =======" + new_name + new_email + new_password + new_mobile);
            }
        });
    }

}


