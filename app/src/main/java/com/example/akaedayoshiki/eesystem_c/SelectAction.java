package com.example.akaedayoshiki.eesystem_c;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SelectAction extends AppCompatActivity {

    Handler handle;//一定時間後に処理するための変数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectaction);

        get_data();//データの受信と書き込み
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_data();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//画面表示時に再度起動された際にgetIntent()を更新する。
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
        if(stats.equals("0"))
            stats1_textview.setText("退室");
        else
            stats1_textview.setText("入室");

        handle = new Handler();
        handle.postDelayed(new back_wait_card(), 2000);//2秒後に画面切り替え

    }


    class back_wait_card implements Runnable {
        @Override
        public void run() {

            Intent intent = new Intent(SelectAction.this, WaitCard.class);
            finish();
            startActivity(intent);//カード読み取り画面に切り替え

        }
    }
}
