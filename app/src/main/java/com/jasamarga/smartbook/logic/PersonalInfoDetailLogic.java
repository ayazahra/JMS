package com.jasamarga.smartbook.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jasamarga.smartbook.callback.PersonalInfoDetailCallback;
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
 * Created by apridosandyasa on 8/13/16.
 */
public class PersonalInfoDetailLogic {

    private final String TAG = PersonalInfoDetailLogic.class.getSimpleName();
    private Context context;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private PersonalInfo personalInfo;
    private List<PersonalInfo> peerList = new ArrayList<>();
    private List<PersonalInfo> bawahanList = new ArrayList<>();
    private PersonalInfoDetailCallback callback;
    private String params;

    public PersonalInfoDetailLogic(Context context, PersonalInfoDetailCallback listener) {
        this.context = context;
        this.callback = listener;
    }

    public void setupPersonalDetailInfoViews(String personNpp) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainPersonalDetailItems(personNpp);
    }

    public void setupAtasanViews(String personNpp) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainAtasanItems(personNpp);
    }

    public void setupPeerListViews(String personNpp) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainPeerListItems(personNpp);
    }

    public void setupBawahanListViews(String personNpp) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context))
            obtainBawahanListItems(personNpp);

    }

    private void doNetworkService(String url, String params) {
        Intent networkIntent = new Intent(this.context, NetworkConnection.class);
        this.messenger = new Messenger(this.handler);
        networkIntent.putExtra("messenger", this.messenger);
        networkIntent.putExtra("url", url);
        networkIntent.putExtra("params", params);
        this.context.startService(networkIntent);
    }

    private void obtainPersonalDetailItems(String personNpp) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parsePersonalDetailItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("nppreq", ""+ personNpp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getAtasanInfo(), jsonObject.toString());
    }

    private void obtainAtasanItems(String personNpp) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseAtasanItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("nppreq", ""+ personNpp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getAtasanInfo(), jsonObject.toString());
    }

    private void obtainPeerListItems(String personNpp) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parsePeerListItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("nppreq", ""+ personNpp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getPeerList(), jsonObject.toString());
    }

    private void obtainBawahanListItems(String personNpp) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseBawahanListItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("nppreq", ""+ personNpp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getBawahanList(), jsonObject.toString());
    }

    private void parsePersonalDetailItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] detail info " + this.stringResponse[0]);
        try {
            this.personalInfo = Utility.JSONUtility.getAtasanInfoFromJSON(this.stringResponse[0]);
            this.callback.finishedSetupPersonalDetailInfoViews(this.personalInfo);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }
    }

    private void parseAtasanItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] atasan info " + this.stringResponse[0]);
        try {
            this.personalInfo = Utility.JSONUtility.getAtasanInfoFromJSON(this.stringResponse[0]);
            this.callback.finishedSetupAtasanViews(this.personalInfo);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }
    }

    private void parsePeerListItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] peer info " + this.stringResponse[0]);
        try {
            this.peerList = Utility.JSONUtility.getPeerListFromJSON(this.stringResponse[0]);
            this.callback.finishedSetupPeerListViews(this.peerList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }
    }

    private void parseBawahanListItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] bawahan info " + this.stringResponse[0]);
        try {
            this.bawahanList = Utility.JSONUtility.getBawahanListFromJSON(this.stringResponse[0]);
            this.callback.finishedSetupBawahanListViews(this.bawahanList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }
    }

}
