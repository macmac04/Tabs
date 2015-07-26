package com.app.macky.droidgency;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RespondActivity extends Activity {

    private static final String YES_ACTION = "reply" ;
    public NotificationManager mManager;
    public static final String beginText = "[Emergency Alert ";
    public static final String bTEnd = "]DroidGency App ";
    StringBuilder strAddress;
    DBHelper dbhelper;
    List<String> ac = new ArrayList<String>();
    static Context ct;
    int state;
    //gps
    double latitude;
    double longitude;
    LocationData lcd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond);
        ct = this.getApplicationContext();
        Notification();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -30;
        params.height = 1100;
        params.width = 1050;
        params.y = -50;
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Context context;
        context = getApplicationContext();
        mManager = (NotificationManager) getSystemService(context.NOTIFICATION_SERVICE);

        this.getWindow().setAttributes(params);
        Button btnY = (Button) findViewById(R.id.btnYes);
        Button btnN = (Button) findViewById(R.id.btnNo);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          SendingOk();
            }
        });

        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingSMS();
            }
        });
    }

    public void Notification() {

        int notificationId = 101;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, RespondActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        //Building notification layout
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Checkup Notification")
                        .setContentText("[Emergency Alert 2]Droidgency App Hey, are you okay ?")
                        .setContentIntent(viewPendingIntent);
                        notificationBuilder.setAutoCancel(true);

        Intent reply = new Intent();
        reply.setAction(YES_ACTION);
        PendingIntent pendingIntentReply = PendingIntent.getBroadcast(this, 12345, reply, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.logo, "Reply", pendingIntentReply);
        // instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and notify it using notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());

    }
    public void SendingSMS() {

        dbhelper = new DBHelper(this);
        ac = dbhelper.getAllNumbers();


                /* Send to each in Alert Contacts List */
        if (ac.isEmpty()) {
            Toast q = Toast.makeText(HomeFragment.ct, "Add contacts to alert!", Toast.LENGTH_LONG);
            q.show();
        }

        if (state != 3) {
            for (int i = 0; i < ac.size(); i++) {
                setData();
                sendSMS(ac.get(i), state);
            }
        }
    }
    public void SendingOk() {

        dbhelper = new DBHelper(this);
        ac = dbhelper.getAllNumbers();


                /* Send to each in Alert Contacts List */
        if (ac.isEmpty()) {
            Toast q = Toast.makeText(HomeFragment.ct, "Add contacts to alert!", Toast.LENGTH_LONG);
            q.show();
        }

        if (state != 3) {
            for (int i = 0; i < ac.size(); i++) {
                setOk();
                sendSMS(ac.get(i), state);
            }
        }
    }
    public void setOk(){
        state = 0;
    }

    public void setData() {
        state = 1;
        lcd = new LocationData(this);
        // check if GPS enabled
        if (lcd.canGetLocation()) {
            latitude = lcd.getLatitude();
            longitude = lcd.getLongitude();
            // Toast.makeText(getActivity().getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            lcd.showSettingsAlert();
        }
        getMyLocationAddress();
    }

    public void sendSMS(String num, int state) {
        String message = "";
        switch (state) {
			/* Safe Message */
            case 0:
                message = "Just letting you know I'm safe.";

                break;
			/* Help Message */
            case 1:
                message = "Request for help im here at\n\n " + strAddress.toString() + " https://maps.google.com/maps?q=" + latitude + "," + longitude + "";
                break;
			/* CheckUp Message */
            case 2:
                message = "Hey, are you okay?";
                break;
        }
        SmsManager sms = SmsManager.getDefault();
        String m = "";
        try {
            sms.sendTextMessage(num, null, beginText + state + bTEnd + message, null, null);
            m = "Sent Text";
        } catch (Exception e) {
            m = "Sending failed";
        }
        Toast t = Toast.makeText(ct, m, Toast.LENGTH_SHORT);
        t.show();
    }

    public void getMyLocationAddress() {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {

            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null) {

                Address fetchedAddress = addresses.get(0);
                strAddress = new StringBuilder();

                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }

                //  myAddress.setText("I am at: " +strAddress.toString());

            } else
                Toast.makeText(this, "No Address", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }




}

