package com.example.audioandvedio;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RecordAudio extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private TextView textView;
    private Button button;
    private long name;
    private boolean enabled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);
        button = findViewById(R.id.playRaudio);
        button.setEnabled(enabled);
        mediaRecorder = new MediaRecorder();
        textView = findViewById(R.id.textView);

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Playing Audio...");
                File file = new File(getFilesDir(),name+".3gp");
                Uri uri = Uri.fromFile(file);
                MediaPlayer mediaPlayer = MediaPlayer.create(RecordAudio.this,uri);
                mediaPlayer.start();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void iWasClicked(View view) {

        switch(view.getId()){
            case R.id.startRecord:
                enabled = false;
                button.setEnabled(enabled);
                textView.setText("Recording Audio...");
                File file = getFile();
                mediaRecorder.setOutputFile(file);
                try {
                    mediaRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaRecorder.start();
                break;
            case R.id.pauseRecord:
                textView.setText("Recording Paused...");
                mediaRecorder.pause();
                break;
            case R.id.resumeRecord:
                textView.setText("Recording Audio...");
                mediaRecorder.resume();
                break;
            case R.id.overRecord:
                enabled=true;
                button.setEnabled(enabled);
                textView.setText("Recording Completed!!!");
                mediaRecorder.stop();
                mediaRecorder.release();
                break;

        }

    }

    private File getFile(){
        name = System.currentTimeMillis();
        File file = new File(getFilesDir(),name+".3gp");
        return file;
    }



}