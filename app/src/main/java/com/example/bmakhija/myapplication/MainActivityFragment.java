package com.example.bmakhija.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

   // private static final String[] INITIAL_PERMS={
     //       Manifest.permission.ACCESS_FINE_LOCATION,
       //     Manifest.permission.READ_CONTACTS
   // };
    Button one;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        //  we need to store this in root view variable as that will be required to be used to call findviewbyid method
        one = (Button)rootview.findViewById(R.id.MyLocation);
       // here we need to insert the onclicklistener
        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // on clicking this button i want maps activity to open up so lets write it
                // for this we need to create a intent variable

                Intent intent = new Intent(getActivity().getApplicationContext(),BharatMaps.class);
                startActivity(intent);

              //  Context context = getActivity().getApplicationContext();
               // CharSequence text = "Hello toast!";
                //int duration = Toast.LENGTH_SHORT;

                //Toast toast = Toast.makeText(context, text, duration);
                //toast.show();

            }
        });


        return rootview;

    }
}
