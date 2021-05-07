package com.example.audioandvedio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private int audioCode = 1009;
    private boolean audioPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Clicked(View view) {

        switch (view.getId()){
            case R.id.recordAudio:
                if(!audioPermissionGranted){
                    checkPermissionForAudio();
                }
                Intent intent1 = new Intent(MainActivity.this,RecordAudio.class);
                startActivity(intent1);
                break;
            case R.id.recordVideo:
                Intent intent2 = new Intent(MainActivity.this,RecordVideo.class);
                startActivity(intent2);
                break;
        }

    }

    public boolean checkPermissionForAudio(){
        int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO);
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},audioCode);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==audioCode){

            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Audio Accessible",Toast.LENGTH_SHORT).show();
                audioPermissionGranted=true;
            }else{
                Toast.makeText(MainActivity.this,"Please Give Permission",Toast.LENGTH_SHORT).show();
            }

        }

    }

}