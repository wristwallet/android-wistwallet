package com.example.ristwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

public class SendingStatusActivity extends AppCompatActivity {

    ImageView statusImage;
    TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_status);
        statusText = findViewById(R.id.statusText);
        statusImage = findViewById(R.id.statusImage);

        String transactionHash = getIntent().getStringExtra("transactionHash");
        new TransactionStatusChecker().execute(transactionHash);
    }

    public class TransactionStatusChecker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... transactionHashes) {
            String transactionHash = transactionHashes[0];
            try {
                Web3j web3j = Web3j.build(new HttpService(NetworkState.getNetwork().currentEndpoint));

                EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    while (!transactionReceipt.getTransactionReceipt().isPresent()) {
                        Thread.sleep(10000); // Poll every 10 seconds
                        transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
                    }
                }

                TransactionReceipt receipt = transactionReceipt.getTransactionReceipt().get();
                return "0x1".equals(receipt.getStatus()) ? "Success" : "Failed";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String status) {
            switch (status) {
                case "Success":
                    statusImage.setImageResource(R.drawable.successful);
                    statusText.setText("Transaction\nSuccessful");
                    break;
                case "Failed":
                    statusImage.setImageResource(R.drawable.pending);
                    statusText.setText("Transaction\nFailed");
                    break;
                case "Error":
                    statusImage.setImageResource(R.drawable.pending);
                    statusText.setText("Transaction\nError");
                    break;
            }
        }
    }
}