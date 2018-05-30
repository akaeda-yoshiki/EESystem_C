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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class WaitCard extends AppCompatActivity {

    private int buttun_flag = 0;//1:ID確認が押されている  0:戻るが押されている
    private SoundPool soundPool;
    private int cardread_wav;//読み取り時の効果音
    private NfcAdapter mNfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilter = new IntentFilter[]{//タイミングは、タグ発見時とする。
            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    };
    private String send_name = "", send_grade = "", send_stats = "", time1 = "", time2 = "";

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
//        cardread_wav = soundPool.load(this, R.raw.waitcard, 1 );

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
//        soundPool.play(cardread_wav, 1F, 1F, 0, 0, 1F);//効果音再生

        TextView textView =  this.findViewById(R.id.textView);
        textView.setText("IDを確認します。\nカードをかざしてください。");

        Button button = this.findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);

        Button back = this.findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        buttun_flag = 1;
    }

    public void onbackClick(View view) {
        soundPool.play(cardread_wav, 1F, 1F, 0, 0, 1F);//効果音再生

        TextView textView =  this.findViewById(R.id.textView);
        textView.setText("カードをかざしてください");

        Button button = this.findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);

        Button back = this.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);
        buttun_flag = 0;
    }
    //次のactivityへデータの送信と偏移
    public void goto_selectaction(){
        if(send_grade != "" && send_name != "") {
            Intent intent = new Intent(WaitCard.this, SelectAction.class);//入退室選択画面に切り替え
            intent.putExtra("NAME", send_name);
            intent.putExtra("GRADE", send_grade);
            intent.putExtra("TIME", time2);
            intent.putExtra("ST", send_stats);
            send_name = "";
            send_grade = "";
            send_stats = "";
            time1 = "";
            time2 = "";
            startActivity(intent);
        }
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

        if(buttun_flag == 0)
          send_id(id);
        else if(buttun_flag == 1){
            TextView textView = this.findViewById(R.id.textView);
            textView.setText("あなたのIDは\n"+id+"\nです。");
        }

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

    public void send_id(String id){
        updata_time();

        RequestQueue postQueue;
        postQueue = Volley.newRequestQueue(this);
        //サーバーのアドレス
        String url="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/EESystem_S/insert.php";
        url+="?id="+id+"&time1="+time1+"&time2="+time2;
        StringRequest stringReq=new StringRequest(Request.Method.GET ,url,

                //通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(WaitCard.this,"通信に成功しました。",Toast.LENGTH_SHORT).show();
                        response = response.substring(2, response.length() - 2);
                        String[] data = response.split(",");
                        String check = data[0].substring(1, 6);
                        if(check.equals("stats")){
                            TextView Textview =  findViewById(R.id.textView);
                            Textview.setText("このIDは登録されていません");
                        }
                        else {
                            send_name = data[1].substring(8, data[1].length() - 1);
                            send_grade = data[2].substring(9, data[2].length() - 1);
                            send_stats = data[3].substring(9, data[3].length() - 1);
                            goto_selectaction();
                        }

                        //デバッグ用
//                        String[] str = send_time.split(" ");
//                        TextView Textview =  findViewById(R.id.textView);
//                        Textview.setText(time1);





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
//                params.put("id","1111");
//                params.put("name","stats");
//                params.put("grade","aa");
                return params;
            }
        };
        postQueue.add(stringReq);
    }

    public  void updata_time(){
        Calendar calendar;

        //時刻取得
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        time1 += year;
        if(month < 10)
            time1 += "0" + month;
        else
            time1 += month;

        if(day < 10)
            time1 += "0" + day;
        else
            time1 += day;

        if(hour < 10)
            time2 += "0" + hour;
        else
            time2 += hour;

        time2 += ":";

        if(minute < 10)
            time2 += "0" + minute;
        else
            time2 += minute;
    }
}

