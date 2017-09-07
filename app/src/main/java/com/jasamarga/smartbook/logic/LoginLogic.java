package com.jasamarga.smartbook.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jasamarga.smartbook.callback.LoginCallback;
import com.jasamarga.smartbook.service.NetworkConnection;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apridosandyasa on 9/28/16.
 */

public class LoginLogic {

    private final String TAG = LoginLogic.class.getSimpleName();
    private Context context;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private String apiKey = "";
    private String msgResponse = "";
    private LoginCallback callback;

    public LoginLogic(Context context, LoginCallback listener) {
        this.context = context;
        this.callback = listener;
    }

    public void setupLoginProcess(String username, String password) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainLoginResponse(username, password);
    }

    private void doNetworkService(String url, String username, String password) {
        String params = "param1=" + username + "&param2=" + password;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent networkIntent = new Intent(this.context, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("url", url);
        networkIntent.putExtra("params", jsonObject.toString());
        this.context.startService(networkIntent);
    }

    private void obtainLoginResponse(String username, String password) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseLoginResponse(msg);
            }

        };
        doNetworkService(ConstantAPI.getLogin(), username, password);
    }

    private void parseLoginResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] detail info " + this.stringResponse[0]);
        try {
            this.msgResponse = Utility.JSONUtility.getMessageLoginResponseFromJSON(context, this.stringResponse[0]);
            if (this.msgResponse.equals("success !"))
                this.apiKey = Utility.JSONUtility.getApiKeyFromJSON(this.stringResponse[0]);
            else
                this.apiKey = "";
            this.callback.finishedLoginProcess(msgResponse, apiKey);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }

    }

}
