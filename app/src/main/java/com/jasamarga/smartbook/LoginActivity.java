package com.jasamarga.smartbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jasamarga.smartbook.callback.LoginCallback;
import com.jasamarga.smartbook.logic.LoginLogic;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;

/**
 * Created by apridosandyasa on 9/28/16.
 */

public class LoginActivity extends AppCompatActivity implements LoginCallback {

    private final static String TAG = LoginActivity.class.getSimpleName();
    private ImageView iv_header_bg_login;
    private ImageView iv_icon_user_login;
    private ImageView iv_icon_pass_login;
    private TextInputEditText ed_input_user_login;
    private TextInputEditText ed_input_pass_login;
    private Button btn_submit_login;
    private LoginLogic loginLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        this.iv_header_bg_login = (ImageView) findViewById(R.id.iv_header_bg_login);
        this.iv_icon_user_login = (ImageView) findViewById(R.id.iv_icon_user_login);
        this.iv_icon_pass_login = (ImageView) findViewById(R.id.iv_icon_pass_login);
        this.ed_input_user_login = (TextInputEditText) findViewById(R.id.ed_input_user_login);
        this.ed_input_pass_login = (TextInputEditText) findViewById(R.id.ed_input_pass_login);
        this.btn_submit_login = (Button) findViewById(R.id.btn_submit_login);

        setupLoginHeaderBasedOnScreenSize();

        this.iv_icon_user_login.setColorFilter(getResources().getColor(R.color.colorBlueMicrosoft), PorterDuff.Mode.SRC_ATOP);
        this.iv_icon_pass_login.setColorFilter(getResources().getColor(R.color.colorBlueMicrosoft), PorterDuff.Mode.SRC_ATOP);

        this.loginLogic = new LoginLogic(LoginActivity.this, this);
        this.ed_input_pass_login.setOnEditorActionListener(new ActionLoginFromInput());
        this.btn_submit_login.setOnClickListener(new ActionLogin());
    }

    @Override
    public void finishedLoginProcess(String msg, String apiKey) {
        Log.d(TAG, "msg response " + msg);
        if (msg.equals("success !")) {
            SharedPreferencesProvider.getInstance().setLogin(LoginActivity.this, true);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ed_input_pass_login.getWindowToken(), 0);
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    LoginActivity.this.finish();
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void setupLoginHeaderBasedOnScreenSize() {
        DisplayMetrics metrics = Utility.DisplayUtility.getDisplayMetrics(LoginActivity.this);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        if (smallestWidth > 600) {
            this.iv_header_bg_login.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            this.iv_header_bg_login.setScaleType(ImageView.ScaleType.CENTER);
        }
    }

    private class ActionLogin implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            loginLogic.setupLoginProcess(ed_input_user_login.getText().toString(),
                    ed_input_pass_login.getText().toString());
        }
    }

    private class ActionLoginFromInput implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                // Your piece of code on keyboard search click
                loginLogic.setupLoginProcess(ed_input_user_login.getText().toString(),
                        ed_input_pass_login.getText().toString());

                return true;
            }
            return false;
        }
    }

}
