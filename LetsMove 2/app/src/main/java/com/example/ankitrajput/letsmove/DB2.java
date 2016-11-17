package com.example.ankitrajput.letsmove;

import android.content.SharedPreferences;
import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DB2 {

    static StrictMode.ThreadPolicy th = new StrictMode.ThreadPolicy.Builder().build();

    static ArrayList get_My_Post_Data() {
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        ArrayList arrayList = new ArrayList();
        String status = null;

        UserBean userBean = null;
        try {
            HttpGet get2 = new HttpGet(DB.URL_LINK + "get_my_post.php?user_id=" + MyPostsActivity.user_id);

            HttpResponse httpResponse = httpClient.execute(get2);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream inputStream = httpEntity.getContent();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder builder = new StringBuilder();

            String line = null;


            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            String f = builder.toString();

            int counter = 1;
            JSONObject jsonObject = new JSONObject(f);


            JSONArray jsonArray = jsonObject.optJSONArray("post");

            userBean = new UserBean();
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                if (counter == 1) {
                    userBean.setPost_id(jsonObject2.optString("post_id").toString());
                    counter++;
                } else if (counter == 2) {
                    //arrayList.add(jsonObject2.optString("user_id").toString());

                    userBean.setUser_id(jsonObject2.optString("user_id").toString());

                    counter++;
                } else if (counter == 3) {
                    //arrayList.add(jsonObject2.optString("name").toString());
                    userBean.setName(jsonObject2.optString("name").toString());

                    counter++;
                } else if (counter == 4) {
                    //arrayList.add();
                    userBean.setType(jsonObject2.optString("product_type").toString());
                    counter++;
                } else if (counter == 5) {
                    //arrayList.add(jsonObject2.optString("weight").toString());
                    userBean.setWeight(jsonObject2.optString("weight").toString());

                    counter++;
                } else if (counter == 6) {
                    //arrayList.add(jsonObject2.optString("from").toString());
                    userBean.setPic_name(jsonObject2.optString("pic_name").toString());
                    counter++;
                } else if (counter == 7) {
                    //arrayList.add(jsonObject2.optString("to").toString());
                    userBean.setFrom_address(jsonObject2.optString("from_address").toString());
                    counter++;
                } else if (counter == 8) {
                    //arrayList.add(jsonObject2.optString("pic_name").toString());
                    userBean.setTo_address(jsonObject2.optString("to_address").toString());
                    counter++;
                } else if (counter == 9) {
                    //arrayList.add(jsonObject2.optString("max_amount").toString());
                    userBean.setPickup_date(jsonObject2.optString("pickup_date").toString());
                    counter++;
                } else if (counter == 10) {
                    //arrayList.add(jsonObject2.optString("pickup_date").toString());
                    userBean.setMax_amount(jsonObject2.optString("max_amount").toString());
                    counter = 1;
                    arrayList.add(userBean);
                    userBean = new UserBean();
                }


            }


        } catch (Exception exception) {
            exception.printStackTrace();

        }

        return arrayList;

    }


    static ArrayList get_bid_detail(String post_id){
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        ArrayList arrayList = new ArrayList();
        String status = null;

        UserBean2 userBean = null;
        try {
            HttpGet get2 = new HttpGet(DB.URL_LINK + "get_bids.php?post_id=" + post_id);

            HttpResponse httpResponse = httpClient.execute(get2);
            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream inputStream = httpEntity.getContent();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder builder = new StringBuilder();

            String line = null;


            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            String f = builder.toString();

            int counter = 1;
            JSONObject jsonObject = new JSONObject(f);


            JSONArray jsonArray = jsonObject.optJSONArray("bid");

            userBean = new UserBean2();
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                if (counter == 1) {
                    userBean.setBid_id(jsonObject2.optString("bid_id").toString());
                    counter++;
                } else if (counter == 2) {
                    //arrayList.add(jsonObject2.optString("user_id").toString());

                    userBean.setPost_id(jsonObject2.optString("post_id").toString());

                    counter++;
                } else if (counter == 3) {
                    //arrayList.add(jsonObject2.optString("name").toString());
                    userBean.setUser_bidder_id(jsonObject2.optString("user_bidder_id").toString());

                    counter++;
                } else if (counter == 4) {
                    //arrayList.add();
                    userBean.setBidder_name(jsonObject2.optString("bidder_name").toString());
                    counter++;
                } else if (counter == 5) {
                    //arrayList.add(jsonObject2.optString("weight").toString());
                    userBean.setBidder_email(jsonObject2.optString("bidder_email").toString());

                    counter++;
                } else if (counter == 6) {
                    //arrayList.add(jsonObject2.optString("from").toString());
                    userBean.setBidder_mobile(jsonObject2.optString("bidder_mobile").toString());
                    counter++;
                } else if (counter == 7) {
                    //arrayList.add(jsonObject2.optString("to").toString());
                    userBean.setBid_amount(jsonObject2.optString("bid_amount").toString());
                    counter++;
                } else if (counter == 8) {
                    //arrayList.add(jsonObject2.optString("pic_name").toString());
                    userBean.setBid_description(jsonObject2.optString("description").toString());
                    counter = 1;
                    arrayList.add(userBean);
                    userBean = new UserBean2();
                }


            }


        } catch (Exception exception) {
            exception.printStackTrace();

        }

        return arrayList;

    }

    //Bid gets accepted then this method is called

    public static void bid_accepted_done(String post_id, String bidder_id){
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
             post_id = URLEncoder.encode(post_id,"UTF-8");
             bidder_id =URLEncoder.encode(bidder_id,"UTF-8");

            String link = DB.URL_LINK + "bid_accept_update.php?post_id=" + post_id + "&bidder_id=" + bidder_id;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    public static void update_post(String post_id, String post_title, String from_address, String to_address, String max_amount){
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try{
            post_id = URLEncoder.encode(post_id, "UTF-8");
            from_address = URLEncoder.encode(from_address, "UTF-8");
            to_address = URLEncoder.encode(to_address, "UTF-8");
            max_amount = URLEncoder.encode(max_amount, "UTF-8");

            String link = DB.URL_LINK + "update_post.php?post_id=" + post_id + "&post_title=" +post_title+ "&from_address=" + from_address + "&to_address=" + to_address + "&max_amount=" + max_amount;
            HttpGet httpGet = new HttpGet(link);
            httpClient.execute(httpGet);
        }
        catch (Exception e){

        }
    }
}
