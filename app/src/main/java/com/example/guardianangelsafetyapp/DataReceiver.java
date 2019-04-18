package com.example.guardianangelsafetyapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.RequiresApi;

import org.json.JSONException;

public class DataReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public DataReceiver(Handler handler) {
        super(handler);
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException;
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mReceiver != null) {
            try {
                mReceiver.onReceiveResult(resultCode, resultData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
