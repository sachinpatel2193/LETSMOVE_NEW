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

public class CustomAdapterMyBids extends BaseAdapter {
    private Context context;
    UserBean2 userBean = new UserBean2();
    ArrayList arrayList = new java.util.ArrayList();

    static String IMAGE_URL = "http://www.sachinapatel.com/LetsMove/images/";

    public CustomAdapterMyBids(Context context, ArrayList arrayList) {
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println("Gone in CustomAdapter");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_bids_custom_layout, null);
        }

        TextView post_name = (TextView) convertView.findViewById(R.id.my_bids_postName);
        TextView post_type = (TextView) convertView.findViewById(R.id.my_bids_postType);
        TextView post_cost = (TextView) convertView.findViewById(R.id.my_bids_post_cost);

        TextView bid_amount = (TextView) convertView.findViewById(R.id.textView19);
        TextView bid_desc = (TextView) convertView.findViewById(R.id.textView20);

        userBean = (UserBean2) arrayList.get(position);

        try {
            post_name.setText("Post Name : "+userBean.getMyBid_post_name());
            post_type.setText("Product Type : "+userBean.getMyBid_post_type());
            post_cost.setText("Maximum Cost : "+userBean.getMyBid_post_cost());

            bid_amount.setText("Your bid amount : "+userBean.getMyBid_bid_amount());
            bid_desc.setText("Bid Description : "+userBean.getMyBid_bid_desc());

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }


        return convertView;
    }
}