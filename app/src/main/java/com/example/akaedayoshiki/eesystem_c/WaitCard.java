package com.example.akaedayoshiki.eesystem_c;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WaitCard extends AppCompatActivity {

    private SoundPool soundPool;
    private int cardread_wav;//読み取り時の効果音


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitcard);

        //音関係
        soundPool       = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
        cardread_wav = soundPool.load(this, R.raw.waitcard, 1 );


    }

    //ボタンがタップされた時の処理
    public void onClick(View view) {
        soundPool.play(cardread_wav, 1F, 1F, 0, 0, 1F);//効果音再生
        Intent intent = new Intent(this, SelectAction.class);//入退室選択画面に切り替え
        startActivity(intent);
    }
}
