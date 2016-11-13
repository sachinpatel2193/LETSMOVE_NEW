package com.example.ankitrajput.letsmove;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterViewBids extends BaseAdapter {

    ArrayList arrayList = new ArrayList();
    Context context;
    UserBean2 userBean2;

    public CustomAdapterViewBids(Context context, ArrayList arrayList) {
        // TODO Auto-generated constructor stub
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
       return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_bid_view, null);
        }

        TextView t_name = (TextView)convertView.findViewById(R.id.view_bids_transporter_name);
        TextView t_review = (TextView)convertView.findViewById(R.id.view_bids_transporter_reviews);
        TextView t_bid_amount = (TextView)convertView.findViewById(R.id.view_bids_transporter_bid_amount);
        TextView t_description = (TextView)convertView.findViewById(R.id.view_bids_transporter_bid_description);

        userBean2 = (UserBean2) arrayList.get(position);

        t_name.setText(userBean2.getBidder_name());
        t_review.setText("4 / 5");
        t_bid_amount.setText(userBean2.getBid_amount());
        t_description.setText(userBean2.getBid_description());



        return convertView;
    }
}
