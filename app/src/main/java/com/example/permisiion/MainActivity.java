package com.example.permisiion;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    public static final int REQUEST_STRORAGE_CODE=1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            request_permission();
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkpermission()){
                    Toast.makeText(MainActivity.this, "permission granted 1", Toast.LENGTH_SHORT).show();
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        request_permission();
                    }
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void request_permission() {
        Toast.makeText(this, "asked", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this,
                new String[]
                        {Manifest.permission.POST_NOTIFICATIONS},
                REQUEST_STRORAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean granted = true;
        if(requestCode == REQUEST_STRORAGE_CODE){
            //got a result from the user
            Log.v("TAG","got a result ");
            if(grantResults.length>0){
                for(int result:grantResults){
                    if(result ==PackageManager.PERMISSION_DENIED)
                        granted=false;
                }
            }

        }else{
            granted=false;
        }

        if(granted){
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            checkpermission();
        }else{
            requestagain();

        }

    }

    private void requestagain() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.POST_NOTIFICATIONS)){
            Toast.makeText(this, "permission is needed", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                request_permission();
            }
        }
    }

    public boolean checkpermission(){

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED){
            return true;
        }
        else
            return false;
    }
}