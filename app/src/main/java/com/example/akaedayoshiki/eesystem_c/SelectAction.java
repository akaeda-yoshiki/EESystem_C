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
import android.widget.Toast;

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


        //音関係
        soundPool       = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
//        Intent intent = getIntent();
        get_data();
        handle = new Handler();
        handle.postDelayed(new backwaitcard(), 2000);//2秒後に画面切り替え
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_data();
        handle = new Handler();
        handle.postDelayed(new backwaitcard(), 2000);//2秒後に画面切り替え
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //画面表示時に再度起動された際にgetIntent()を更新する。
        setIntent(intent);
//        get_data(intent);
    }

    private void get_data(){
        TextView name1_textview =  this.findViewById(R.id.name1);
        TextView grade1_textview =  this.findViewById(R.id.grade1);
        TextView time1_textview =  this.findViewById(R.id.time1);
        TextView stats1_textview =  this.findViewById(R.id.stats1);

        Intent intent = getIntent();
        String stats = intent.getStringExtra("ST");
        name1_textview.setText(intent.getStringExtra("NAME"));
        grade1_textview.setText(intent.getStringExtra("GRADE"));
        time1_textview.setText(intent.getStringExtra("TIME"));
//        stats1_textview.setText(stats);
        if(stats.equals("0"))
            stats1_textview.setText("退室");
        else
            stats1_textview.setText("入室");
//        stats1_textview.setText(intent.getStringExtra("STATS"));
//        Toast.makeText(SelectAction.this,intent.getStringExtra("STATS1"), Toast.LENGTH_SHORT).show();



    }


    class backwaitcard implements Runnable {
        @Override
        public void run() {

            Intent intent = new Intent(SelectAction.this, WaitCard.class);
            finish();
            startActivity(intent);//カード読み取り画面に切り替え

        }
    }
}
