package com.example.ankitrajput.letsmove;

import android.app.Service;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.*;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by spatel7166 on 2016-11-19.
 */

public class MyService extends Service {

    ScheduledExecutorService scheduleTaskExecutor ;

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.

        //HandlerThread thread = new HandlerThread("ServiceStartArguments",
        //        Process.THREAD_PRIORITY_BACKGROUND);
        //thread.start();


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 2 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {

                //System.out.println(DB.Post_id+" " + DB.User_poster_id+" " + DB.User_bidder_id+ " " + DB.Bid_Amount + " " + DB.Bid_Description);
                //System.out.println("Here Started");
                //PostDetails postDetails = new PostDetails();

//                if(!DB.Post_id.isEmpty() || !DB.User_bidder_id.isEmpty() || !DB.User_poster_id.isEmpty() || !DB.Bid_Amount.isEmpty() || !DB.Bid_Description.isEmpty())
  //              {
    //                postDetails.addBidNotification();
      //          }

            }
        }, 0, 2, TimeUnit.SECONDS);
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduleTaskExecutor.shutdownNow();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void sendDataBack(){
        Intent intent = new Intent("data");
        intent.putExtra("data", "1");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
