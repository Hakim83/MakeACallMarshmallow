package com.example.mohammed.makeacall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    CheckBox chkDirectCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
         chkDirectCall= (CheckBox) findViewById(R.id.chkDirectCall);
        Button btnCall = (Button) findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //specify action depending on option checked
                String intentAction;
                if (chkDirectCall.isChecked()) {
                    intentAction = Intent.ACTION_CALL;
                } else {
                    intentAction = Intent.ACTION_DIAL;
                }

                //prepare intent and fire
                String number = etPhoneNumber.getText().toString();
                if (!number.isEmpty()) {
                    Intent callIntent = new Intent(intentAction, Uri.parse("tel:" + number));
                    startActivity(callIntent);
                } else {
                    //You can't call an empty number
                    Toast.makeText(getApplicationContext(), "Please Enter a number",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        ensureCallPermission();
    }

    private void ensureCallPermission() {

        // Check whether we don't already have granted a permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Since we don't granted permission, request permission from user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            //nothing to do more, wait for response, till that disable
            // ability for direct calling
            chkDirectCall.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! we can use it,
                    chkDirectCall.setEnabled(true);

                } else {

                    // permission denied, boo! We should keep the call functionality
                    // disabled.. We also my inform user
                    Toast.makeText(getApplicationContext(),"Sorry, we can't call direct !",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
        }

}
