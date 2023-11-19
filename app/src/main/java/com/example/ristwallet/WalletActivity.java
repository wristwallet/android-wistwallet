package com.example.ristwallet;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class WalletActivity extends AppCompatActivity {

    public static final String INFURA_MAINNET = "https://mainnet.infura.io/v3/b30f49759769470c8acac821910d824f";
    private static final String TAG = "WalletActivity";
//    static public String ADDRESS = "0xcCb4DD8873b48aC533dA83371Dd9ED4fa064C12b";
    public String address = "0x7B595431639BdBCd3dD6F96AFE7BA32cfC800013";
    public String endpoint = NetworkState.getNetwork().currentEndpoint;

    Web3j web3;
    TextView balanceText;
    TextView currency;
    TextView networkText;
    ImageView networkLogoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        balanceText = findViewById(R.id.balanceText);
        currency = findViewById(R.id.currencyText);
        networkText = findViewById(R.id.networkText);
        networkLogoImage = findViewById(R.id.networkLogoImage);
    }

    @Override
    protected void onResume() {
        networkText.setText(NetworkState.getNetwork().currentNetwork);
        networkLogoImage.setImageResource(NetworkState.getNetwork().currentLogo);

        web3 = Web3j.build(new HttpService(NetworkState.getNetwork().currentEndpoint));
        super.onResume();
        balanceText.setText("...");
        new Thread(() -> {
            BigInteger balance = getBalance(address);
            // Convert balance from Wei to Ether and update UI accordingly
            // Make sure to run UI updates on the main thread

            float formatedBalance;
            if(NetworkState.getNetwork().currentNetwork.equals("Ethereum")) {
                formatedBalance = balance.floatValue() / 10000000000f;
            } else {
                formatedBalance = balance.floatValue() / 1000000000000000000f;
            }
            // format to just 4 digits after the decimal point
            formatedBalance = (float) (Math.round(formatedBalance * 10000.0) / 10000.0);

            float finalFormatedBalance = formatedBalance;
            WalletActivity.this.runOnUiThread(() -> {
                balanceText.setText(Float.toString(finalFormatedBalance));
                currency.setText(NetworkState.getNetwork().currentCurrency);
            });
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

    public void goToRecieveActivity(View view) {
        Intent intent = new Intent(this, RecieveActivity.class);
        startActivity(intent);
    }

    public void goToSendActivity(View view) {
        Intent intent = new Intent(this, SendDataActivity.class);
        startActivity(intent);
    }

    public void goToNetworkActivity(View view) {
        Intent intent = new Intent(this, NetworkActivity.class);
        startActivity(intent);
    }
}