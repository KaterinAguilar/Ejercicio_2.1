package com.example.ejercicio_21;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private static final int TOMA_VIDEO = 1;
    private VideoView vv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vv1 = findViewById(R.id.videoView);
    }

    public void tomarvideo(View v) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, TOMA_VIDEO);

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TOMA_VIDEO && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            vv1.setVideoURI(videoUri);
            vv1.start();

            try {
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream in = videoAsset.createInputStream();
                FileOutputStream archivo = openFileOutput(crearNombreArchivoMp4(), Context.MODE_PRIVATE);
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    archivo.write(buf, 0, len);
                }

            } catch (IOException e) {
                Toast.makeText(this, "Problemas en la grabacion", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private String crearNombreArchivoMp4() {
        String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombre = fecha + ".mp4";
        return nombre;
    }


}




