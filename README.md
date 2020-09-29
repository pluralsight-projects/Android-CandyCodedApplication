# Add Implicit and Explicit Intents to an Android Candy Store App

This repo contains the code for the Pluralsight Project "Add Implicit and Explicit Intents to an Android Candy Store App," located here: https://app.pluralsight.com/projects/add-implicit-and-explicit-intents-to-an-android-candy-store-app/

Android-CandyCodedApplication
 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent infoIntent = new Intent ( this, InfoActivity.class );
        startActivity ( infoIntent );
        return super.onOptionsItemSelected ( item );
    }