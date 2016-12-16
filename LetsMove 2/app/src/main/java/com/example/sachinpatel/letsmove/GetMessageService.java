package com.example.sachinpatel.letsmove;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GetMessageService extends Service {

    ScheduledExecutorService scheduleTaskExecutor ;
    static ArrayList message_data;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 5 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {

                boolean netStatus = isNetworkAvailable();
                if(netStatus == true){
                    message_data = DB2.get_Message(ChatActivity.user_id, ChatActivity.to_id);

                    if(message_data.size() > ChatActivity.arrayList_message.size()){
                        sendDataBack();

                    }
                    else{
                        System.out.println("Get Message Service");
                    }
                }
                else{
                    System.out.println("No Internet Connection");
                }


            }
        }, 0, 2, TimeUnit.SECONDS);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduleTaskExecutor.shutdownNow();
    }

    public void sendDataBack(){
        Intent intent = new Intent("data_message");
        intent.putExtra("data_message", "1");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    //To check that internet is connected in mobile or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
