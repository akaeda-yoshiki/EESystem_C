
package com.example.akaedayoshiki.eesystem_c;

import android.os.AsyncTask;


public class MyAsyncTask extends AsyncTask<String, Void, String> {
    Connect connect = new Connect();
    @Override
    protected void onPreExecute() {
        //バックグラウンド処理開始前にUIスレッドで実行される。
        //ダイアログの生成などを行う。
        connect.startVolley();
    }

    @Override
    protected String doInBackground(String... params) {
        //バックグラウンドで処理させる内容をここで記述。
        //AsyncTaskを使うにあたって、このメソッドの中身は必ず記述しなければいけない。
//        Connect connect = new Connect();
//        connect.startVolley();

        return "";
    }

//    @Override
//    protected void onProgressUpdate(Progress... values) {
//        //doInBackgroundの実行中にUIスレッドで実行される。
//        //引数のvaluesを使ってプログレスバーの更新などをする際は、ここに記述する。
//    }

    @Override
    protected void onPostExecute(String result) {
        //doInBackgroundが終了するとUIスレッドで実行される。
        //ダイアログの消去などを行う。
        //doInBackgroundの結果を画面表示に反映させる処理もここに記述。
    }
}