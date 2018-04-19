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
    Handler handle;
    private SoundPool soundPool;
    private int selectaction_wav;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectaction);

        findViewById(R.id.enter);
        findViewById(R.id.exit);
        handle = new Handler();
        soundPool       = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
        selectaction_wav = soundPool.load(this, R.raw.selectaction, 1 );

//        findViewById(R.id.info_layout).setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        TextView textview =  this.findViewById(R.id.stats1);
        switch (view.getId()) {
            case R.id.enter:
                textview.setText(R.string.enter_message);
                action();
                break;
            case R.id.exit:
                textview.setText(R.string.exit_message);
                action();
                break;
        }
    }

    private void action(){
        handle.postDelayed(new backwaitcard(), 2000);
        soundPool.play(selectaction_wav, 1F, 1F, 0, 0, 1F);
        findViewById(R.id.enter).setVisibility(View.INVISIBLE);
        findViewById(R.id.exit).setVisibility(View.INVISIBLE);

        calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TextView textview =  this.findViewById(R.id.time1);
        textview.setText(hour + "時" + minute + "分");

    }

    class backwaitcard implements Runnable {
        @Override
        public void run() {
//            TextView textview1 = (TextView) findViewById(R.id.text);
//            textview1.setText("待機");
            Intent intent = new Intent(SelectAction.this, WaitCard.class);
            startActivity(intent);
        }
    }
}
