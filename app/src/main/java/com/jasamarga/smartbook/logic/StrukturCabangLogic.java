package com.jasamarga.smartbook.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jasamarga.smartbook.callback.StrukturCabangCallback;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.service.NetworkConnection;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 10/11/16.
 */

public class StrukturCabangLogic {

    private static final String TAG = StrukturCabangLogic.class.getSimpleName();
    private Context context;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private StrukturCabangCallback callback;
    private PersonalInfo personalInfo;
    private List<PersonalInfo> bawahanList = new ArrayList<>();

    public StrukturCabangLogic(Context context, StrukturCabangCallback listener) {
        this.context = context;
        this.callback = listener;
    }

    public void setupStrukturCabangViews(String param) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainStrukturCabangItems(param);
        }

    }

    private void doNetworkService(String url, String params) {
        Intent networkIntent = new Intent(this.context, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("url", url);
        networkIntent.putExtra("params", params);
        this.context.startService(networkIntent);
    }

    private void obtainStrukturCabangItems(final String param) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseStrukturCabangItemsResponse(msg, param);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("nppreq", ""+ param);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getAtasanInfo(), jsonObject.toString());
    }

    private void obtainStrukturCabangBawahanItems(String param) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseStrukturCabangBawahanItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("nppreq", ""+ param);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getBawahanList(), jsonObject.toString());
    }

    private void parseStrukturCabangItemsResponse(Message message, String param) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] detail info " + this.stringResponse[0]);
        try {
            this.personalInfo = Utility.JSONUtility.getAtasanInfoFromJSON(this.stringResponse[0]);
            obtainStrukturCabangBawahanItems(param);
        } catch (JSONException e) {
            Log.d(TAG, "Exception personal info Response " + e.getMessage());
        }
    }

    private void parseStrukturCabangBawahanItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] bawahan info " + this.stringResponse[0]);
        try {
            this.bawahanList = Utility.JSONUtility.getBawahanListFromJSON(this.stringResponse[0]);
            this.callback.finishedSetupStruktrukCabangViews(this.personalInfo, this.bawahanList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception bawahan Response " + e.getMessage());
        }
    }

}
