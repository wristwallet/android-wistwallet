package com.example.ristwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openWallet(View view) {
        // go to wallet activity
        Intent intent = new Intent(this, WalletActivity.class);
        startActivity(intent);
    }
}