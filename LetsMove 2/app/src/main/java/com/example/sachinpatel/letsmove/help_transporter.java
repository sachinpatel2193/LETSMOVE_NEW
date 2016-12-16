package com.example.sachinpatel.letsmove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class help_transporter extends BaseActivity {
    TextView help_Transporter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_transporter);

        help_Transporter2 = (TextView)findViewById(R.id.help_transporter2);

        help_Transporter2.setText("\u2022 LetsMove helps you to find customers who wants move their Items from one place to another place.\n\n" +
                "\u2022 Check in the list of Post, view details of the Post by clicking on Particular Post. Check distance between two places by Google Map.\n\n" +
                "\u2022 Bid on the Posts.\n\n" +
                "\u2022 Wait for the user to Accept the Bid.\n\n" +
                "\u2022 Once, user accepts your bid, then, that user will be added to your Chat List.\n\n" +
                "\u2022 You can chat with the user and you can gather more information related to the transportation that user wants.\n");
    }
}
