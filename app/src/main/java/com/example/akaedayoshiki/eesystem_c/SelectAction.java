package com.example.akaedayoshiki.eesystem_c;
//package com.example.akaedayoshiki.eesystem_c;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SelectAction extends AppCompatActivity {

    //    private Button enter;
//    private Button exit;
    Handler handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectaction);

        findViewById(R.id.enter);
        findViewById(R.id.exit);
        handle = new Handler();
        // 第２引数で切り替わる秒数(ミリ秒)を指定、今回は2秒

//
//        enter.setOnClickListener(this);
//        exit.setOnClickListener(this);
    }

    public void onClick(View view) {
        TextView textview1 = (TextView) this.findViewById(R.id.stats1);
        switch (view.getId()) {
            case R.id.enter:
                textview1.setText("入室");
                handle.postDelayed(new backwaitcard(), 2000);
                break;
            case R.id.exit:
                textview1.setText("退室");
                handle.postDelayed(new backwaitcard(), 2000);
                break;
        }
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
