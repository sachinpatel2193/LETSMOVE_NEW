package com.example.ankitrajput.letsmove;

import android.net.http.AndroidHttpClient;
import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit Rajput on 10/1/2016.
 */

public class DB {

    static public final String URL_LINK = "http://www.conestoga.freeoda.com/LetsMove/";

    static StrictMode.ThreadPolicy th = new StrictMode.ThreadPolicy.Builder().build();

    public static void user_signup(String email, String f, String l, String p, String m) {

        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);
        try {
            // String username = URLEncoder.encode(MainActivity.user, "UTF-8");
            // String password = URLEncoder.encode(MainActivity.pass, "UTF-8");

            String link = URL_LINK + "signup_user.php?email=" + email + "&firstname=" + f + "&lastname=" + l + "&password=" + p + "&mobile=" + m;

            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);

        } catch (Exception e) {
            System.out.println("Error = " + e);
            System.out.println("Hello");

        }
    }

    public static void user_new_post(String post_title, String type_transport, String approx_weight, String from_address, String desti_address, String amount, String selected_date) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);
        try {
            approx_weight = URLEncoder.encode(approx_weight);
            // String username = URLEncoder.encode(MainActivity.user, "UTF-8");
            // String password = URLEncoder.encode(MainActivity.pass, "UTF-8");

            String link = URL_LINK + "add_new_post.php?title=" + post_title + "&type=" + type_transport + "&weight=" + approx_weight + "&from=" + from_address + "&to=" + desti_address + "&amount=" + amount+"&selected_date="+selected_date;

            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);

        } catch (Exception e) {
            System.out.println("Error = " + e);


        }
    }

    public static void signup_transporter(String email, String name, String password, String mobile) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);
        try {
            String link = URL_LINK + "signup_transporter.php?email=" + email + "&name=" + name + "&password=" + password + "&mobile=" + mobile;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);

        } catch (Exception e) {
            System.out.println("Error = " + e);


        }

    }

    public static String check_user(String email , String password){
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        HttpGet get2 = new HttpGet(URL_LINK + "check_user.php?email="+email+"&password="+password);
        // Depends on your web service

//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//
 //       nameValuePairs.add(new BasicNameValuePair("email", email));
  //      nameValuePairs.add(new BasicNameValuePair("password", password));

        String status = null;
        try {

       //     get2.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
            JSONArray jsonArray = jsonObject.optJSONArray("check");

            for (int i = 0; i < jsonArray.length(); i++) {

                System.out.println("For Loop");
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                status = jsonObject2.optString("status").toString();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("Status = "+status);
        return status;


    }
    static public void send_image(String filePath) {

        StrictMode.setThreadPolicy(th);
        HttpClient http = AndroidHttpClient.newInstance("LetsMove");
        HttpPost method = new HttpPost(URL_LINK + "images");

        method.setEntity(new FileEntity(new File(filePath), "application/octet-stream"));

        try {
            HttpResponse response = http.execute(method);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
