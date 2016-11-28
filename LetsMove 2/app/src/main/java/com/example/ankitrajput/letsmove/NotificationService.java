package com.example.ankitrajput.letsmove;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NotificationService extends Service {

    ScheduledExecutorService scheduleTaskExecutor ;
    static ArrayList notification_data;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
       //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        //To get the user id of login user
        SharedPreferences preferences = getSharedPreferences("login_data", MODE_PRIVATE);
        final String id = preferences.getString("user_id", null);

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 5 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {

                boolean netStatus = isNetworkAvailable();
                if(netStatus == true){
                    notification_data = DB2.get_bid_notification(id);
                    if(!(notification_data.isEmpty())){
                        sendDataBack();

                    }
                    else{
                        System.out.println("Here only");
                    }
                }
                else{
                    System.out.println("No Internet Connection");
                }


            }
        }, 0, 5, TimeUnit.SECONDS);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduleTaskExecutor.shutdownNow();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void sendDataBack(){

            //Define sound URI
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            android.support.v7.app.NotificationCompat.Builder builder = (android.support.v7.app.NotificationCompat.Builder)new android.support.v7.app.NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.iconactionbar)
                    .setContentTitle("LetsMove")
                    //.setContentText(""+NotificationService.notification_data.get(0)+"  (Bid Amount = "+NotificationService.notification_data.get(3)+")")
                    //.setContentText(""+NotificationService.notification_data.get(4))
                    .setSound(soundUri)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Got Bid on your post \n"+NotificationService.notification_data.get(0)+"\n(Bid Amount = "+NotificationService.notification_data.get(3)+")"));

        builder.setAutoCancel(true);

            Intent notificationIntent = new Intent(this, UserHome.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

        /*  Intent intent = new Intent("data");
        intent.putExtra("data", "1");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        */
    }

    //To check that internet is connected in mobile or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
