package com.example.ristwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class WalletActivity extends AppCompatActivity {

    Web3j web3;
    TextView balanceText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        balanceText = findViewById(R.id.balanceText);

        web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/b30f49759769470c8acac821910d824f"));

        balanceText.setText("loading...");
        new Thread(() -> {
            BigInteger balance = getBalance("0xcCb4DD8873b48aC533dA83371Dd9ED4fa064C12b");
            // Convert balance from Wei to Ether and update UI accordingly
            // Make sure to run UI updates on the main thread
            balanceText.setText(balance.toString());
        }).start();

    }

    private BigInteger getBalance(String address) {
        try {
            EthGetBalance ethGetBalance = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                ethGetBalance = web3
                        .ethGetBalance(address, DefaultBlockParameterName.LATEST)
                        .sendAsync()
                        .get();
            }

            return ethGetBalance.getBalance();
        } catch (Exception e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }
}