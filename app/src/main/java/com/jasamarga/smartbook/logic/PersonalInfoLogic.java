package com.jasamarga.smartbook.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jasamarga.smartbook.callback.PersonalInfoCallback;
import com.jasamarga.smartbook.database.PisDao;
import com.jasamarga.smartbook.object.MainMenu;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.PersonalInfoSearch;
import com.jasamarga.smartbook.service.NetworkConnection;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/10/16.
 */
public class PersonalInfoLogic {

    private final String TAG = PersonalInfoLogic.class.getSimpleName();
    private Context context;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String[] stringResponse = {""};
    private List<PersonalInfo> personalInfoList = new ArrayList<>();
    private PersonalInfoCallback callback;

    public PersonalInfoLogic(Context context, PersonalInfoCallback listener) {
        this.context = context;
        this.callback = listener;
    }

    public void setupPersonalInfoViews(int limit, int offset, String word) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainPersonalInfoItems(limit, offset, word);
        }else{
            this.callback.failedSetupPersonalInfoViews();
        }
    }

    public void setupMorePersonalInfoViews(int limit, int offset, String word) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainMorePersonalInfoItems(limit, offset, word);
        }else{
            this.callback.failedSetupPersonalInfoViews();
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

//    private void obtainPersonalInfoItemsFromDB(String param) {
//        PisDao pisDao = new PisDao(this.context);
//        Log.d(TAG, "size record " + pisDao.getListPersonalInfoSearch(param).size());
//        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
//            obtainPersonalInfoItems(param);
//        } else {
//            this.personalInfoSearchList = pisDao.getListPersonalInfoSearch(param);
//            this.callback.finishedSetupPersonalInfoViews(personalInfoSearchList);
//        }
//    }

//    private void obtainMorePersonalInfoItemsFromDB(String param, List<PersonalInfoSearch> personalInfoSearchList) {
//        PisDao pisDao = new PisDao(this.context);
//        Log.d(TAG, "size record " + pisDao.getListPersonalInfoSearch(param).size());
//        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
//            obtainMorePersonalInfoItems(param, personalInfoSearchList);
//        } else {
//            List<PersonalInfoSearch> personalInfoSearchList1 = pisDao.getListPersonalInfoSearch(param);
//            for (PersonalInfoSearch personalInfoSearch : personalInfoSearchList1) {
//                personalInfoSearchList.add(personalInfoSearch);
//            }
//            this.callback.finishedMorePersonalInfoViews(personalInfoSearchList);
//        }
//    }

    private void obtainPersonalInfoItems(int limit, int offset, String word) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parsePersonalInfoResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("limit", ""+ limit);
            jsonObject.put("start", "" + offset);
            jsonObject.put("word", word);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getPersonalInfo(), jsonObject.toString());
    }

    private void obtainMorePersonalInfoItems(int limit, int offset, String word) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseMorePersonalInfoListItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("limit", ""+ limit);
            jsonObject.put("start", "" + offset);
            jsonObject.put("word", "" + word);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getPersonalInfo(), jsonObject.toString());
    }

    private void parsePersonalInfoResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            this.personalInfoList = Utility.JSONUtility.getPersonalInfoListFromJSON(this.context, this.stringResponse[0]);
            this.callback.finishedSetupPersonalInfoViews(this.personalInfoList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void parseMorePersonalInfoListItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] " + this.stringResponse[0]);
        try {
            this.personalInfoList = Utility.JSONUtility.getMorePersonalInfoListFromJSON(this.context, this.stringResponse[0]);
            this.callback.finishedMorePersonalInfoViews(this.personalInfoList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

}
