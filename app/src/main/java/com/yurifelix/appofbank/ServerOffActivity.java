package com.yurifelix.appofbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ServerOffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_off);
    }

    public void finalizar(View v){
        finish();
    }


}
