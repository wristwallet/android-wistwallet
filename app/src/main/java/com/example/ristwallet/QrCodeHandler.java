package com.example.ristwallet;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;



public class QrCodeHandler {
    public static final int QR_HEIGHT = 400;
    public static final int QR_WIDTH = 400;

    public Bitmap generateQR(String publicId) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(publicId, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static String readQR() {
        return "";
    }
}
