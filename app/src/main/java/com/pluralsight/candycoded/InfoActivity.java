package com.pluralsight.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

    public void createMapIntent(View view){
        Uri uriAddress = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");
        Intent map_intent = new Intent(Intent.ACTION_VIEW, uriAddress);
        map_intent.setPackage("com.google.android.apps.maps");

        if( map_intent.resolveActivity(getPackageManager()) != null){
            startActivity(map_intent);
        }

    }

    public void createPhoneIntent(View view) {
        Intent phone_intent= new Intent(Intent.ACTION_DIAL);
        phone_intent.setData(Uri.parse("tel:0123456789"));
        startActivity(phone_intent);
    }
    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***
}
