package com.example.sachinpatel.letsmove;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class help extends BaseActivity {

    TextView help2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        help2=(TextView)findViewById(R.id.help2);

        help2.setText("\u2022 LetsMove helps you to find best transporters to move your Items from one place to another place.\n\n" +
                "\u2022 Post new add on LetsMove with all the required details, wait for the transporter to bid.\n\n" +
                "\u2022 Select transporter with the best price and best reviews from the list of bids.\n\n" +
                "\u2022 Once you select the transporter, that transporter's name will be added in your Chat List.\n\n" +
                "\u2022 So, you can chat with your selected transporter and you can easily move your items with his/her help.\n");
    }
}
