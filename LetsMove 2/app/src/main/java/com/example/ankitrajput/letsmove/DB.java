package com.example.ankitrajput.letsmove;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.StrictMode;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DB {
    static String getname = "";
    static String getRole = "";

    static String get_user_name = "";
    static String get_user_email = "";
    static String get_user_password = "";
    static String get_user_mobile = "";

    static String get_transporter_name = "";
    static String get_transporter_Email = "";
    static String get_transporter_password = "";
    static String get_transporter_phoneNumber = "";
    //static public final String URL_LINK = "http://10.0.2.2:8080/android/LetsMove/";
    static public final String URL_LINK = "http://conestoga.freeoda.com/LetsMove/";
    static StrictMode.ThreadPolicy th = new StrictMode.ThreadPolicy.Builder().build();

    ///////////////////////////////////send_user_signup_data_to_database_starts//////////////////////////////////////////
    public static void user_signup(String email, String name, String p, String m) {

        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        try {

            name = URLEncoder.encode(name, "utf-8");
            p = URLEncoder.encode(p, "utf-8");

            String link = URL_LINK + "signup_user.php?email=" + email + "&name=" + name + "&password=" + p + "&mobile=" + m;
            HttpGet httpGet = new HttpGet(link);
            httpClient.execute(httpGet);

        } catch (Exception e) {
            System.out.println("Error = " + e);


        }
    }

    ///////////////////////////////////send_user_signup_data_to_database_ends//////////////////////////////////////////

    public static void user_new_post(String post_title, String type_transport, String approx_weight, String from_address, String desti_address, String amount, String pic_name, String selected_date, String user_email) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);
        try {
            post_title = URLEncoder.encode(post_title, "utf-8");
            type_transport = URLEncoder.encode(type_transport, "utf-8");
            from_address = URLEncoder.encode(from_address, "utf-8");
            desti_address = URLEncoder.encode(desti_address, "utf-8");
            approx_weight = URLEncoder.encode(approx_weight, "utf-8");
            amount = URLEncoder.encode(amount, "utf-8");
            pic_name = URLEncoder.encode(pic_name, "utf-8");
            user_email = "ankitrajput077@hotmaill.com";

            String link = URL_LINK + "add_new_post.php?title=" + post_title + "&type=" + type_transport + "&weight=" + approx_weight + "&from=" + from_address + "&to=" + desti_address + "&amount=" + amount + "&selected_date=" + selected_date + "&pic_name=" + pic_name + "&email=" + user_email;

            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
            uploadImage();

        } catch (Exception e) {
            System.out.println("Error = " + e);


        }
    }
    //////////////////////////////////transporter_sigup_data//////////////////////////////////////////////////////////

    public static void signup_transporter(String email, String name, String password, String mobile) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
            name=URLEncoder.encode(name,"UTF-8");
            email=URLEncoder.encode(email,"UTF-8");
            password=URLEncoder.encode(password,"UTF-8");
            mobile=URLEncoder.encode(mobile,"UTF-8");

            String link = URL_LINK + "signup_transporter.php?email=" + email + "&name=" + name + "&password=" + password + "&mobile=" + mobile;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);

        } catch (Exception e) {
            System.out.println("Error = " + e);


        }

    }

    ///////////////////////////////////////////////////chaeck_login_user/////////////////////////////////////////////////////
    public static String check_user(String email, String password) {
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        String status = null;
        try {
            //   email =  URLEncoder.encode(email, "utf-8");
            password = URLEncoder.encode(password, "utf-8");

            System.out.println("Data Sent  =  " + email + "   " + password);
            HttpGet get2 = new HttpGet(URL_LINK + "check_user.php?email=" + email + "&password=" + password);


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


            System.out.println("For Loop");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            JSONObject jsonObject3 = jsonArray.getJSONObject(1);
            JSONObject jsonObject4 = jsonArray.getJSONObject(2);

            status = jsonObject2.optString("status").toString();
            getname = jsonObject3.optString("name").toString();
            getRole = jsonObject4.optString("role").toString();

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
        System.out.println("Status = " + status);
        System.out.println("I am " + getRole);
        return status;


    }

    ///////////////////////////////chaeck_email_user/////////////////////////////////////////////////////
    public static String check_email(String email) {
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        String status = null;
        try {
            email =  URLEncoder.encode(email, "utf-8");


            HttpGet get2 = new HttpGet(URL_LINK + "check_email.php?email=" + email);


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
            JSONArray jsonArray = jsonObject.optJSONArray("check_email");


            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            status = jsonObject2.optString("status").toString();

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
        System.out.println("Status = " + status);
        return status;


    }
    ///////////////////////////////////////////////////upload_image_code///////////////////////////////////////////////////////

    static public void uploadImage() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            HttpPost httppost = new HttpPost(URL_LINK + "fileUpload.php");
            File file = new File(NewPost.filePath);

            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("fileToUpload", file, ContentType.create("image/jpeg"), file.getName())
                    .build();

            httppost.setEntity(httpEntity);
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println(response);
            System.out.println(resEntity);

            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                System.out.println(EntityUtils.toString(resEntity));
            }
            if (resEntity != null) {
                resEntity.consumeContent();
            }

            httpclient.getConnectionManager().shutdown();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //////////////////////////////// Get Post Data /////////////////////////////////////////////////

    static ArrayList get_Post_Data() {
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);

        ArrayList arrayList = new ArrayList();
        String status = null;
        UserBean userBean = null;
        try {

            HttpGet get2 = new HttpGet(URL_LINK + "getlist_post.php");

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
                    //arrayList.add(jsonObject2.optString("user_id").toString());
                    userBean.setUser_id(jsonObject2.optString("user_id").toString());
                    counter++;
                } else if (counter == 2) {
                    //arrayList.add(jsonObject2.optString("name").toString());
                    userBean.setName(jsonObject2.optString("name").toString());
                    counter++;
                } else if (counter == 3) {
                    //arrayList.add();
                    userBean.setType(jsonObject2.optString("type").toString());

                    counter++;
                } else if (counter == 4) {
                    //arrayList.add(jsonObject2.optString("weight").toString());
                    userBean.setWeight(jsonObject2.optString("weight").toString());
                    counter++;
                } else if (counter == 5) {
                    //arrayList.add(jsonObject2.optString("from").toString());
                    userBean.setFrom_address(jsonObject2.optString("from").toString());
                    counter++;
                } else if (counter == 6) {
                    //arrayList.add(jsonObject2.optString("to").toString());
                    userBean.setTo_address(jsonObject2.optString("to").toString());
                    counter++;
                } else if (counter == 7) {
                    //arrayList.add(jsonObject2.optString("pic_name").toString());
                    userBean.setPic_name(jsonObject2.optString("pic_name").toString());
                    counter++;
                } else if (counter == 8) {
                    //arrayList.add(jsonObject2.optString("max_amount").toString());
                    userBean.setMax_amount(jsonObject2.optString("max_amount").toString());
                    counter++;
                } else if (counter == 9) {
                    //arrayList.add(jsonObject2.optString("pickup_date").toString());
                    userBean.setPickup_date(jsonObject2.optString("pickup_date").toString());
                    counter = 1;
                    arrayList.add(userBean);

                    userBean = new UserBean();

                }

            }


        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }

        return arrayList;

    }

    ////////////////////////////////////////// Get_user_deatils ////////////////////////////////////////////////

    public static void get_user_details(String email){

        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        try {
            HttpGet link = new HttpGet(URL_LINK + "get_user_profile.php?email=" + email);
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
            JSONArray jsonArray = jsonObject.optJSONArray("profile");

            System.out.println("For Loop");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            JSONObject jsonObject3 = jsonArray.getJSONObject(1);
            JSONObject jsonObject4 = jsonArray.getJSONObject(2);
            JSONObject jsonObject5 = jsonArray.getJSONObject(3);

            get_user_name = jsonObject2.optString("name").toString();
            get_user_email= jsonObject3.optString("email").toString();
            get_user_password=jsonObject4.optString("password").toString();
            get_user_mobile = jsonObject5.optString("mobile").toString();

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
        //System.out.println("I am " + get_user_email +","+ get_user_name);
    }

    ////////////////////////////////////////// Get_transporter_deatils ////////////////////////////////////////////////

    public static void get_transporter_details(String email){

        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        try {
            HttpGet link = new HttpGet(URL_LINK + "get_transporter_profile.php?email=" + email);
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
            JSONArray jsonArray = jsonObject.optJSONArray("profile");

            System.out.println("For Loop");
            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            JSONObject jsonObject3 = jsonArray.getJSONObject(1);
            JSONObject jsonObject4 = jsonArray.getJSONObject(2);
            JSONObject jsonObject5 = jsonArray.getJSONObject(3);

            get_transporter_name = jsonObject2.optString("name").toString();
            get_transporter_Email= jsonObject3.optString("email").toString();
            get_transporter_password=jsonObject4.optString("password").toString();
            get_transporter_phoneNumber = jsonObject5.optString("mobile").toString();

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
        System.out.println("I am " + get_transporter_Email +","+ get_transporter_name);
    }



}