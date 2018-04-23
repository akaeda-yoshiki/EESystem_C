package com.example.akaedayoshiki.eesystem_c;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.nfc.*;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.Toast;



public class WaitCard extends AppCompatActivity {

    private SoundPool soundPool;
    private int cardread_wav;//読み取り時の効果音
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitcard);

        //音関係
        soundPool       = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
        cardread_wav = soundPool.load(this, R.raw.waitcard, 1 );


    }

    @Override
    protected void onResume() {
        super.onResume();

        //▼▼▼▼ここから
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //▼NFCの機能判定
        //NFC機能なし機種
        if(mNfcAdapter == null){
            Toast.makeText(getApplicationContext(), "no Nfc feature", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //NFC通信OFFモード
        if(!mNfcAdapter.isEnabled()){
            Toast.makeText(getApplicationContext(), "off Nfc feature", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //▲NFCの機能判定

        //NFCを見つけたときに反応させる
        //PendingIntent→タイミング（イベント発生）を指定してIntentを発生させる
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()), 0);

        //タイミングは、タグ発見時とする。
        IntentFilter[] intentFilter = new IntentFilter[]{
                new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        };

        //反応するタグの種類を指定。
        String[][] techList = new String[][]{
                {
                        android.nfc.tech.NfcA.class.getName(),
                        android.nfc.tech.NfcB.class.getName(),
                        android.nfc.tech.IsoDep.class.getName(),
                        android.nfc.tech.MifareClassic.class.getName(),
                        android.nfc.tech.MifareUltralight.class.getName(),
                        android.nfc.tech.NdefFormatable.class.getName(),
                        android.nfc.tech.NfcV.class.getName(),
                        android.nfc.tech.NfcF.class.getName(),
                }
        };
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, techList);
        //▲▲▲▲▲ここまで
    }

    @Override
    public void onPause(){
        super.onPause();

        //▼▼▼▼ここから
        //アプリが表示されてない時は、NFCに反応しなくてもいいようにする
        mNfcAdapter.disableForegroundDispatch(this);
        //▲▲▲▲▲ここまで
    }

    //ボタンがタップされた時の処理
    public void onClick(View view) {
        soundPool.play(cardread_wav, 1F, 1F, 0, 0, 1F);//効果音再生
        Intent intent = new Intent(this, SelectAction.class);//入退室選択画面に切り替え
        startActivity(intent);
    }

    //NFCをタッチした後の処理
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        //▼▼▼▼ここから
        String action = intent.getAction();
        if(TextUtils.isEmpty(action)){
            return;
        }

        if(!action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)){
            return;
        }

        //成功！と表示してみる
        Toast.makeText(getApplicationContext(), "成功！", Toast.LENGTH_SHORT).show();
        //▲▲▲▲▲ここまで
    }
}

