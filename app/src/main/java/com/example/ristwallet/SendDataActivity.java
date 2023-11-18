package com.example.ristwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SendDataActivity extends AppCompatActivity {

    private static final String PRIVATE_KEY = "8fb7505ce1b2abf8433e37c06d8631709252bb8173cd7023518b6697a65cb4b2";
    private static final String TAG = "SendDataActivity";
    String recipientAddress = "0xcCb4DD8873b48aC533dA83371Dd9ED4fa064C12b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
    }

    public void sendFunds(View view) {
        Web3j web3j = Web3j.build(new HttpService(WalletActivity.INFURA));
        Credentials credentials = Credentials.create(PRIVATE_KEY);
        BigDecimal amountToSend = BigDecimal.valueOf(0.002);

        new SendFundsTask(recipientAddress, amountToSend, credentials, web3j).execute();
    }

    public class SendFundsTask extends AsyncTask<Void, Void, String> {
        private static final String TAG = "SendFundsTask";
        private final String recipientAddress;
        private final BigDecimal amount;
        private final Credentials credentials;
        private final Web3j web3j;

        public SendFundsTask(String recipientAddress, BigDecimal amount, Credentials credentials, Web3j web3j) {
            this.recipientAddress = recipientAddress;
            this.amount = amount;
            this.credentials = credentials;
            this.web3j = web3j;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                Log.d(TAG, "address: "+ credentials.getAddress());
                EthGetBalance balanceWei = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
                BigInteger balanceInWei = balanceWei.getBalance();

                Log.d(TAG, "balance before send: "+ balanceInWei);

                EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                        credentials.getAddress(), DefaultBlockParameterName.LATEST).send();

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();
                TransactionReceipt transactionReceipt = Transfer.sendFunds(
                        web3j, credentials, recipientAddress, amount, Convert.Unit.ETHER).send();

                return transactionReceipt.getTransactionHash();
            } catch (Exception e) {
                Log.e(TAG, "Error sending funds: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String transactionHash) {
            if (transactionHash != null) {
                Log.d(TAG, "sendFunds: " + transactionHash);
                // Update the UI with the transaction hash or notify the user
            }
        }
    }
}