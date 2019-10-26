package com.pluralsight.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Uri uri = Uri.parse("android.resource://com.codeschool.candycoded/" + R.drawable.store_front);
        ImageView candyStoreImageView = (ImageView)findViewById(R.id.image_view_candy_store);
        Picasso.with(this).
                load(uri).
                into(candyStoreImageView);


    }

    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***
    //create a method that will be executed after the textview_address ClickListener registers a click
    public void createMapIntent(View view){
        //create a uri from the string in the parameter using the Uri class
        Uri locationAddress=Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");


        //create an implicit intent that will create a view for a given uri parameter
        Intent mapIntent=new Intent(Intent.ACTION_VIEW,locationAddress);

        //make the intent explicit by specifying the app it will use to create the view for the address
        mapIntent.setPackage("com.google.android.apps.maps");

        //return a component of the activity that is required to start the intent for tests
        if(mapIntent.resolveActivity(getPackageManager())!=null){

            //if the component exists then we proceed to start an activity that with our intent
            startActivity(mapIntent);
        }

    }

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***

    //a method that will be executed after the textview_phone ClickListener registers a click
    public void createPhoneIntent(View view){

        //create a uri from the string in the parameter using the Uri class
        Uri phoneAddress=Uri.parse("tel:0123456789");

        /*create an implicit intent that will launch a phone dialer application with some number entered as
        specified by the intent content
         */
        Intent phoneIntent=new Intent(Intent.ACTION_DIAL);

        //make the intent operate on the data in the phoneaddress Uri
        phoneIntent.setData(phoneAddress);

        //start an activity with the phoneIntent
        startActivity(phoneIntent);

}}
