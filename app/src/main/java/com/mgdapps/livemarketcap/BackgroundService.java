package com.mgdapps.livemarketcap;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by HP on 12/14/2017.
 */

public class BackgroundService extends IntentService {

    public static final String Message_Request = "myRequest";
    public static final String Message_Response = "myResponse";
    public static final String Message_Message = "myMessage";

    public BackgroundService() {
        super("MY_Thread");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Log.e("From Onstart", "Started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("From OnDestroy", "Stopped");
    }

    private void SendNotification(String name, String margin) {
        if (name == null) {
            name = "";
        }
        if (margin == null) {
            margin = "";
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContentTitle(name).
                setStyle(new NotificationCompat.BigTextStyle().bigText(margin)).setContentText("Content Text")
                .setSmallIcon(R.drawable.tab_market_black).setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true);

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(1, builder.build());
    }
}
