package com.example.akaedayoshiki.eesystem_c;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WaitCard extends AppCompatActivity {

//    private SoundPool soundPool;
//    private int cardread_wav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitcard);

//        AudioAttributes attr = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            attr = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .build();
//            soundPool = new SoundPool.Builder()
//                    .setAudioAttributes(attr)
//                    .setMaxStreams(1)
//                    .build();
//        }
//        cardread_wav = soundPool.load(this, R.raw.cardread, 1);
    }

    public void onClick(View view) {
//        soundPool.play(cardread_wav, 1F, 1F, 0, 0, 1F);
        Intent intent = new Intent(this, SelectAction.class);
        startActivity(intent);
    }
}
