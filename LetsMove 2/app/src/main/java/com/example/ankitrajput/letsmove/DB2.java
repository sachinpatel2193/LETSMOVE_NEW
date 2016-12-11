package com.example.ankitrajput.letsmove;

import android.content.SharedPreferences;
import android.os.StrictMode;

import org.apache.commons.codec.Encoder;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DB2 {

    static StrictMode.ThreadPolicy th = new StrictMode.ThreadPolicy.Builder().build();

    public static Map<String, String> final_transporter_info = new HashMap<String, String>();

    static ArrayList arrayChatListName;
    static  ArrayList arrayChatListId;

    ArrayList list_of_raters_id = new ArrayList();
    ArrayList list_of_rating_points = new ArrayList();
    ArrayList list_of_description = new ArrayList();
    ArrayList list_of_date = new ArrayList();

    ArrayList list_of_raters_name = new ArrayList();

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
                    counter++;
                } else if (counter == 11) {
                    userBean.setStatus(jsonObject2.optString("status").toString());
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


    static ArrayList get_bid_detail(String post_id) {
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
                    counter++;
                }
                else if(counter==9){
                    if (jsonObject2.optString("average_rating").toString()!=null) {
                        userBean.setAverage_Rating(jsonObject2.optString("average_rating").toString());
                    }
                    else {
                        userBean.setAverage_Rating("0");
                    }
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

    public static void bid_accepted_done(String post_id, String bidder_id) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
            post_id = URLEncoder.encode(post_id, "UTF-8");
            bidder_id = URLEncoder.encode(bidder_id, "UTF-8");

            String link = DB.URL_LINK + "bid_accept_update.php?post_id=" + post_id + "&bidder_id=" + bidder_id;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    public static void update_post(String post_id, String post_title, String from_address, String to_address, String max_amount, String selected_date) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
            post_id = URLEncoder.encode(post_id, "UTF-8");
            post_title = URLEncoder.encode(post_title, "UTF-8");
            from_address = URLEncoder.encode(from_address, "UTF-8");
            to_address = URLEncoder.encode(to_address, "UTF-8");
            max_amount = URLEncoder.encode(max_amount, "UTF-8");
            selected_date = URLEncoder.encode(selected_date, "UTF-8");

            String link = DB.URL_LINK + "update_post.php?post_id=" + post_id + "&post_title=" + post_title + "&from_address=" + from_address + "&to_address=" + to_address + "&max_amount=" + max_amount + "&date=" + selected_date;
            HttpGet httpGet = new HttpGet(link);
            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("eeeeeeeee======"+e);
        }
    }

    //To show their own bid list to transporters
    public static ArrayList myBids(String transporter_id) {

        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        ArrayList arrayList = new ArrayList();
        String status = null;

        UserBean2 userBean = null;
        try {
            HttpGet get2 = new HttpGet(DB.URL_LINK + "get_my_bids.php?t_id=" + transporter_id);

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


            JSONArray jsonArray = jsonObject.optJSONArray("my_bids");


            userBean = new UserBean2();
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                if (counter == 1) {
                    //arrayList.add(jsonObject2.optString("user_id").toString());

                    userBean.setMyBid_post_name(jsonObject2.optString("name").toString());

                    counter++;
                } else if (counter == 2) {
                    //arrayList.add(jsonObject2.optString("name").toString());
                    userBean.setMyBid_post_type(jsonObject2.optString("type").toString());

                    counter++;
                } else if (counter == 3) {
                    //arrayList.add();
                    userBean.setMyBid_post_cost(jsonObject2.optString("cost").toString());
                    counter++;
                } else if (counter == 4) {
                    //arrayList.add(jsonObject2.optString("weight").toString());
                    userBean.setMyBid_bid_amount(jsonObject2.optString("amount").toString());

                    counter++;
                } else if (counter == 5) {
                    //arrayList.add(jsonObject2.optString("from").toString());
                    userBean.setMyBid_bid_desc(jsonObject2.optString("desc").toString());
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

    public static void add_rating(String user_id, String transporter_id, String rating_points, String rating_description, String date_of_rating) {
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);
        try {
            user_id = URLEncoder.encode(user_id, "UTF-8");
            transporter_id = URLEncoder.encode(transporter_id, "UTF-8");
            rating_points = URLEncoder.encode(rating_points, "UTF-8");
            rating_description = URLEncoder.encode(rating_description, "UTF-8");
            date_of_rating = URLEncoder.encode(date_of_rating, "UTF-8");

            String link = DB.URL_LINK + "add_rating.php?user_id=" + user_id + "&transporter_id=" + transporter_id + "&rating_points=" + rating_points + "&rating_description=" + rating_description + "&date=" + date_of_rating;
            HttpGet httpGet = new HttpGet(link);
            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("Error =" + e);
        }
    }

    //This method is called by NotificationService that checks that if there is any new bid on the current user post
    static ArrayList get_bid_notification(String user_id) {

        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        ArrayList arrayList = new ArrayList();

        System.out.println("User Id = "+user_id);
        try {
            HttpGet get2 = new HttpGet(DB.URL_LINK + "get_bid_notification.php?user_id=" + user_id);

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

            JSONObject jsonObject = new JSONObject(f);

            JSONArray jsonArray = jsonObject.optJSONArray("notification_detail");

            if(jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    String detail = jsonObject2.optString("detail").toString();
                    arrayList.add(detail);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();

        }

        return arrayList;


    }

    //This method is called by AcceptedService that notifies transporter that they are accepted by customer
    public static ArrayList get_accept_notification(String user_id) {

            HttpClient httpClient = new DefaultHttpClient();
            StrictMode.setThreadPolicy(th);

            ArrayList arrayList = new ArrayList();

            System.out.println("User Id = " + user_id);
            try {
                HttpGet get2 = new HttpGet(DB.URL_LINK + "get_accept_notification.php?user_id=" + user_id);

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

                JSONObject jsonObject = new JSONObject(f);

                JSONArray jsonArray = jsonObject.optJSONArray("accept_detail");

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String detail = jsonObject2.optString("detail").toString();
                        arrayList.add(detail);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();

            }

            return arrayList;
    }


        //////////////////////////////////////// getting final transporter's Details from database ///////////////////////////


        public static Map<String,String> get_final_transporter_detail(String current_post_id){
            HttpClient httpClient = new DefaultHttpClient();
            StrictMode.setThreadPolicy(th);
            try{
                current_post_id = URLEncoder.encode(current_post_id, "UTF-8");


                String link = DB.URL_LINK + "get_final_transporter_detail.php?post_id="+current_post_id;
                HttpGet httpGet = new HttpGet(link);
                HttpResponse httpResponse = httpClient.execute(httpGet);


                HttpEntity httpEntity = httpResponse.getEntity();


                InputStream inputStream = httpEntity.getContent();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder builder = new StringBuilder();


                String line=null;


                while((line=bufferedReader.readLine()) !=null){
                    builder.append(line + "\n");
                }
                String f = builder.toString();


                JSONObject jsonObject = new JSONObject(f);
                JSONArray jsonArray = jsonObject.optJSONArray("profile");


                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                JSONObject jsonObject3 = jsonArray.getJSONObject(2);
                JSONObject jsonObject4 = jsonArray.getJSONObject(3);


                for(int i=0;i<jsonArray.length();i++){
                    final_transporter_info.put("email", jsonObject1.optString("email"));
                    final_transporter_info.put("name", jsonObject2.optString("name"));
                    final_transporter_info.put("mobile", jsonObject3.optString("mobile"));
                    final_transporter_info.put("id", jsonObject4.optString("id"));
                }


            }
            catch (Exception e){
                System.out.println("Error when getting final transporter data :" + e);
            }


            return final_transporter_info;
        }

    static void send_Message(String message, String from_id, String to_id){
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
            message=URLEncoder.encode(message, "UTF-8");

            String link = DB.URL_LINK + "send_message.php?from_id=" + from_id + "&to_id=" + to_id + "&message=" + message;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }


    static ArrayList get_Message(String from_id, String to_id){
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        ArrayList arrayList = new ArrayList();
        String status = null;

        UserBean2 userBean = null;
        try {
            HttpGet get2 = new HttpGet(DB.URL_LINK + "get_message.php?from_id=" + from_id+"&to_id="+to_id);

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


            JSONArray jsonArray = jsonObject.optJSONArray("m");


            userBean = new UserBean2();
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                if (counter == 1) {
                    userBean.setMessage_from_id(jsonObject2.optString("from_id").toString());

                    counter++;
                } else if (counter == 2) {
                    userBean.setMessage_to_id(jsonObject2.optString("to_id").toString());

                    counter++;
                } else if (counter == 3) {
                    userBean.setMessage(jsonObject2.optString("message").toString());
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

    public static void getChatListId(String UserId){
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        arrayChatListName = new ArrayList();
        arrayChatListId = new ArrayList();

        try{
            HttpGet link = new HttpGet(DB.URL_LINK + "get_chatlist_id.php?id=" + UserId);
            HttpResponse httpResponse = httpClient.execute(link);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            String f = builder.toString();
            JSONObject jsonObject = new JSONObject(f);
            JSONArray jsonArray = jsonObject.optJSONArray("chatlist");

            int counter = 0;
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                if(counter == 0){
                    if(!UserId.equals(jsonObject1.optString("final_t_id").toString())) {
                        arrayChatListId.add(jsonObject1.optString("final_t_id").toString());
                    }
                        counter++;
                }
                else if(counter == 1){
                    if(!UserId.equals(jsonObject1.optString("user_id").toString())) {
                        arrayChatListId.add(jsonObject1.optString("user_id").toString());
                    }

                    counter++;
                }
                else {
                    arrayChatListName.add(jsonObject1.optString("name").toString());

                    counter = 0;
                }

            }

        } catch (Exception e) {
            System.out.println("Error =" + e);
        }
    }

    public void get_My_ratings(String user_id){
        StrictMode.setThreadPolicy(th);
        HttpClient httpClient=new DefaultHttpClient();


        try{
            HttpGet httpGet = new HttpGet(DB.URL_LINK + "get_ratings.php?current_user_id="+user_id);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity=httpResponse.getEntity();
            InputStream inputStream=httpEntity.getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
            StringBuilder builder=new StringBuilder();
            String line=null;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            String f = builder.toString();
            JSONObject jsonObject = new JSONObject(f);
            JSONArray jsonArray = jsonObject.optJSONArray("rates");


            int counter = 0;
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                if(counter == 0){
                    list_of_raters_id.add(jsonObject1.optString("user_id").toString());
                    counter++;
                }
                else if(counter == 1){
                    list_of_rating_points.add(jsonObject1.optString("rating_points").toString());
                    counter++;
                }
                else if(counter == 2) {
                    list_of_description.add(jsonObject1.optString("description").toString());
                    counter++;
                }
                else if(counter == 3){
                    list_of_date.add(jsonObject1.optString("date").toString());
                    counter ++;
                }
                else{
                    list_of_raters_name.add("Rate by "+jsonObject1.optString("name").toString());
                    counter=0;
                }
            }
        }
        catch(Exception e){
            System.out.println("Error when getting my ratings = " +e);
        }
    }

    public static void changeRole(String user_id, String value){
        StrictMode.setThreadPolicy(th);
        HttpClient httpClient=new DefaultHttpClient();
        try {
            user_id = URLEncoder.encode(user_id, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");

            String link = DB.URL_LINK + "change_role.php?user_id=" + user_id + "&value_to_change=" +value;

            HttpGet httpGet=new HttpGet(link);
            httpClient.execute(httpGet);
        }
        catch (Exception e){
            System.out.println("Error == "+e);
        }

    }
}

