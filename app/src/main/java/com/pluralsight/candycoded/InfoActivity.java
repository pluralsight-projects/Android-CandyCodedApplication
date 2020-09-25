package com.pluralsight.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class InfoActivity extends AppCompatActivity {

    private String mGeoInfo;
    private static final String GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps";

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
    public void createMapIntent(View view){
        mGeoInfo = "geo:0,0?q=618 E South St Orlando, FL 32801";
        Uri uUri = Uri.parse(mGeoInfo);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW,uUri);
        mapIntent.setPackage(GOOGLE_MAPS_PACKAGE);
        if(mapIntent.resolveActivity(getPackageManager()) != null){
            startActivity(mapIntent);
        }
    }

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***
}
