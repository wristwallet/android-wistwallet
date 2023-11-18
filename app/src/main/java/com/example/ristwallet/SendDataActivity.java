package com.example.ristwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.web3j.ens.EnsResolver;
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

//    private static final String PRIVATE_KEY = "9a340ac657f253bbfe0bed9c482c5bcff60b3eb8b4272fa5acb255828daa7cd0";
    private static final String PRIVATE_KEY = "9a340ac657f253bbfe0bed9c482c5bcff60b3eb8b4272fa5acb255828daa7cd0";
    private static final String TAG = "SendDataActivity";

    private EditText recieveAddressEditText;
    private EditText amountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        recieveAddressEditText = findViewById(R.id.recieveAddressEditText);
        amountEditText = findViewById(R.id.amountText);
    }

    public void sendFunds(View view) {
        Web3j web3Mainnet = Web3j.build(new HttpService(WalletActivity.INFURA_MAINNET));
        Web3j web3j = Web3j.build(new HttpService(NetworkState.getNetwork().currentEndpoint));
        Credentials credentials = Credentials.create(PRIVATE_KEY);
        BigDecimal amountToSend = BigDecimal.valueOf(Double.parseDouble(amountEditText.getText().toString()));

        new SendFundsTask(recieveAddressEditText.getText().toString(), amountToSend, credentials, web3j, web3Mainnet).execute();
    }

    public class SendFundsTask extends AsyncTask<Void, Void, String> {
        private static final String TAG = "SendFundsTask";
        private final String recipientAddress;
        private final BigDecimal amount;
        private final Credentials credentials;
        private final Web3j web3j;
        private final Web3j web3Mainnet;

        public SendFundsTask(String recipientAddress, BigDecimal amount, Credentials credentials, Web3j web3j, Web3j web3Mainnet) {
            this.recipientAddress = recipientAddress;
            this.amount = amount;
            this.credentials = credentials;
            this.web3j = web3j;
            this.web3Mainnet = web3Mainnet;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String resolvedAddress = resolveEnsOrGetAddress(recipientAddress);
                Log.d(TAG, "address: "+ credentials.getAddress());
                EthGetBalance balanceWei = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
                BigInteger balanceInWei = balanceWei.getBalance();

                Log.d(TAG, "balance before send: "+ balanceInWei);

                EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                        credentials.getAddress(), DefaultBlockParameterName.LATEST).send();

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();
                TransactionReceipt transactionReceipt = Transfer.sendFunds(
                        web3j, credentials, resolvedAddress, amount, Convert.Unit.ETHER).send();

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
        private String resolveEnsOrGetAddress(String recipient) throws Exception {
            if (recipient.endsWith(".eth")) {
                EnsResolver ensResolver = new EnsResolver(web3Mainnet);
                String resolvedAddress = ensResolver.resolve(recipient);
                if (resolvedAddress == null) {
                    throw new Exception("ENS name not resolved");
                }
                return resolvedAddress;
            }
            return recipient;
        }
    }
}