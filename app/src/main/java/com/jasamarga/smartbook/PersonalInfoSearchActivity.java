package com.jasamarga.smartbook;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jasamarga.smartbook.adapter.PersonalInfoSearchAdapter;
import com.jasamarga.smartbook.callback.PersonalInfoCallback;
import com.jasamarga.smartbook.fragment.PersonalInfoFragment;
import com.jasamarga.smartbook.logic.PersonalInfoLogic;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.PersonalInfoSearch;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class PersonalInfoSearchActivity extends AppCompatActivity implements PersonalInfoCallback {

    private final String TAG = PersonalInfoSearchActivity.class.getSimpleName();
    private Toolbar toolbar_search;
    private ListView lv_search;
    private PersonalInfoSearchAdapter lv_search_adapter;
    private SwingBottomInAnimationAdapter lv_search_animation_adapter;
    private PersonalInfoFragment personalInfoFragment;
    private List<PersonalInfo> personalInfoList = new ArrayList<>();
    private String searchQuery = "";
    private SearchView searchView;
    private SearchManager searchManager;
    private MenuItem searchMenuItem;
    private PersonalInfoLogic personalInfoLogic;
    private boolean isLoadMore = false;
    private boolean isMaxSize = false;
    private int prevSize = 0;
    private String param = "";
    private String personalNpp = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getIntent().getStringExtra("personalNpp") != null)
            this.personalNpp = getIntent().getStringExtra("personalNpp");

        if (getIntent().getStringExtra("personalNama") != null)
            this.param = getIntent().getStringExtra("personalNama");

        this.toolbar_search = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(this.toolbar_search);

        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            this.searchQuery = intent.getStringExtra(SearchManager.QUERY);
        }
        this.param = this.searchQuery.replace(" ", "%20");
        this.prevSize = 0;
        this.isMaxSize = false;
        this.isLoadMore = false;
        this.personalInfoList.clear();
        this.progressDialog.show();
        this.personalInfoLogic.setupPersonalInfoViews(20, this.prevSize, this.param);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        this.searchMenuItem = menu.findItem(R.id.action_search);
        this.searchView = (SearchView) this.searchMenuItem.getActionView();
        this.searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        this.searchView.setSearchableInfo(this.searchManager.getSearchableInfo(PersonalInfoSearchActivity.this.getComponentName()));
        this.searchView.setOnQueryTextListener(new SearchQueryListener());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            PersonalInfoSearchActivity.this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("PERSONAL INFO");

        setupPersonalInfoSearchProgressDialog();

        this.lv_search = (ListView) findViewById(R.id.lv_search);

        this.progressDialog.show();
        this.personalInfoLogic = new PersonalInfoLogic(PersonalInfoSearchActivity.this, this);
        this.personalInfoLogic.setupPersonalInfoViews(20, this.prevSize, this.param);
    }

    private void setupPersonalInfoSearchViews() {
        Log.d(TAG, "searchQuery " + this.searchQuery);
        this.progressDialog.dismiss();
        doSearchPersonalInfo(this.searchQuery);
    }

    private void ShowPersonalInfoViews(int position) {
        this.personalInfoFragment = new PersonalInfoFragment(PersonalInfoSearchActivity.this, this.personalInfoList.get(position).getNpp());
        this.personalInfoFragment.show(getSupportFragmentManager(), "personalInfoFragment");
    }

    private void doSearchPersonalInfo(String searchQuery) {
        Log.d(TAG, "searchQuery1 " + searchQuery);
        this.lv_search.setAdapter(null);
        this.lv_search_adapter = null;

        this.lv_search_adapter = new PersonalInfoSearchAdapter(PersonalInfoSearchActivity.this, this.personalInfoList);
//        this.lv_search_animation_adapter = new SwingBottomInAnimationAdapter(this.lv_search_adapter);
//        this.lv_search_animation_adapter.setAbsListView(this.lv_search);
        this.lv_search.setAdapter(this.lv_search_adapter);
        this.lv_search.setOnItemClickListener(new ActionPersonalInfoSearchList());
        this.lv_search.setOnScrollListener(new PersonalInfoSearchLazyLoad());

        if (!this.personalNpp.equals(""))
            ShowPersonalInfoViews(0);
    }

    @Override
    public void finishedSetupPersonalInfoViews(List<PersonalInfo> personalInfoSearchList) {
        this.personalInfoList = personalInfoSearchList;
        this.prevSize = this.personalInfoList.size();

        if (!this.personalNpp.equals("")) {
            this.isMaxSize = true;
        }

        setupPersonalInfoSearchViews();
    }

    @Override
    public void finishedMorePersonalInfoViews(List<PersonalInfo> personalInfoSearchList) {
        for (PersonalInfo personalInfo : personalInfoSearchList) {
            this.personalInfoList.add(personalInfo);
        }
        this.lv_search_adapter.notifyDataSetChanged();
        if (this.prevSize != this.personalInfoList.size())
            this.prevSize = this.personalInfoList.size();
        else
            this.isMaxSize = true;
        this.isLoadMore = false;
        this.progressDialog.dismiss();
        //setupPersonalInfoSearchViews();
    }

    @Override
    public void failedSetupPersonalInfoViews() {

    }

    private void setupPersonalInfoSearchProgressDialog() {
        this.progressDialog = new ProgressDialog(PersonalInfoSearchActivity.this);
        this.progressDialog.setMessage("Mohon tunggu..");
        this.progressDialog.setCancelable(false);
    }

    private class ActionPersonalInfoSearchList implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShowPersonalInfoViews(position);
        }
    }

    private class SearchQueryListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.d(TAG, "searchQuery2 " + query);
            //doSearchPersonalInfo(query);

            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Log.d(TAG, "searchQuery2 " + newText);
            if (TextUtils.isEmpty(newText)) {
                searchQuery = "";
                param = "";
                prevSize = 0;
                isMaxSize = false;
                isLoadMore = false;
                personalInfoList.clear();
                progressDialog.show();
                personalInfoLogic.setupPersonalInfoViews(20, prevSize, param);
            }

            return false;
        }
    }

    private class PersonalInfoSearchLazyLoad implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
            {
                if(isLoadMore == false && isMaxSize == false)
                {
                    isLoadMore = true;
                    progressDialog.show();
                    param = (searchQuery.equals("") ? "" : searchQuery.replace(" ", "%20"));
                    personalInfoLogic.setupMorePersonalInfoViews(20, totalItemCount, param);
                }
            }
        }
    }
}
