 package com.app.macky.droidgency;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    private TextView mTextBox;

    public static String REFRESH_ACTION = "com.app.macky.droidgency.REFRESH";

    public static final String beginText = "[Emergency Alert ";
    public static final String bTEnd = "]DroidGency App ";
    StringBuilder strAddress;
    ContactFragment cf;
    DBHelper dbhelper;
    List<String> ac = new ArrayList<String>();
    static Context ct;
    int state;
    //gps
    double latitude;
    double longitude;
    LocationData lcd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // *-- wear code
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        /// ----*


        ct = this.getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.activity_menu, container, false);

        mTextBox = (TextView) view.findViewById(R.id.text);
        ImageButton btnSend = (ImageButton) view.findViewById(R.id.btnSOS);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingSMS();
            }
        });

        return view;
    }

    public void SendingSMS() {

        dbhelper = new DBHelper(getActivity());
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


    public void setData() {
        state = 1;
        lcd = new LocationData(getActivity());
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

        Geocoder geocoder = new Geocoder(this.getActivity(), Locale.ENGLISH);

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
                Toast.makeText(getActivity(), "No Address", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getActivity(), "Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, messageFilter);
    }

    @Override
    public void onStop() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();

    }


    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {


            String message = intent.getStringExtra(getString(R.string.message_key));
            mTextBox.setText(message);
            SendingSMS();
            Log.i("Incoming message", message);

        }
    }

    public class WidgetAppReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                   int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    // do something for the widget that has appWidgetId = widgetId

                           SendingSMS();


                }

            }
        }


        public class ReplyReceiver extends BroadcastReceiver {


            private static final String YES_ACTION = "reply";

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (YES_ACTION.equals(action)) {
                   SendingSMS();
                }
            }

            // handheld Broadcast
            public class WidgetReciever extends BroadcastReceiver {
                @Override
                public void onReceive(Context context, Intent intent) {

                    final String action = intent.getAction();
                    if (action.equals(REFRESH_ACTION)) {
                        //do something here
                        SendingSMS();

                    }


                }

            }
        }
    }
}
