package com.pluralsight.candycoded;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pluralsight.candycoded.DB.CandyContract;
import com.pluralsight.candycoded.DB.CandyContract.CandyEntry;
import com.pluralsight.candycoded.DB.CandyDbHelper;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - ";
    public static final String HASHTAG_CANDYCODED = " #candycoded";
    String mCandyImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = DetailActivity.this.getIntent();

        if (intent != null && intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);

            CandyDbHelper dbHelper = new CandyDbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            cursor.moveToPosition(position);

            String candyName = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyContract.CandyEntry.COLUMN_NAME_NAME));
            String candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_PRICE));
            mCandyImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_IMAGE));
            String candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_DESC));


            TextView textView = (TextView) this.findViewById(R.id.text_view_name);
            textView.setText(candyName);

            TextView textViewPrice = (TextView) this.findViewById(R.id.text_view_price);
            textViewPrice.setText(candyPrice);

            TextView textViewDesc = (TextView) this.findViewById(R.id.text_view_desc);
            textViewDesc.setText(candyDesc);

            ImageView imageView = (ImageView) this.findViewById(
                    R.id.image_view_candy);
            Picasso.with(this).load(mCandyImageUrl).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    // ***
    // TODO - Task 4 - Share the Current Candy with an Intent
    // ***

    //This method should determine whether the intent is consumed or forwaded to get consumed
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //call the method to create the intent we want associated with the selection of a given option
        createShareIntent();
        //return the default call to super class
        return super.onOptionsItemSelected(item);
    }


    //this method will be used to create the shareIntent
    private void createShareIntent(){

        /*concatenate the attributes of the text element we want to share to make one that
        makes sense in our context
         */
        String fullCandyText=SHARE_DESCRIPTION+mCandyImageUrl+ HASHTAG_CANDYCODED;
        /*create an intent that will use a chooser to send data to an unspecified location
        that the user will specify*/
        Intent shareIntent =new Intent(Intent.ACTION_SEND);

        //set the type and format of data that will be presented
        shareIntent.setType("text/plain");


        /*put the key value pair that will denote the fullCandyText in a Bundle that can later be
        extracted in our target activity
         */
        shareIntent.putExtra(Intent.EXTRA_TEXT,fullCandyText);


        //start an activity with the intent shareIntent
        startActivity(shareIntent);

    }
}
