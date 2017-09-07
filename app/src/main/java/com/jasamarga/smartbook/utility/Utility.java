package com.jasamarga.smartbook.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.jasamarga.smartbook.database.MainMenuDao;
import com.jasamarga.smartbook.database.PisDao;
import com.jasamarga.smartbook.object.BocData;
import com.jasamarga.smartbook.object.Cabang;
import com.jasamarga.smartbook.object.Highlight;
import com.jasamarga.smartbook.object.MainMenu;
import com.jasamarga.smartbook.object.Penghargaan;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.Submenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andy on 5/19/2016.
 */
public class Utility {

    public static class ConnectionUtility {

        public static boolean isNetworkConnected(Context context) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }

    }

    public static class DateUtility {

        public static String parseFormatDate(String date) {
            String[] dateTimeString = date.split(" ");
            String[] dates = dateTimeString[0].split("-");
            String month = (dates[0].equals("01")) ? "Januari" : (dates[0].equals("02")) ? "Februari" :
            (dates[0].equals("03")) ? "Maret" : (dates[0].equals("04")) ? "April" : (dates[0].equals("05")) ?
            "Mei" : (dates[0].equals("06")) ? "Juni" : (dates[0].equals("07")) ? "Juli" : (dates[0].equals("08")) ?
            "Agustus" : (dates[0].equals("09")) ? "September" : (dates[0].equals("10")) ? "Oktober" : (dates[0].equals("11")) ?
            "November" : "Desember";

            return dates[1] + " " + month + " " + dates[2];
        }

    }

    public static class ImageUtility {

        public static Bitmap convertByteOfArrayToBitmap(byte[] responseByte) {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inScaled = true;
            if (responseByte.length < (512 * 1024))
                options.inSampleSize = 1;
            else if (responseByte.length < (1024 * 1024))
                options.inSampleSize = 2;
            else if (responseByte.length < (2048 * 1024))
                options.inSampleSize = 4;
            else if (responseByte.length < (4096 * 1024))
                options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeByteArray(responseByte,0, responseByte.length,options);

            return bitmap;
        }

    }

    public static class JSONUtility {

        private final static String TAG = JSONUtility.class.getSimpleName();

        public static String getMessageLoginResponseFromJSON(Context context, String json) throws JSONException {
            String msg = "";

            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.getInt("code") == 200) {
                msg = jsonObject.getString("message");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                SharedPreferencesProvider.getInstance().setApiKey(context, jsonArray.getJSONObject(0).getString("api_token"));
                SharedPreferencesProvider.getInstance().setNpp(context, jsonArray.getJSONObject(0).getString("npp"));
            }else{
                msg = jsonObject.getString("message");
            }

            return msg;
        }

        public static String getApiKeyFromJSON(String json) throws JSONException {
            String apiKey = "";

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            apiKey = jsonArray.getJSONObject(0).getString("api_token");

            return apiKey;
        }

        public static List<BocData> getBOCBODList(String json) throws JSONException {
            List<BocData> bocDataList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    bocDataList.add(new BocData(jsonArray.getJSONObject(i).getInt("id"),
                            jsonArray.getJSONObject(i).getInt("mainmenu_id"),
                            jsonArray.getJSONObject(i).getInt("menu_id"),
                            jsonArray.getJSONObject(i).getInt("kantor_id"),
                            jsonArray.getJSONObject(i).getString("Nama"),
                            jsonArray.getJSONObject(i).getString("Jabatan"),
                            jsonArray.getJSONObject(i).getString("photo")));
                }
            }

            return bocDataList;
        }

        public static List<PersonalInfo> getMorePersonalInfoListFromJSON(Context context, String json) throws JSONException {
            List<PersonalInfo> personalInfoList = new ArrayList<>();
            PisDao pisDao = new PisDao(context, true);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i ++) {
                    PersonalInfo personalInfo = new PersonalInfo();
                    personalInfo.setAgama(jsonArray.getJSONObject(i).getString("AGAMA"));
                    personalInfo.setAlamat(jsonArray.getJSONObject(i).getString("ALAMAT"));
                    personalInfo.setEmail(jsonArray.getJSONObject(i).getString("EMAIL_ADDRESS"));
                    personalInfo.setGolongan(jsonArray.getJSONObject(i).getString("GRADE"));
                    personalInfo.setJabatan(jsonArray.getJSONObject(i).getString("POSITION_NAME"));
                    personalInfo.setJenis_kelamin(jsonArray.getJSONObject(i).getString("SEX"));
                    personalInfo.setKantor_alamat(jsonArray.getJSONObject(i).getString("LOCATION_ADDRESS"));
                    personalInfo.setKantor_desc(jsonArray.getJSONObject(i).getString("LOCATION_NAME"));
                    personalInfo.setNpp(jsonArray.getJSONObject(i).getString("ASSIGNMENT_NUMBER"));
                    personalInfo.setNpp_atasan(jsonArray.getJSONObject(i).getString("PARENT_ASSIGNMENT_NUMBER"));
                    personalInfo.setNama(jsonArray.getJSONObject(i).getString("LAST_NAME"));
                    personalInfo.setNama_atasan(jsonArray.getJSONObject(i).getString("PARENT_LAST_NAME"));
                    personalInfo.setStatus_desc(jsonArray.getJSONObject(i).getString("EMPLOYMENT_CATEGORY"));
                    personalInfo.setTanggallahir(jsonArray.getJSONObject(i).getString("DATE_OF_BIRTH"));
                    personalInfo.setTempatlahir(jsonArray.getJSONObject(i).getString("TOWN_OF_BIRTH"));
                    personalInfo.setTelp1(jsonArray.getJSONObject(i).getString("TEL_NUMBER_1"));
                    personalInfo.setTelp2(jsonArray.getJSONObject(i).getString("TEL_NUMBER_2"));
                    personalInfo.setUnit_desc(jsonArray.getJSONObject(i).getString("UNIT_KERJA"));
                    personalInfo.setUrlfoto(jsonArray.getJSONObject(i).getString("urlfoto"));
                    personalInfoList.add(personalInfo);
                }
            }

            return personalInfoList;
        }

        public static List<PersonalInfo> getPersonalInfoListFromJSON(Context context, String json) throws JSONException {
            List<PersonalInfo> personalInfoList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i ++) {
                    PersonalInfo personalInfo = new PersonalInfo();
                    personalInfo.setAgama(jsonArray.getJSONObject(i).getString("AGAMA"));
                    personalInfo.setAlamat(jsonArray.getJSONObject(i).getString("ALAMAT"));
                    personalInfo.setEmail(jsonArray.getJSONObject(i).getString("EMAIL_ADDRESS"));
                    personalInfo.setGolongan(jsonArray.getJSONObject(i).getString("GRADE"));
                    personalInfo.setJabatan(jsonArray.getJSONObject(i).getString("POSITION_NAME"));
                    personalInfo.setJenis_kelamin(jsonArray.getJSONObject(i).getString("SEX"));
                    personalInfo.setKantor_alamat(jsonArray.getJSONObject(i).getString("LOCATION_ADDRESS"));
                    personalInfo.setKantor_desc(jsonArray.getJSONObject(i).getString("LOCATION_NAME"));
                    personalInfo.setNpp(jsonArray.getJSONObject(i).getString("ASSIGNMENT_NUMBER"));
                    personalInfo.setNpp_atasan(jsonArray.getJSONObject(i).getString("PARENT_ASSIGNMENT_NUMBER"));
                    personalInfo.setNama(jsonArray.getJSONObject(i).getString("LAST_NAME"));
                    personalInfo.setNama_atasan(jsonArray.getJSONObject(i).getString("PARENT_LAST_NAME"));
                    personalInfo.setStatus_desc(jsonArray.getJSONObject(i).getString("EMPLOYMENT_CATEGORY"));
                    personalInfo.setTanggallahir(jsonArray.getJSONObject(i).getString("DATE_OF_BIRTH"));
                    personalInfo.setTempatlahir(jsonArray.getJSONObject(i).getString("TOWN_OF_BIRTH"));
                    personalInfo.setTelp1(jsonArray.getJSONObject(i).getString("TEL_NUMBER_1"));
                    personalInfo.setTelp2(jsonArray.getJSONObject(i).getString("TEL_NUMBER_2"));
                    personalInfo.setUnit_desc(jsonArray.getJSONObject(i).getString("UNIT_KERJA"));
                    personalInfo.setUrlfoto(jsonArray.getJSONObject(i).getString("urlfoto"));
                    personalInfoList.add(personalInfo);
                }
            }

            return personalInfoList;
        }

        public static PersonalInfo getAtasanInfoFromJSON(String json) throws JSONException {
            PersonalInfo personalInfo = new PersonalInfo();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    personalInfo.setAgama(jsonArray.getJSONObject(i).getString("AGAMA"));
                    personalInfo.setAlamat(jsonArray.getJSONObject(i).getString("ALAMAT"));
                    personalInfo.setEmail(jsonArray.getJSONObject(i).getString("EMAIL_ADDRESS"));
                    personalInfo.setGolongan(jsonArray.getJSONObject(i).getString("GRADE"));
                    personalInfo.setJabatan(jsonArray.getJSONObject(i).getString("POSITION_NAME"));
                    personalInfo.setJenis_kelamin(jsonArray.getJSONObject(i).getString("SEX"));
                    personalInfo.setKantor_alamat(jsonArray.getJSONObject(i).getString("LOCATION_ADDRESS"));
                    personalInfo.setKantor_desc(jsonArray.getJSONObject(i).getString("LOCATION_NAME"));
                    personalInfo.setNpp(jsonArray.getJSONObject(i).getString("ASSIGNMENT_NUMBER"));
                    personalInfo.setNpp_atasan(jsonArray.getJSONObject(i).getString("PARENT_ASSIGNMENT_NUMBER"));
                    personalInfo.setNama(jsonArray.getJSONObject(i).getString("LAST_NAME"));
                    personalInfo.setNama_atasan(jsonArray.getJSONObject(i).getString("PARENT_LAST_NAME"));
                    personalInfo.setStatus_desc(jsonArray.getJSONObject(i).getString("EMPLOYMENT_CATEGORY"));
                    personalInfo.setTanggallahir(jsonArray.getJSONObject(i).getString("DATE_OF_BIRTH"));
                    personalInfo.setTempatlahir(jsonArray.getJSONObject(i).getString("TOWN_OF_BIRTH"));
                    personalInfo.setTelp1(jsonArray.getJSONObject(i).getString("TEL_NUMBER_1"));
                    personalInfo.setTelp2(jsonArray.getJSONObject(i).getString("TEL_NUMBER_2"));
                    personalInfo.setUnit_desc(jsonArray.getJSONObject(i).getString("UNIT_KERJA"));
                    personalInfo.setUrlfoto(jsonArray.getJSONObject(i).getString("urlfoto"));

                }
            }

            return personalInfo;
        }

        public static List<PersonalInfo> getPeerListFromJSON(String json) throws JSONException {
            List<PersonalInfo> personalInfoList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    PersonalInfo personalInfo = new PersonalInfo();
                    personalInfo.setAgama(jsonArray.getJSONObject(i).getString("AGAMA"));
                    personalInfo.setAlamat(jsonArray.getJSONObject(i).getString("ALAMAT"));
                    personalInfo.setEmail(jsonArray.getJSONObject(i).getString("EMAIL_ADDRESS"));
                    personalInfo.setGolongan(jsonArray.getJSONObject(i).getString("GRADE"));
                    personalInfo.setJabatan(jsonArray.getJSONObject(i).getString("POSITION_NAME"));
                    personalInfo.setJenis_kelamin(jsonArray.getJSONObject(i).getString("SEX"));
                    personalInfo.setKantor_alamat(jsonArray.getJSONObject(i).getString("LOCATION_ADDRESS"));
                    personalInfo.setKantor_desc(jsonArray.getJSONObject(i).getString("LOCATION_NAME"));
                    personalInfo.setNpp(jsonArray.getJSONObject(i).getString("ASSIGNMENT_NUMBER"));
                    personalInfo.setNpp_atasan(jsonArray.getJSONObject(i).getString("PARENT_ASSIGNMENT_NUMBER"));
                    personalInfo.setNama(jsonArray.getJSONObject(i).getString("LAST_NAME"));
                    personalInfo.setNama_atasan(jsonArray.getJSONObject(i).getString("PARENT_LAST_NAME"));
                    personalInfo.setStatus_desc(jsonArray.getJSONObject(i).getString("EMPLOYMENT_CATEGORY"));
                    personalInfo.setTanggallahir(jsonArray.getJSONObject(i).getString("DATE_OF_BIRTH"));
                    personalInfo.setTempatlahir(jsonArray.getJSONObject(i).getString("TOWN_OF_BIRTH"));
                    personalInfo.setTelp1(jsonArray.getJSONObject(i).getString("TEL_NUMBER_1"));
                    personalInfo.setTelp2(jsonArray.getJSONObject(i).getString("TEL_NUMBER_2"));
                    personalInfo.setUnit_desc(jsonArray.getJSONObject(i).getString("UNIT_KERJA"));
                    personalInfo.setUrlfoto(jsonArray.getJSONObject(i).getString("urlfoto"));
                    personalInfoList.add(personalInfo);
                }
            }

            return personalInfoList;
        }

        public static List<PersonalInfo> getBawahanListFromJSON(String json) throws JSONException {
            List<PersonalInfo> personalInfoList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    PersonalInfo personalInfo = new PersonalInfo();
                    personalInfo.setAgama(jsonArray.getJSONObject(i).getString("AGAMA"));
                    personalInfo.setAlamat(jsonArray.getJSONObject(i).getString("ALAMAT"));
                    personalInfo.setEmail(jsonArray.getJSONObject(i).getString("EMAIL_ADDRESS"));
                    personalInfo.setGolongan(jsonArray.getJSONObject(i).getString("GRADE"));
                    personalInfo.setJabatan(jsonArray.getJSONObject(i).getString("POSITION_NAME"));
                    personalInfo.setJenis_kelamin(jsonArray.getJSONObject(i).getString("SEX"));
                    personalInfo.setKantor_alamat(jsonArray.getJSONObject(i).getString("LOCATION_ADDRESS"));
                    personalInfo.setKantor_desc(jsonArray.getJSONObject(i).getString("LOCATION_NAME"));
                    personalInfo.setNpp(jsonArray.getJSONObject(i).getString("ASSIGNMENT_NUMBER"));
                    personalInfo.setNpp_atasan(jsonArray.getJSONObject(i).getString("PARENT_ASSIGNMENT_NUMBER"));
                    personalInfo.setNama(jsonArray.getJSONObject(i).getString("LAST_NAME"));
                    personalInfo.setNama_atasan(jsonArray.getJSONObject(i).getString("PARENT_LAST_NAME"));
                    personalInfo.setStatus_desc(jsonArray.getJSONObject(i).getString("EMPLOYMENT_CATEGORY"));
                    personalInfo.setTanggallahir(jsonArray.getJSONObject(i).getString("DATE_OF_BIRTH"));
                    personalInfo.setTempatlahir(jsonArray.getJSONObject(i).getString("TOWN_OF_BIRTH"));
                    personalInfo.setTelp1(jsonArray.getJSONObject(i).getString("TEL_NUMBER_1"));
                    personalInfo.setTelp2(jsonArray.getJSONObject(i).getString("TEL_NUMBER_2"));
                    personalInfo.setUnit_desc(jsonArray.getJSONObject(i).getString("UNIT_KERJA"));
                    personalInfo.setUrlfoto(jsonArray.getJSONObject(i).getString("urlfoto"));
                    personalInfoList.add(personalInfo);
                }
            }

            return personalInfoList;
        }

        public static List<MainMenu> getListMainMenuFromJSON(Context context, String json) throws JSONException {
            MainMenuDao mainMenuDao = new MainMenuDao(context, true);
            List<MainMenu> mainMenuList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i=0; i < jsonArray.length(); i++) {
                    mainMenuList.add(new MainMenu(jsonArray.getJSONObject(i).getInt("id"),
                            jsonArray.getJSONObject(i).getString("nama"),
                            jsonArray.getJSONObject(i).getString("icon")));
                    mainMenuDao.insertTable(new MainMenu(jsonArray.getJSONObject(i).getInt("id"),
                            jsonArray.getJSONObject(i).getString("nama"),
                            jsonArray.getJSONObject(i).getString("icon")));
                }
            }

            return mainMenuList;
        }

        public static List<Submenu> getListSubmenuFromJSON(String json) throws JSONException {
            List<Submenu> submenuList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    submenuList.add(new Submenu(jsonArray.getJSONObject(i).getInt("id"),
                            jsonArray.getJSONObject(i).getInt("mainmenu_id"),
                            jsonArray.getJSONObject(i).getString("nama"),
                            jsonArray.getJSONObject(i).getString("icon")));
                }
            }

            return submenuList;
        }

        public static List<Cabang> getListPerusahaanFromJSON(String json) throws JSONException {
            List<Cabang> cabangList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    cabangList.add(new Cabang(jsonArray.getJSONObject(i).getInt("id"),
                            jsonArray.getJSONObject(i).getInt("mainmenu_id"),
                            jsonArray.getJSONObject(i).getInt("kantor_id"),
                            jsonArray.getJSONObject(i).getString("kantor_desc"),
                            jsonArray.getJSONObject(i).getString("kantor_alamat"),
                            jsonArray.getJSONObject(i).getString("kantor_icon")));
                }
            }

            return cabangList;
        }

        public static String getKontentFromJSON(String json) throws JSONException {
            String kontent = "";

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                if (jsonArray.getJSONObject(0).has("introtext"))
                    kontent = jsonArray.getJSONObject(0).getString("introtext");
                else if (jsonArray.getJSONObject(0).has("konten"))
                    kontent = jsonArray.getJSONObject(0).getString("konten");
                else if (jsonArray.getJSONObject(0).has("struktur")) {
                    kontent = jsonArray.getJSONObject(0).getString("struktur");
                }else if (jsonArray.getJSONObject(0).has("kontent")) {
                    kontent = jsonArray.getJSONObject(0).getString("kontent");
                }else if (jsonArray.getJSONObject(0).has("npp")) {
                    kontent = jsonArray.getJSONObject(0).getString("npp");
                }
            }

            return kontent;
        }

        public static List<Highlight> getListHighlightFromJSON(Context context, String json) throws JSONException {
            //ArtikelDao artikelDao = new ArtikelDao(context, true);
            List<Highlight> highlightList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    highlightList.add(new Highlight(i,
                            jsonArray.getJSONObject(i).getString("Nama"),
                            jsonArray.getJSONObject(i).getInt("cat_id"),
                            jsonArray.getJSONObject(i).getString("fulltext"),
                            (!jsonArray.getJSONObject(i).getString("introtext").equals(null)) ? jsonArray.getJSONObject(i).getString("introtext") : "",
                            jsonArray.getJSONObject(i).getString("date")));
//                    artikelDao.insertTable(new Highlight(jsonArray.getJSONObject(i).getString("art_highlight_id"),
//                            jsonArray.getJSONObject(i).getString("art_highlight_head"),
//                            jsonArray.getJSONObject(i).getString("art_highlight_title"),
//                            jsonArray.getJSONObject(i).getString("art_highlight_content"),
//                            jsonArray.getJSONObject(i).getString("art_highlight_ringkas"),
//                            jsonArray.getJSONObject(i).getString("art_highlight_foot")));
                }
            }

            return highlightList;
        }

        public static HashMap<String, Object> getPenghargaanContentDataFromJSON(String json) throws JSONException {
            HashMap<String, Object> mainContentData = new HashMap<>();

            Log.d(TAG, json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            mainContentData.put("data", jsonArray.toString());

            return mainContentData;
        }

    }

    public static class HashMapUtility {

        public static List<String> getStrukturYearListFromHashMap(HashMap<String, Object> object) throws JSONException {
            List<String> strukturYearList = new ArrayList<>();
            String keyYear = "";
            JSONArray jsonArray = new JSONArray(object.get("data").toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                if (!keyYear.equals(jsonArray.getJSONObject(i).getString("penghargaan_year"))) {
                    keyYear = String.valueOf(jsonArray.getJSONObject(i).getString("penghargaan_year"));
                    strukturYearList.add(keyYear);
                }
            }

            return strukturYearList;
        }

        public static List<Penghargaan> getStrukturListFromHashMap(HashMap<String, Object> object, String keyYear) throws JSONException {
            List<Penghargaan> penghargaanList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(object.get("data").toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("penghargaan_year").equals(keyYear)) {
                    penghargaanList.add(new Penghargaan(jsonArray.getJSONObject(i).getString("penghargaan_desc"),
                            jsonArray.getJSONObject(i).getString("penghargaan_detail")));
                }
            }

            return penghargaanList;
        }

    }

    public static class DisplayUtility {

        public static DisplayMetrics getDisplayMetrics(Context context) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics;
        }

        public static int getScreenWidth(Context context) {
            int width = (int) getDisplayMetrics(context).widthPixels;
            return width;
        }

        public static int getScreenHeight(Context context) {
            int height = (int) getDisplayMetrics(context).heightPixels;
            return height;
        }

        public static int getGroupIndicatorMargin(Context context) {
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, DisplayUtility.getDisplayMetrics(context));
            return margin;
        }

    }

    public static class CharSequenceUtility {

        public static CharSequence noTrailingwhiteLines(CharSequence text) {

            if (text.length() > 0) {
                while (text.charAt(text.length() - 1) == '\n') {
                    text = text.subSequence(0, text.length() - 1);
                }
            }
            return text;
        }

    }

    public static class DummyData {

        public static CharSequence getBocHeaderDummyData() {
            CharSequence header = CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml("<div><h2>Dewan Komisaris</h2><div></div></div>")));
            return header;
        }

        public static CharSequence getBodHeaderDummyData() {
            CharSequence header = CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml("<div><h2>Dewan Direksi</h2><div></div></div>")));
            return header;
        }

    }

}
