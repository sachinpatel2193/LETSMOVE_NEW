package com.example.sachinpatel.letsmove;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterMessage extends BaseAdapter {
    private Context context;
    UserBean2 userBean = new UserBean2();
    ArrayList arrayList = new java.util.ArrayList();

    static String IMAGE_URL = "http://www.sachinapatel.com/LetsMove/images/";

    public CustomAdapterMessage(Context context, ArrayList arrayList) {
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            userBean = (UserBean2) arrayList.get(position);

        String from_id = userBean.getMessage_from_id();
        String to_id = userBean.getMessage_to_id();
        String message = userBean.getMessage();

        if(from_id.equals(UserHome.user_id)){
            convertView = inflater.inflate(R.layout.customlayout_right, null);
            TextView message_right = (TextView) convertView.findViewById(R.id.textview_message_right);

            try {
                message_right.setText(message);

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }

        }
        else{

                convertView = inflater.inflate(R.layout.customlayout_left, null);
            TextView message_left = (TextView) convertView.findViewById(R.id.textview_message_left);


            try {
                message_left.setText(message);

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }

        }



        return convertView;
    }
}