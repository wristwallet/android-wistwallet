package com.example.ristwallet;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

    }

    public void changeToEthereum(View view) {
        NetworkState.getNetwork().changeNetwork("Ethereum");
        finish();
    }

    public void changeToGnosis(View view) {
        NetworkState.getNetwork().changeNetwork("GnosisChain");
        finish();
    }

    public void changeToArbitrum(View view) {
        NetworkState.getNetwork().changeNetwork("Arbitrum");
        finish();
    }

    public void changeToPolygon(View view) {
        NetworkState.getNetwork().changeNetwork("Polygon");
        finish();
    }

    public void changeToMantle(View view) {
        NetworkState.getNetwork().changeNetwork("Mantle");
        finish();
    }

    public void changeTozkSync(View view) {
        NetworkState.getNetwork().changeNetwork("zkSync");
        finish();
    }

    public void changeToStarknet(View view) {
        NetworkState.getNetwork().changeNetwork("Starknet");
        finish();
    }

    public void changeToScroll(View view) {
        NetworkState.getNetwork().changeNetwork("Scroll");
        finish();
    }

    public void changeToCelo(View view) {
        NetworkState.getNetwork().changeNetwork("Celo");
        finish();
    }

    public void changeToBase(View view) {
        NetworkState.getNetwork().changeNetwork("Base");
        finish();
    }
}
