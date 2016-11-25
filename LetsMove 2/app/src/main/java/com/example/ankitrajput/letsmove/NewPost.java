package com.example.ankitrajput.letsmove;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewPost extends Activity implements AdapterView.OnItemSelectedListener {

    static String post_title, type_item , weight , pic_name, pickup_Date, max_amount;
    public static String selected_Date;
    private DatePicker datePicker;
    private Calendar calendar;
    static final int DATE_PICKER_ID = 1111;
    Button btn_setDate = null;
    private int year, month, day;
    public String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ///////to upload image////
    final static String TAG = NewPost.class.getSimpleName();

    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "http://www.conestoga.freeoda.com/LetsMove/image_upload.php";


    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri; // file url to store image/video

    private Button btnCapturePicture, btnRecordVideo;
    Calendar myCalendar = Calendar.getInstance();
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    static  String filePath;
    String imagePath;
    Button btn_choose_pic;
    Boolean selectedDate=false;
    Boolean btnforImage=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerProductType);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinnerApproxWeight);
        btn_setDate = (Button) findViewById(R.id.button_setDate);
        btn_choose_pic = (Button) findViewById(R.id.button_choose_pic);
        Button btn_post_new = (Button) findViewById(R.id.button_next);


        final EditText edittext_post_title = (EditText) findViewById(R.id.editText_post_title);
        final EditText edittext_max_amount = (EditText) findViewById(R.id.editText_maximumAmount);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List categories = new ArrayList();
        categories.add("Personal");
        categories.add("Vehicle");
        categories.add("Household");
        categories.add("Electronics");
        categories.add("Pet");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // Spinner Drop down elements
        List categories2 = new ArrayList();
        categories2.add("0 - 20 lb");
        categories2.add("20 - 50 lb");
        categories2.add("50 - 150 lb");
        categories2.add("150 - 250 lb");
        categories2.add("250 - 350 lb");
        categories2.add("> 350 lb");




        ///////////////////////////////////////////////////////////spinner_creating_code///////////////////////////////////
        // Creating adapter for spinner
        ArrayAdapter dataAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        ////////////////////////////////////////////////////////////////Set Date Onclick ////////////////////////////

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        btn_setDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectedDate=true;
                DatePickerDialog dpd = new DatePickerDialog(NewPost.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


            }
        });

        ///////////////////////////////////upload_image_starts/////////////////////////////////////////////////////

        btn_choose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnforImage=true;
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

        //////////////////////upload_image_ends/////////////////////////////////////////////////////


        btn_post_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File f = new File(filePath);
                post_title = edittext_post_title.getText().toString();
                type_item = spinner.getSelectedItem().toString();
                weight = spinner2.getSelectedItem().toString();
                pic_name = f.getName();
                pickup_Date = selected_Date;
                max_amount = edittext_max_amount.getText().toString();
                if(!post_title.isEmpty() && !type_item.isEmpty() && !weight.isEmpty() && !pic_name.isEmpty() && !pickup_Date.isEmpty() && !max_amount.isEmpty()){
                    if(selectedDate) {
                        if(btnforImage) {
                            System.out.println(post_title + "  " + type_item + " " + weight + " " + pic_name + " " + pickup_Date + " " + max_amount + "");
                            startActivity(new Intent(NewPost.this, AddressActivity.class));
                        }
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewPost.this);
                    builder.setMessage("One or Multiple field of New Post can not be Empty!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // do things
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //  .setText(sdf.format(myCalendar.getTime()));
        selected_Date = sdf.format(myCalendar.getTime());
        btn_setDate.setText(selected_Date);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        //  btn_setDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(column_index);
        System.out.println("Imagepath  ==  " + imagePath);
        return cursor.getString(column_index);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                    System.out.println(filePath);
                    btn_choose_pic.setText("Done");
                }
        }
    }

}