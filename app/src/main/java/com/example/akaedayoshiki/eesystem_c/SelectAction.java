package com.example.akaedayoshiki.eesystem_c;
//package com.example.akaedayoshiki.eesystem_c;


import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class SelectAction extends AppCompatActivity {

    //    private Button enter;
//    private Button exit;
    Handler handle;//一定時間後に処理するための変数
    private SoundPool soundPool;
    private int selectaction_wav;//入退室を選択時の効果音

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectaction);

        findViewById(R.id.enter);
        findViewById(R.id.exit);
        handle = new Handler();

        //音関係
        soundPool       = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );

        TextView name1_textview =  this.findViewById(R.id.name1);
        TextView grade1_textview =  this.findViewById(R.id.grade1);
        TextView time1_textview =  this.findViewById(R.id.time1);
        TextView stats1_textview =  this.findViewById(R.id.stats1);

        Intent intent = getIntent();
        name1_textview.setText(intent.getStringExtra("NAME"));
        grade1_textview.setText(intent.getStringExtra("GRADE"));
        time1_textview.setText(intent.getStringExtra("TIME"));
        String st = intent.getStringExtra("STATS");
        if(st == "1")
            stats1_textview.setText("入室");
        else
            stats1_textview.setText("退室");

    }

    //ボタンタップ時
    public void onClick(View view) {
        TextView textview =  this.findViewById(R.id.stats1);
        switch (view.getId()) {
            case R.id.enter://「入室」をタップ
                textview.setText(R.string.enter_message);
                action();
                break;
            case R.id.exit://「退室」をタップ
                textview.setText(R.string.exit_message);
                action();
                break;
        }
    }

    //入退室選択時の処理
    private void action(){
        handle.postDelayed(new backwaitcard(), 2000);//2秒後に画面切り替え
        soundPool.play(selectaction_wav, 1F, 1F, 0, 0, 1F);//効果音再生
        findViewById(R.id.enter).setVisibility(View.INVISIBLE);//ボタン非表示
        findViewById(R.id.exit).setVisibility(View.INVISIBLE);//ボタン非表示



    }

    class backwaitcard implements Runnable {
        @Override
        public void run() {
//            TextView textview1 = (TextView) findViewById(R.id.text);
//            textview1.setText("待機");
            Intent intent = new Intent(SelectAction.this, WaitCard.class);
            startActivity(intent);//カード読み取り画面に切り替え
        }
    }
}
