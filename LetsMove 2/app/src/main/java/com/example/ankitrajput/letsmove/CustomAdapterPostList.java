package com.example.ankitrajput.letsmove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;


public class CustomAdapterPostList extends BaseAdapter {


    private Context context;
    UserBean userBean = new UserBean();
    static ArrayList arrayList = new java.util.ArrayList();

    private ImageView imageProduct;
    private TextView textviewtitle;
    private TextView textviewservice;
    static String IMAGE_URL = "http://www.sachinapatel.com/LetsMove/images/";
    static ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

    public CustomAdapterPostList(Context context, ArrayList arrayList) {
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

            convertView = inflater.inflate(R.layout.customlist, null);
        }
        imageProduct = (ImageView) convertView.findViewById(R.id.imageView_cutom_layout_post);
        textviewtitle = (TextView) convertView.findViewById(R.id.textView_post_title);
        textviewservice = (TextView) convertView.findViewById(R.id.textView_product_type);
        userBean = (UserBean)arrayList.get(position);

        try {
            //      URL url = new URL(IMAGE_URL+""+userBean.getPic_name());
            //      Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());


            //  ImageDownloaderTask.bitmapArrayList.add(ImageDownloaderTask.bmp); // Add a bitmap
            // Get first bitmap

            imageProduct.setImageBitmap((Bitmap) ImageDownloaderTask.bitmapArrayList.get(position));

            //  imageProduct.setImageResource(R.drawable.product1);
            textviewtitle.setText(userBean.getName());
            textviewservice.setText(userBean.getType());

        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return convertView;
    }
}