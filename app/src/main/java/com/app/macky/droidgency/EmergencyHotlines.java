package com.app.macky.droidgency;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.macky.droidgency.R;

public class EmergencyHotlines extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_emergency_hotlines, container, false);
            Button btnP = (Button) rootView.findViewById(R.id.btnPolice);
            Button btnR = (Button) rootView.findViewById(R.id.btnNDRRMC);
            Button btnF = (Button) rootView.findViewById(R.id.btnFire);
            btnP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:117"));
                    startActivity(callIntent);
                }
            });
            btnR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:029111406"));
                    startActivity(callIntent);
                }
            });
            btnF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:024260219"));
                    startActivity(callIntent);
                }
            });


            return rootView;
        }
    }

