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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class WaitCard extends AppCompatActivity {

    private SoundPool soundPool;
    private int cardread_wav;//読み取り時の効果音
    private NfcAdapter mNfcAdapter;
    private PendingIntent pendingIntent;
    //タイミングは、タグ発見時とする。
    private IntentFilter[] intentFilter = new IntentFilter[]{
            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    };


    //反応するタグの種類を指定。
    private String[][] techList = new String[][]{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitcard);

        //音関係
        soundPool       = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
        cardread_wav = soundPool.load(this, R.raw.waitcard, 1 );

//NFCを見つけたときに反応させる
        //PendingIntent→タイミング（イベント発生）を指定してIntentを発生させる
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()), 0);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, techList);
    }

    @Override
    public void onPause(){
        super.onPause();

        //アプリが表示されてない時は、NFCに反応しなくてもいいようにする
        mNfcAdapter.disableForegroundDispatch(this);
    }

    //ボタンがタップされた時の処理
    public void onClick(View view) {
        soundPool.play(cardread_wav, 1F, 1F, 0, 0, 1F);//効果音再生
//        Intent intent = new Intent(this, SelectAction.class);//入退室選択画面に切り替え
//        startActivity(intent);
//        String POST_URL="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/server/readcard.php";
//        MyAsyncTask task = new MyAsyncTask();
//        task.execute(POST_URL);

//        String POST_URL="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/server/readcard.php";
//        MyAsyncTask task = new MyAsyncTask();
//        task.execute(POST_URL);

        RequestQueue postQueue;
        postQueue = Volley.newRequestQueue(this);
        //サーバーのアドレス任意
        String POST_URL="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/EESystem_S/insert.php";
//        String POST_URL="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/EESystem_S/insert.php?name=gggg&id=gg&grade=ggg";

        StringRequest stringReq=new StringRequest(Request.Method.GET ,POST_URL,

                //通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(WaitCard.this,"通信に成功しました。",Toast.LENGTH_SHORT).show();
                    }
                },

                //通信失敗
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(WaitCard.this,"通信に失敗しました。",Toast.LENGTH_SHORT).show();
                    }
                }){

            //送信するデータを設定
            @Override
            protected Map<String,String> getParams(){

                //今回は[FastText：名前]と[SecondText：内容]を設定
                Map<String,String> params = new HashMap<>();
                params.put("id","1111");
                params.put("name","stats");
                params.put("grade","aa");
                return params;
            }
        };
        postQueue.add(stringReq);
//        Connect connect = new Connect();
//        connect.startVolley();

    }

    //NFCをタッチした後の処理
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        String action = intent.getAction();
        if(TextUtils.isEmpty(action)){
            return;
        }

        if(!action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)){
            return;
        }

        //NFCのIDを取得。byte配列。
        byte[] rawId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        String id = bytesToString(rawId);
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();

//        String POST_URL="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/server/readcard.php";
//        MyAsyncTask task = new MyAsyncTask();
//        task.execute(POST_URL);


//        Intent go_intent = new Intent(this, SelectAction.class);//入退室選択画面に切り替え
//        startActivity(go_intent);
    }


    //byte配列をStringにして返す
    public String bytesToString(byte[] bytes){
        StringBuilder buffer = new StringBuilder();
        boolean isFirst = true;

        for(byte b : bytes){
            if(isFirst){
                isFirst = false;
            } else {
                buffer.append("-");
            }
            //負の場合があるので「& 0xff」をつける。
            buffer.append(Integer.toString(b & 0xff));
        }
        return buffer.toString();
    }
}

