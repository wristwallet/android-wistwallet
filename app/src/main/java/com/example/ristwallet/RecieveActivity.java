package com.example.ristwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.util.List;

public class RecieveActivity extends AppCompatActivity {

    private static final String TAG = "RecieveActivity";
    private static final String publidId = "0xcCb4DD8873b48aC51Dd9ED4fa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
        QrCodeHandler qr_generator = new QrCodeHandler();
        Bitmap bitmap = qr_generator.generateQR(publidId);
        ImageView imageView = findViewById(R.id.qr_image);
        imageView.setImageBitmap(bitmap);
        Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/b30f49759769470c8acac821910d824f"));
    }


}