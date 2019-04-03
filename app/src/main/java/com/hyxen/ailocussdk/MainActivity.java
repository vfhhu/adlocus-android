package com.hyxen.ailocussdk;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hyxen.ailocus.AdLocus;


public class MainActivity extends AppCompatActivity {

    private static final int TAG_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView text_key=findViewById(R.id.text_key);
        text_key.setText(getString(R.string.fcm_app_key));


        FirebaseApp.initializeApp(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initSDK();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, TAG_LOCATION);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAG_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initSDK();
                } else {
                    initSDK();
                }
                return;
            }
        }
    }
    public void initSDK(){
        String token = FirebaseInstanceId.getInstance().getToken();
        if(!TextUtils.isEmpty(token)){
            TextView text_token=findViewById(R.id.text_token);
            text_token.setText(token);
        }

        AdLocus.getInstance(this)
                .checkUserStatement(!TextUtils.isEmpty(token) ? token : "",
                        getString(R.string.fcm_app_key), getPackageName(), getString(R.string.app_key));
    }


    public void copyData(View v){
        String key=getString(R.string.fcm_app_key);
        String token = FirebaseInstanceId.getInstance().getToken();

        String sendData="key= "+key+"   \ntoken= "+token;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sendData);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }
}
