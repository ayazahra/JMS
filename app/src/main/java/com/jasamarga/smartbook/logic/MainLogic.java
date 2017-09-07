package com.jasamarga.smartbook.logic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import com.jasamarga.smartbook.callback.MainCallback;
import com.jasamarga.smartbook.object.BocData;
import com.jasamarga.smartbook.object.Cabang;
import com.jasamarga.smartbook.object.Highlight;
import com.jasamarga.smartbook.object.MainMenu;
import com.jasamarga.smartbook.object.Submenu;
import com.jasamarga.smartbook.service.NetworkConnection;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainLogic {

    private final String TAG = MainLogic.class.getSimpleName();
    private Context context;
    private Messenger messenger;
    private Message message;
    private Bundle bundle;
    private Handler handler;
    private String failureResponse = "";
    private String[] stringResponse = {""};
    private String kontent = "";
    private List<MainMenu> mainMenuList = new ArrayList<>();
    private List<Submenu> submenuList = new ArrayList<>();
    private List<Cabang> headerList = new ArrayList<>();
    private List<Cabang> cabangList = new ArrayList<>();
    private HashMap<String, Object> penghargaan = new HashMap<>();
    private List<BocData> bocDataList = new ArrayList<>();
    private List<Highlight> highlightList = new ArrayList<>();
    private MainCallback mainCallback;

    public MainLogic(Context context, MainCallback listener) {
        this.context = context;
        this.mainCallback = listener;
    }

    public void setupSlideViews() {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainSlideItems();
        }else {
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupMainViews() {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainNavigationItems();
        }else{
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupHeaderViews(int mainMenuId, boolean isHeaderLoaded) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainHeaderListItems(mainMenuId, isHeaderLoaded);
        }else{
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupSubmenuViews(int mainMenuId) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainSubMenuItems(mainMenuId);
        }else{
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupPusatContentViews(int mainMenuId, int subMenuId, int position) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainPusatContentItems(mainMenuId, subMenuId, position);
        }else{
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupCabangContentViews(int mainMenuId, int subMenuId, int kantorId, int position) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainCabangContentItems(mainMenuId, subMenuId, kantorId, position);
        }else{
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupPenghargaanViews(int mainMenuId, int subMenuId, int position) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainPenghargaanItems(mainMenuId, subMenuId, position);
        }else{
            this.mainCallback.failedSetupMainViews();
        }
    }

    public void setupBOCBOD(int mainMenuId, int subMenuId, int kantorId, int position) {
        if (Utility.ConnectionUtility.isNetworkConnected(this.context)) {
            obtainBOCBODListItems(mainMenuId, subMenuId, kantorId, position);
        }else{
            this.mainCallback.failedSetupMainViews();
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

    private void obtainSlideItems() {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseSlideItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getArticle(), jsonObject.toString());
    }

    private void obtainNavigationItems() {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseNavigationItemsResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getMainMenu(), jsonObject.toString());
    }

    private void obtainSubMenuItems(final int mainMenuId) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseSubMenuResponse(msg, mainMenuId);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+mainMenuId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getSubmenu(), jsonObject.toString());
    }

    private void obtainHeaderListItems(final int mainMenuId, final boolean isHeaderLoaded) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseHeaderListItemsResponse(msg, mainMenuId, isHeaderLoaded);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+mainMenuId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getListPerusahaan(), jsonObject.toString());
    }

    private void obtainListPerusahaan(int mainMenuId) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseListPerusahaanResponse(msg);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+mainMenuId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getListPerusahaan(), jsonObject.toString());
    }

    private void obtainPusatContentItems(final int mainMenuId, final int subMenuId, final int position) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parsePusatContentItemsResponse(msg, mainMenuId, subMenuId, position);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+ mainMenuId);
            jsonObject.put("menu_id", "" + subMenuId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getPusatContent(), jsonObject.toString());
    }

    private void obtainCabangContentItems(final int mainMenuId, final int subMenuId, final int kantorId, final int position) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseCabangContentItemsResponse(msg, mainMenuId, subMenuId, kantorId, position);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+ mainMenuId);
            jsonObject.put("menu_id", "" + subMenuId);
            jsonObject.put("kantor_id", "" + kantorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getCabangContent(), jsonObject.toString());
    }

    private void obtainPenghargaanItems(final int mainMenuId, final int subMenuId, final int position) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parsePenghargaanItemsResponse(msg, mainMenuId, subMenuId, position);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+ mainMenuId);
            jsonObject.put("menu_id", "" + subMenuId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getPenghargaan(), jsonObject.toString());
    }

    private void obtainBOCBODListItems(final int mainMenuId, final int subMenuId, final int kantorId, final int position) {
        this.handler = new Handler(this.context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                parseBOCBODListItemsResponse(msg, mainMenuId, subMenuId, kantorId, position);
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("npp", SharedPreferencesProvider.getInstance().getNpp(this.context));
            jsonObject.put("api_token", SharedPreferencesProvider.getInstance().getApiKey(this.context));
            jsonObject.put("mainmenu_id", ""+ mainMenuId);
            jsonObject.put("menu_id", "" + subMenuId);
            jsonObject.put("kantor_id", "" + kantorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        doNetworkService(ConstantAPI.getCabangContent(), jsonObject.toString());
    }

    private void parseHeaderListItemsResponse(Message message, int mainMenuId, boolean isHeaderLoaded) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] detail info " + this.stringResponse[0]);
        try {
            this.headerList.clear();
            this.headerList = Utility.JSONUtility.getListPerusahaanFromJSON(this.stringResponse[0]);
            this.mainCallback.finishedSetupHeaderListViews(this.headerList, mainMenuId);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }
    }

    private void parseListPerusahaanResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] detail info " + this.stringResponse[0]);
        try {
            this.cabangList.clear();
            this.headerList.clear();
            this.cabangList = Utility.JSONUtility.getListPerusahaanFromJSON(this.stringResponse[0]);
            this.headerList = Utility.JSONUtility.getListPerusahaanFromJSON(this.stringResponse[0]);
            this.mainCallback.finishedSetupMainViews(this.mainMenuList, this.cabangList, this.headerList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception detail Response " + e.getMessage());
        }
    }

    private void parseNavigationItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.failureResponse = this.bundle.getString("network_failure");
        if (this.failureResponse.equals("yes")) {
            this.mainCallback.failedSetupMainViews();
        }else{
            this.stringResponse[0] = this.bundle.getString("network_response");
            Log.d(TAG, "responseString[0] main " + this.stringResponse[0]);
            try {
                this.mainMenuList = Utility.JSONUtility.getListMainMenuFromJSON(this.context, this.stringResponse[0]);
                obtainListPerusahaan(2);
            } catch (JSONException e) {
                Log.d(TAG, "Exception " + e.getMessage());
            }
        }
    }

    private void parseSubMenuResponse(Message message, int mainMenuId) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] pusat " + this.stringResponse[0]);
        try {
            this.submenuList = Utility.JSONUtility.getListSubmenuFromJSON(this.stringResponse[0]);
            this.mainCallback.finishedSetupSubmenuViews(this.submenuList, mainMenuId);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void parsePusatContentItemsResponse(Message message, int mainMenuId, int subMenuId, int position) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] pusat " + this.stringResponse[0]);
        try {
            this.kontent = Utility.JSONUtility.getKontentFromJSON(this.stringResponse[0]);
            this.mainCallback.finishedSetupPusatContentViews(this.kontent, mainMenuId, subMenuId, position);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void parseCabangContentItemsResponse(Message message, int mainMenuId, int subMenuId, int kantorId, int position) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] pusat " + this.stringResponse[0]);
        try {
            this.kontent = Utility.JSONUtility.getKontentFromJSON(this.stringResponse[0]);
            this.mainCallback.finishedSetupCabangContentViews(this.kontent, mainMenuId, subMenuId, kantorId, position);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void parsePenghargaanItemsResponse(Message message, int mainMenuId, int subMenuId, int position) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] pusat " + this.stringResponse[0]);
        try {
            this.penghargaan = Utility.JSONUtility.getPenghargaanContentDataFromJSON(this.stringResponse[0]);
            this.mainCallback.finishedSetupPenghargaanViews(this.penghargaan, mainMenuId, subMenuId, position);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void parseBOCBODListItemsResponse(Message message, int mainMenuId, int subMenuId, int kantorId, int position) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] pusat " + this.stringResponse[0]);
        try {
            this.bocDataList = Utility.JSONUtility.getBOCBODList(this.stringResponse[0]);
            this.mainCallback.finishedSetupBOCBOD(this.bocDataList, mainMenuId, subMenuId, kantorId, position);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

    private void parseSlideItemsResponse(Message message) {
        this.message = message;
        this.bundle = this.message.getData();
        this.stringResponse[0] = this.bundle.getString("network_response");
        Log.d(TAG, "responseString[0] article " + this.stringResponse[0]);
        try {
            this.highlightList = Utility.JSONUtility.getListHighlightFromJSON(this.context, this.stringResponse[0]);
            this.mainCallback.finishedSetupSlideViews(this.highlightList);
        } catch (JSONException e) {
            Log.d(TAG, "Exception " + e.getMessage());
        }
    }

}
