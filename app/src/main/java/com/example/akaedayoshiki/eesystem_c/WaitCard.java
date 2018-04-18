package com.example.akaedayoshiki.eesystem_c;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WaitCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitcard);

//        findViewById(R.id.button);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, SelectAction.class);
        startActivity(intent);
    }
}
