package com.varejodigital.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

/**
 * Created by thiagocortat on 6/26/15.
 */
public class SampleBarCodeScannerFragment  extends BarCodeScannerFragment {

    public static final String BAR_CODE = "com.varejodigital.BARCODE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.setmCallBack(new IResultCallback() {
            @Override
            public void result(Result lastResult) {
//                Toast.makeText(getActivity(), "Scan: " + lastResult.toString(), Toast.LENGTH_SHORT).show();

                Intent it = new Intent();
                it.putExtra(BAR_CODE, lastResult.toString());
                getActivity().setResult(Activity.RESULT_OK, it);
                getActivity().finish();

            }
        });
        //cameraManager.setManualFramingRect(450, 349, 100, 50);
    }

    public static SampleBarCodeScannerFragment newInstance() {
        return (new SampleBarCodeScannerFragment());
    }
}
