package com.example.akaedayoshiki.eesystem_c;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;


public class Connect extends AppCompatActivity {
    public void startVolley() {

        //queue
        RequestQueue postQueue = Volley.newRequestQueue(this);

        //サーバーのアドレス任意
        String POST_URL="http://192.168.0.159/2018grade4/kaihatu_zemi/akaeda/server/readcard.php";

        StringRequest stringReq=new StringRequest(Request.Method.POST,POST_URL,

                //通信成功
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(Connect.this,"通信に成功しました。",Toast.LENGTH_SHORT).show();
                    }
                },

                //通信失敗
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(Connect.this,"通信に失敗しました。",Toast.LENGTH_SHORT).show();
                    }
                }){

            //送信するデータを設定
            @Override
            protected Map<String,String> getParams(){

                //今回は[FastText：名前]と[SecondText：内容]を設定
                Map<String,String> params = new HashMap<String,String>();
                params.put("FastText","1111");
                params.put("SecondText","stats");
                return params;
            }
        };
        postQueue.add(stringReq);
    }

}