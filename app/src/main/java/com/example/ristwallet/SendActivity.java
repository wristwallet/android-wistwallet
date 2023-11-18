package com.example.ristwallet;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class SendActivity extends AppCompatActivity {

    private static final String TAG = "SendActivity";

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        editText = findViewById(R.id.editTextNumberDecimal);

        // get focus on the editText
        editText.requestFocus();
        //show the keyboard
        editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));

        editText.setOnEditorActionListener((v, actionId, event) -> {
            Log.d(TAG, "event: "+ event);
            // Code to execute when "Send" is pressed
            Toast.makeText(SendActivity.this, "Sending "+ editText.getText() + " ETH" , Toast.LENGTH_SHORT).show();

            barcodeLauncher.launch(new ScanOptions());
            return false;
        });
    }

    // Register the launcher and result handler
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(SendActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });
}