package com.example.audioandvedio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RecordVideo extends AppCompatActivity {

    private VideoView videoView;
    private int CODE = 890;
    private long name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
       videoView = findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(RecordVideo.this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

    }

    public void recordVideo(View view) {
        if(true){
            hasCamera();
        }

             Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

             if(intent.resolveActivity(getPackageManager())!=null){
                 startActivityForResult(intent,CODE);
             }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CODE){
            if(resultCode==RESULT_OK){
                File file = getFile();
                Uri uri = data.getData();


                try {
                    InputStream iStream =   getContentResolver().openInputStream(uri);
                    byte[] inputData = getBytes(iStream);
                    FileOutputStream fos  = new FileOutputStream(file);
                    fos.write(inputData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                videoView.setVideoURI(uri);
                videoView.start();
            }
        }
    }

    private File getFile(){
        name = System.currentTimeMillis();
        File file = new File(getFilesDir(),name+".mp4");
        return file;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private boolean hasCamera(){
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                    return  true;
        }else{
            return false;
        }

    }



}