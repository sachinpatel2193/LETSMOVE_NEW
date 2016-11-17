package com.example.ankitrajput.letsmove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditPost extends AppCompatActivity {

    EditText post_title, from_address, to_address, max_amount;
    Button btn_update_save;
    String new_post_title, new_from_address, new_to_address, new_max_amount;

    UserBean userBean = PostDetails.userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        post_title = (EditText)findViewById(R.id.edit_post_Name);
        from_address = (EditText)findViewById(R.id.edit_post_from_address);
        to_address = (EditText)findViewById(R.id.edit_post_to_address);
        max_amount = (EditText)findViewById(R.id.edit_max_ammount);

        post_title.setText(userBean.getName());
        from_address.setText(userBean.getFrom_address());
        to_address.setText(userBean.getTo_address());
        max_amount.setText(userBean.max_amount);

        btn_update_save = (Button)findViewById(R.id.btn_update_post);

        btn_update_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_post_title = post_title.getText().toString();
                new_from_address = from_address.getText().toString();
                new_to_address = to_address.getText().toString();
                new_max_amount = max_amount.getText().toString();

                DB2.update_post(userBean.getPost_id(), new_post_title, new_from_address, new_to_address, new_max_amount);
            }
        });
    }
}
