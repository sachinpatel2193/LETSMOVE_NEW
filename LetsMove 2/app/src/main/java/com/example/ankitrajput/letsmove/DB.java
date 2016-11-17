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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {
    static String getname = "";
    static String getRole = "";
    static String getId = "";
    static String get_user_name = "";
    static String get_user_email = "";
    static String get_user_password = "";
    static String get_user_mobile = "";

    static ArrayList transporterName ;
    static  ArrayList transporterMobile ;
    static ArrayList transporterEmail;
    public static Map<String, String> user_info = new HashMap<String, String>();

    //static public final String URL_LINK = "http://10.0.2.2:8080/android/LetsMove/";
    static public final String URL_LINK = "http://www.sachinapatel.com/LetsMove/";
    static StrictMode.ThreadPolicy th = new StrictMode.ThreadPolicy.Builder().build();



    ///////////////////////////////////send_user_signup_data_to_database_starts//////////////////////////////////////////
    public static void user_signup(String email, String name, String password, String mobile, String user_type) {
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);
        try {
            name = URLEncoder.encode(name, "utf-8");
            password = URLEncoder.encode(password, "utf-8");

            String link = URL_LINK + "signup_user.php?email=" + email + "&name=" + name + "&password=" + password + "&mobile=" + mobile + "&user_type=" + user_type;
            HttpGet httpGet = new HttpGet(link);
            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    ///////////////////////////////////send_user_signup_data_to_database_ends//////////////////////////////////////////

    ///////////////////////////////////////send_new_post_data_to_database /////////////////////////////////////////////

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
            user_email=URLEncoder.encode(user_email, "utf-8");

            String link = URL_LINK + "add_new_post.php?title=" + post_title + "&type=" + type_transport + "&weight=" + approx_weight + "&from=" + from_address + "&to=" + desti_address + "&amount=" + amount + "&selected_date=" + selected_date + "&pic_name=" + pic_name + "&email=" + user_email;

            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
            uploadImage();

        } catch (Exception e) {
            System.out.println("Error = " + e);


        }
    }
    //////////////////////////////////transporter_sigup_data//////////////////////////////////////////////////////////

    public static void signup_transporter(String email, String name, String password, String mobile, String user_type) {
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
            name=URLEncoder.encode(name,"UTF-8");
            email=URLEncoder.encode(email,"UTF-8");
            password=URLEncoder.encode(password,"UTF-8");
            mobile=URLEncoder.encode(mobile,"UTF-8");

            String link = URL_LINK + "signup_transporter.php?email=" + email + "&name=" + name + "&password=" + password + "&mobile=" + mobile + "user_type=" +user_type;
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
            JSONObject jsonObject5 = jsonArray.getJSONObject(3);

            status = jsonObject2.optString("status").toString();
            getname = jsonObject3.optString("name").toString();
            getRole = jsonObject4.optString("user_type").toString();
            getId = jsonObject5.optString("user_id").toString();
            System.out.println("My Role ======================" + getRole);


        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
        System.out.println("Status = " + status);
        System.out.println("I am " + getRole);
        return status;


    }

    ///////////////////////////////check_email_user/////////////////////////////////////////////////////
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
                //status=builder.toString();
            }
            String f = builder.toString();

            JSONObject jsonObject = new JSONObject(f);
            JSONArray jsonArray = jsonObject.optJSONArray("check_email");


            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            status = jsonObject2.optString("status").toString();
            getId = jsonObject2.optString("user_id").toString();
            getRole = jsonObject2.optString("user_type_id").toString();


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
        //String user_id = ListOfPost.user_id;
        //String post_detail = ListOfPost.posts_detail;
        System.out.println("Debugger in get_Post_Data!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ArrayList arrayList = new ArrayList();
        String status = null;


        UserBean userBean = null;
        try {
           // post_detail = URLEncoder.encode(post_detail,"utf-8");
           // HttpGet get2 = new HttpGet(URL_LINK + "getlist_post.php?user_id="+user_id+"&post_detail="+post_detail);
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
                if(counter==1){
                    userBean.setPost_id(jsonObject2.optString("post_id").toString());
                    counter++;
                }
                else if(counter == 2) {
                    //arrayList.add(jsonObject2.optString("user_id").toString());

                    userBean.setUser_id(jsonObject2.optString("user_id").toString());

                    counter++;
                } else if(counter == 3) {
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
                }else if (counter == 10) {
                    //arrayList.add(jsonObject2.optString("pickup_date").toString());
                    userBean.setMax_amount(jsonObject2.optString("max_amount").toString());
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

         transporterEmail = new ArrayList();
         transporterName = new ArrayList();
         transporterMobile = new ArrayList();

        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        try {
            HttpGet link = new HttpGet(URL_LINK + "get_all_transporters.php");
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
            JSONArray jsonArray = jsonObject.optJSONArray("details");
            int k = 1;
            String get_transporter_detail = null;


           for (int i = 0; i < jsonArray.length(); i++) {
               JSONObject jsonObject2 = jsonArray.getJSONObject(i);

               JSONObject jsonObjectTrans = jsonArray.getJSONObject(i);

               if(k == 1)
               {
                   get_transporter_detail= jsonObjectTrans.optString("email").toString();
                   transporterEmail.add(get_transporter_detail);
                    k++;
               }
               else if(k==2){
                   get_transporter_detail = jsonObjectTrans.optString("name").toString();
                    transporterName.add(get_transporter_detail);
                    k++;
               }
               else if(k==3){
                   get_transporter_detail = jsonObjectTrans.optString("mobile").toString();
                    transporterMobile.add(get_transporter_detail);

                   k=1;
               }


           }

        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
    }

    /////////////////////////////////////// get user details by id ///////////////////////////////////////////

    public static Map<String, String> get_user_details_by_id(String user_id){


        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);


        try {
            HttpGet link = new HttpGet(URL_LINK + "get_user_by_id.php?id=" + user_id);
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

            for(int i=0; i<jsonArray.length();i++){
                user_info.put("name",jsonObject2.optString("name").toString());
                user_info.put("email",jsonObject3.optString("email").toString());
                user_info.put("mobile",jsonObject4.optString("mobile").toString());

            }


        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Exception here");
        }
        //System.out.println("I am " + get_user_email +","+ get_user_name);
        return user_info;
    }

    ///////////////////////////////////////// edit user details /////////////////////////////////////////////

    public static void edit_user(String New_name, String New_email, String New_password, String New_mobile, String email_session){
        HttpClient httpClient = new DefaultHttpClient();

        StrictMode.setThreadPolicy(th);

        try {
            New_name=URLEncoder.encode(New_name,"UTF-8");
            New_email=URLEncoder.encode(New_email,"UTF-8");
            New_password=URLEncoder.encode(New_password,"UTF-8");
            New_mobile=URLEncoder.encode(New_mobile,"UTF-8");
            email_session=URLEncoder.encode(email_session, "UTF-8");

            String link = URL_LINK + "edit_user.php?new_email=" + New_email + "&new_name=" + New_name + "&new_password=" + New_password + "&new_mobile=" + New_mobile + "&current_user=" + email_session;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    }

    ///////////////////////////////////// Send Bid Data ///////////////////////////////////
    public static void send_bid_info(String post_id, String user_bidder_id, String bid_amount, String bid_description){
        HttpClient httpClient = new DefaultHttpClient();
        StrictMode.setThreadPolicy(th);
        int Post_id=0;
        int User_bidder_id=0;
        try{
            Post_id=Integer.parseInt(post_id);
            User_bidder_id=Integer.parseInt(user_bidder_id);
            //Post_id=URLEncoder.encode(Post_id, "UTF-8");
            //User_bidder_id = URLEncoder.encode(User_bidder_id, "UTF-8");
            bid_amount = URLEncoder.encode(bid_amount, "UTF-8");
            bid_description = URLEncoder.encode(bid_description, "UTF-8");

            String link=URL_LINK + "add_new_bid.php?post_id=" + Post_id + "&user_bidder_id=" + User_bidder_id + "&bid_amount=" + bid_amount + "&bid_description=" + bid_description;
            HttpGet httpGet = new HttpGet(link);

            httpClient.execute(httpGet);
        }
        catch(Exception e)
        {
            System.out.println("Error when sending bid data=" + e);
        }
    }


}