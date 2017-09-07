package com.jasamarga.smartbook;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.adapter.HeaderListAdapter;
import com.jasamarga.smartbook.adapter.MainBottomContentAdapter;
import com.jasamarga.smartbook.adapter.MainContentAdapter;
import com.jasamarga.smartbook.adapter.MainSlidePagerAdapter;
import com.jasamarga.smartbook.adapter.NavigationViewAdapter;
import com.jasamarga.smartbook.callback.AnggaranFragmentCallback;
import com.jasamarga.smartbook.callback.HeaderListAdapterCallback;
import com.jasamarga.smartbook.callback.MainBottomContentAdapterCallback;
import com.jasamarga.smartbook.callback.MainCallback;
import com.jasamarga.smartbook.callback.MainContentAdapterCallback;
import com.jasamarga.smartbook.fragment.AnakBocFragment;
import com.jasamarga.smartbook.fragment.AnggaranFragment;
import com.jasamarga.smartbook.fragment.BocFragment;
import com.jasamarga.smartbook.fragment.SekilasContentFragment;
import com.jasamarga.smartbook.fragment.StrukturCabangFragment;
import com.jasamarga.smartbook.fragment.StrukturFragment;
import com.jasamarga.smartbook.logic.MainLogic;
import com.jasamarga.smartbook.object.BocData;
import com.jasamarga.smartbook.object.Cabang;
import com.jasamarga.smartbook.object.Highlight;
import com.jasamarga.smartbook.object.MainMenu;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.Submenu;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;
import com.ugurtekbas.fadingindicatorlibrary.FadingIndicator;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainCallback, MainContentAdapterCallback,
        MainBottomContentAdapterCallback, AnggaranFragmentCallback, HeaderListAdapterCallback {

    private final static String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar_main;
    private ListView lv_content_navigationview;
    private NavigationViewAdapter lv_content_navigationview_adapter;
    private RelativeLayout rl_header_main;
    private CoordinatorLayout ll_header_cabang_main;
    private RelativeLayout ll_header_cabang_slide;
    private ImageView iv_bg_cabang_slide;
    private RelativeLayout rl_border_cabang_slide;
    private CustomTextView tv_name_cabang_slide;
    private CircleImageView civ_logo_cabang_slide;
    private RecyclerView rv_header_cabang_slide;
    private LinearLayoutManager rv_header_cabang_slide_llm;
    private HeaderListAdapter rv_header_cabang_slide_adapter;
    private ViewPager vp_slide_main;
    private MainSlidePagerAdapter vp_slide_main_adapter;
    private FadingIndicator fi_slide_main;
    private RelativeLayout rl_top_main;
    private CustomTextView tv_top_main;
    private RecyclerView rv_content_main;
    private GridLayoutManager rv_content_main_glm;
    private MainContentAdapter rv_content_main_adapter;
    private RecyclerView rv_bottom_main;
    private LinearLayoutManager rv_bottom_main_llm;
    private MainBottomContentAdapter rv_bottom_main_adapter;
    private SekilasContentFragment sekilasContentFragment;
    private BottomSheetBehavior bottomSheetBehavior;
    private StrukturFragment strukturFragment;
    private AnggaranFragment anggaranFragment;
    private BocFragment bocFragment;
    private AnakBocFragment anakBocFragment;
    private StrukturCabangFragment strukturCabangFragment;
    private MainLogic mainLogic;
    private List<MainMenu> mainMenuList;
    private List<Submenu> submenuList;
    private List<Cabang> cabangList;
    private List<Cabang> headerList;
    private int headerListSelection = 0;
    private int navigationSelection = 0;
    private boolean isHeaderLoaded = false;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            this.drawerLayout.openDrawer(this.navigationView);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawers();
        if (this.navigationSelection != 0) {
            setupNavigationListSelection(0);
            this.progressDialog.show();
            mainLogic.setupHeaderViews(mainMenuList.get(0).getMainMenuId(), isHeaderLoaded);
        }else {
            if (this.bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            else
                ShowCloseDialog();
        }
    }

    private void initView() {
        this.toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);

        this.navigationView = (NavigationView) findViewById(R.id.navigationView);
        this.lv_content_navigationview = (ListView) findViewById(R.id.lv_content_navigationview);
        this.rl_header_main = (RelativeLayout) findViewById(R.id.rl_header_main);
        this.vp_slide_main = (ViewPager) findViewById(R.id.vp_slide_main);
        this.fi_slide_main = (FadingIndicator) findViewById(R.id.fi_slide_main);
        this.ll_header_cabang_main = (CoordinatorLayout) findViewById(R.id.ll_header_cabang_main);
        this.ll_header_cabang_slide = (RelativeLayout) findViewById(R.id.ll_header_cabang_slide);
        this.iv_bg_cabang_slide = (ImageView) findViewById(R.id.iv_bg_cabang_slide);
        this.rl_border_cabang_slide = (RelativeLayout) findViewById(R.id.rl_border_cabang_slide);
        this.tv_name_cabang_slide = (CustomTextView) findViewById(R.id.tv_name_cabang_slide);
        this.rv_header_cabang_slide = (RecyclerView) findViewById(R.id.rv_header_cabang_slide);
        this.civ_logo_cabang_slide = (CircleImageView) findViewById(R.id.civ_logo_cabang_slide);
        this.rl_top_main = (RelativeLayout) findViewById(R.id.rl_top_main);
        this.tv_top_main = (CustomTextView) findViewById(R.id.tv_top_main);
        this.rv_content_main = (RecyclerView) findViewById(R.id.rv_content_main);
        this.rv_bottom_main = (RecyclerView) findViewById(R.id.rv_bottom_main);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        this.rv_content_main_glm = new GridLayoutManager(MainActivity.this, 3);
        this.rv_content_main.setHasFixedSize(true);
        this.rv_content_main.setLayoutManager(this.rv_content_main_glm);

        this.rv_header_cabang_slide_llm = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        this.rv_header_cabang_slide.setHasFixedSize(true);
        this.rv_header_cabang_slide.setLayoutManager(this.rv_header_cabang_slide_llm);

        this.rv_bottom_main_llm = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        this.rv_bottom_main.setHasFixedSize(true);
        this.rv_bottom_main.setLayoutManager(this.rv_bottom_main_llm);

        setupProgressDialog();

        this.mainLogic = new MainLogic(MainActivity.this, this);
        this.progressDialog.show();
        this.mainLogic.setupMainViews();
    }

    private void setupProgressDialog() {
        if (this.progressDialog != null)
            this.progressDialog = null;
        this.progressDialog = new ProgressDialog(MainActivity.this);
        this.progressDialog.setCancelable(true);
        this.progressDialog.setCanceledOnTouchOutside(true);
        this.progressDialog.setMessage("Sedang mengunduh... mohon tunggu");
    }

    private void ShowFailedDialog() {
        this.alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Silahkan cek koneksi internet anda.")
                .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        MainActivity.this.finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();
    }

    private void ShowCloseDialog() {
        this.alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Keluar dari Smartbook?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        drawerLayout.closeDrawers();
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK &&
                                event.getAction() == KeyEvent.ACTION_UP &&
                                !event.isCanceled()) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();
    }

    private void ShowLogoutDialog() {
        this.alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("Logout dari Smartbook?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                drawerLayout.closeDrawers();
                                SharedPreferencesProvider.getInstance().setLogin(MainActivity.this, false);
                                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                MainActivity.this.finish();
                            }
                        });
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK &&
                                event.getAction() == KeyEvent.ACTION_UP &&
                                !event.isCanceled()) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();
    }

    private void setupNavigationView() {
        setSupportActionBar(this.toolbar_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        this.lv_content_navigationview.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        this.lv_content_navigationview_adapter = new NavigationViewAdapter(MainActivity.this, this.mainMenuList);
        this.lv_content_navigationview.setAdapter(this.lv_content_navigationview_adapter);
        this.lv_content_navigationview.setOnItemClickListener(new ActionNavigationView());
        setupNavigationListSelection(0);
        this.navigationSelection = 0;

        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }

        };

        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);
        this.actionBarDrawerToggle.syncState();
    }

    private void setupMainSlideViews() {
        this.mainLogic.setupSlideViews();
    }

//    private void setupMainContentViews(String type) {
//        this.mainLogic.setupMainContentViews(this.pusatList, type);
//    }

    private void setupSubmenuViews() {
        this.mainLogic.setupSubmenuViews(this.mainMenuList.get(this.navigationSelection).getMainMenuId());
    }

    private void setupMainBottomViews() {
        this.rv_bottom_main_adapter = new MainBottomContentAdapter(MainActivity.this, this.cabangList, this);
        this.rv_bottom_main.setAdapter(this.rv_bottom_main_adapter);
        this.bottomSheetBehavior = BottomSheetBehavior.from(this.rv_bottom_main);
    }

    private void ShowSekilasContentViews(String kontent, String title) {
        this.sekilasContentFragment = new SekilasContentFragment(MainActivity.this, kontent, title);
        this.sekilasContentFragment.show(getSupportFragmentManager(), "sekilasFragment");
    }

    private void ShowPenghargaanViews(HashMap<String, Object> mainContentData, String title) {
        this.strukturFragment = new StrukturFragment(MainActivity.this, mainContentData, title);
        this.strukturFragment.show(getSupportFragmentManager(), "strukturFragment");
    }

    private void ShowBocViews(String kontent, String title, String mode) {
        this.bocFragment = new BocFragment(MainActivity.this, kontent, title, mode);
        this.bocFragment.show(getSupportFragmentManager(), "bocFragment");
    }

    private void ShowAnggaranDasarViews(String kontent, String title, String mode) {
        this.anggaranFragment = new AnggaranFragment(MainActivity.this, this,  kontent, title, mode);
        this.anggaranFragment.show(getSupportFragmentManager(), "anggaranFragment");
    }

    private void ShowAnakBOCViews(List<BocData> bocDataList, String mode, String title) {
        Log.d(TAG, title);
        this.anakBocFragment = new AnakBocFragment(MainActivity.this, bocDataList, mode, title);
        this.anakBocFragment.show(getSupportFragmentManager(), "anakBocFragment");
    }

    private void ShowStrukturCabangViews(String konten, String title) {
        Log.d("Struktur", "konten " + konten);
        this.strukturCabangFragment = new StrukturCabangFragment(MainActivity.this, konten, title, this);
        this.strukturCabangFragment.show(getSupportFragmentManager(), "strukturCabangFragment");
    }

    private void ShowCabangViews() {
        this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void GoToPersonalInfoSearchViews() {
        Intent searchIntent = new Intent(MainActivity.this, PersonalInfoSearchActivity.class);
        startActivity(searchIntent);
    }

    private void GoToPersonalInfoSearchViewsWithNpp(PersonalInfo personalInfo) {
        Intent searchIntent = new Intent(MainActivity.this, PersonalInfoSearchActivity.class);
        searchIntent.putExtra("personalNpp", personalInfo.getNpp());
        searchIntent.putExtra("personalNama", personalInfo.getNama());
        startActivity(searchIntent);
    }

    @Override
    public void finishedSetupMainViews(List<MainMenu> mainMenuList, List<Cabang> cabangList, List<Cabang> headerList) {
        this.mainMenuList = mainMenuList;
        this.cabangList = cabangList;
        this.headerList = headerList;

        setupMainSlideViews();
        setupNavigationView();
        setupMainBottomViews();
        setupSubmenuViews();

        this.progressDialog.dismiss();
        drawerLayout.closeDrawers();
    }

    @Override
    public void finishedSetupHeaderListViews(List<Cabang> headerList, int mainMenuId) {
        if (isHeaderLoaded == false) {
            isHeaderLoaded = true;
            this.headerList = headerList;
            Log.d(TAG, "header size " + this.headerList.size());
            this.rv_header_cabang_slide_adapter = new HeaderListAdapter(MainActivity.this, this.headerList, mainMenuId, this, this.headerListSelection);
            this.rv_header_cabang_slide.setAdapter(this.rv_header_cabang_slide_adapter);
            this.rv_header_cabang_slide_llm.scrollToPositionWithOffset(this.headerListSelection, 0);
        }

        if (mainMenuId == 2) {
            this.rl_header_main.setVisibility(View.GONE);
            this.ll_header_cabang_main.setVisibility(View.VISIBLE);
            this.rl_top_main.setBackgroundResource(R.drawable.cabang_smartbook_bg);
            this.tv_top_main.setTextColor(getResources().getColor(R.color.colorYellowJM));
            this.ll_header_cabang_slide.setBackgroundColor(getResources().getColor(R.color.colorYellowJM));
            this.iv_bg_cabang_slide.setImageResource(R.drawable.cabang_content_bg);
            this.rl_border_cabang_slide.setBackgroundColor(getResources().getColor(R.color.colorYellowJM));
        }else if (mainMenuId == 3) {
            this.rl_header_main.setVisibility(View.GONE);
            this.ll_header_cabang_main.setVisibility(View.VISIBLE);
            this.rl_top_main.setBackgroundResource(R.drawable.cabang_smartbook_bg);
            this.tv_top_main.setTextColor(getResources().getColor(R.color.colorYellowJM));
            this.ll_header_cabang_slide.setBackgroundColor(getResources().getColor(R.color.colorRedAP));
            this.iv_bg_cabang_slide.setImageResource(R.drawable.ap_content_bg);
            this.rl_border_cabang_slide.setBackgroundColor(getResources().getColor(R.color.colorRedAP));
        }else if (mainMenuId == 4) {
            this.rl_header_main.setVisibility(View.GONE);
            this.ll_header_cabang_main.setVisibility(View.VISIBLE);
            this.rl_top_main.setBackgroundResource(R.drawable.cabang_smartbook_bg);
            this.tv_top_main.setTextColor(getResources().getColor(R.color.colorYellowJM));
            this.ll_header_cabang_slide.setBackgroundColor(getResources().getColor(R.color.colorRedAP));
            this.iv_bg_cabang_slide.setImageResource(R.drawable.ap_content_bg);
            this.rl_border_cabang_slide.setBackgroundColor(getResources().getColor(R.color.colorRedAP));
        }else if (mainMenuId == 1){
            resetHeaderListSelection();
            this.ll_header_cabang_main.setVisibility(View.GONE);
            this.rl_header_main.setVisibility(View.VISIBLE);
            this.rl_top_main.setBackgroundResource(R.drawable.smartbook_bg);
            this.tv_top_main.setTextColor(getResources().getColor(R.color.colorBlueMicrosoft));
        }

        if (this.headerList.size() > 0) {
            CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public" + this.headerList.get(headerListSelection).getCabangUrl())));
            Log.d(TAG, "header url " + url.toString());
            Picasso.with(this)
                    .load(url.toString())
                    .placeholder(R.drawable.placeholder)
                    .into(this.civ_logo_cabang_slide);
            this.tv_name_cabang_slide.setText(this.headerList.get(this.headerListSelection).getCabangDesc());

        }

        setupSubmenuViews();
    }

    @Override
    public void finishedSetupPusatContentViews(String kontent, int mainMenuId, int subMenuId, int position) {
        if (mainMenuId == 1) {
            if (subMenuId == 1 || subMenuId == 2 || subMenuId == 7) {
                ShowSekilasContentViews(kontent, this.submenuList.get(position).getSubMenuNama());
            }else if (subMenuId == 8) {
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "pdf");
            }else if (subMenuId == 3) {
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "web");
            }else if (subMenuId == 4 || subMenuId == 5) {
                ShowBocViews(kontent, this.submenuList.get(position).getSubMenuNama(), "web");
            }
        }
    }

    @Override
    public void finishedSetupCabangContentViews(String kontent, int mainMenuId, int subMenuId, int kantorId, int position) {
        if (mainMenuId == 2) {
            if (subMenuId == 15) {
            }if (subMenuId == 11) {
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "url");
            }else if (subMenuId == 15) {
                ShowStrukturCabangViews(kontent, this.submenuList.get(position).getSubMenuNama());
            }else {
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "web");
            }
        }else if (mainMenuId == 3) {
            if (subMenuId == 19) {
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "url");
            }else{
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "web");
            }
        }else if (mainMenuId == 4) {
            if (subMenuId == 24) {
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "url");
            }else{
                ShowAnggaranDasarViews(kontent, this.submenuList.get(position).getSubMenuNama(), "web");
            }
        }
    }

    @Override
    public void finishedSetupPenghargaanViews(HashMap<String, Object> contentPenghargaan, int mainMenuId, int subMenuId, int position) {
        ShowPenghargaanViews(contentPenghargaan, this.submenuList.get(position).getSubMenuNama());
    }

    @Override
    public void finishedSetupBOCBOD(List<BocData> bocDataList, int mainMenuId, int subMenuId, int kantorId, int position) {
        ShowAnakBOCViews(bocDataList, "", this.submenuList.get(position).getSubMenuNama());
    }

    @Override
    public void finishedSetupSlideViews(List<Highlight> highlightList) {
        this.vp_slide_main_adapter = new MainSlidePagerAdapter(getSupportFragmentManager(), MainActivity.this, highlightList);
        this.vp_slide_main.setAdapter(this.vp_slide_main_adapter);
        this.fi_slide_main.setViewPager(this.vp_slide_main);
    }

    @Override
    public void showPersonalInfoFromSrukturCabangViews(PersonalInfo personalInfo) {
        GoToPersonalInfoSearchViewsWithNpp(personalInfo);
    }

    @Override
    public void finishedSetupSubmenuViews(List<Submenu> submenuList, int mainMenuId) {
        this.submenuList = submenuList;
        this.rv_content_main_adapter = new MainContentAdapter(MainActivity.this, this.submenuList, mainMenuId, this);
        this.rv_content_main.setAdapter(this.rv_content_main_adapter);
        this.progressDialog.dismiss();
        drawerLayout.closeDrawers();
    }

//    @Override
//    public void finishedSetupHighlightViews(List<Highlight> highlightList) {
//        Log.d(MainActivity.class.getSimpleName(), "highlight size " + highlightList.size());
//        this.vp_slide_main_adapter = new MainSlidePagerAdapter(getSupportFragmentManager(), MainActivity.this, highlightList);
//        this.vp_slide_main.setAdapter(this.vp_slide_main_adapter);
//        this.fi_slide_main.setViewPager(this.vp_slide_main);
//    }

    @Override
    public void failedSetupMainViews() {
        this.progressDialog.dismiss();
        ShowFailedDialog();
    }

    @Override
    public void failedSetupSlideViews() {
        ShowFailedDialog();
    }

    @Override
    public void onMainContentAdapterCallback(List<Submenu> object, int position, int mainMenuId) {
        Log.d(TAG, "mainMenuId " + mainMenuId);
        Log.d(TAG, "menuId " + this.submenuList.get(position).getSubMenuId());
        if (mainMenuId == 1) {
            if (position == this.submenuList.size() - 1) {
                ShowCabangViews();
            } else if (position == 5) {
                this.mainLogic.setupPenghargaanViews(mainMenuId, this.submenuList.get(position).getSubMenuId(), position);
            } else {
                this.mainLogic.setupPusatContentViews(mainMenuId, this.submenuList.get(position).getSubMenuId(), position);
            }
        }else{
            if (this.submenuList.get(position).getSubMenuId() == 17 || this.submenuList.get(position).getSubMenuId() == 18 || this.submenuList.get(position).getSubMenuId() == 22 || this.submenuList.get(position).getSubMenuId() == 23) {
                this.mainLogic.setupBOCBOD(mainMenuId, this.submenuList.get(position).getSubMenuId(), this.headerList.get(this.headerListSelection).getKantorId(), position);
            }else{
                this.mainLogic.setupCabangContentViews(mainMenuId, this.submenuList.get(position).getSubMenuId(), this.headerList.get(this.headerListSelection).getKantorId(), position);
            }
        }
    }

    @Override
    public void onHideMainBottomContent(final Serializable object, int position) {
        setupNavigationListSelection(1);
        this.headerListSelection = position;
        this.bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        this.progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mainLogic.setupHeaderViews(mainMenuList.get(1).getMainMenuId(), isHeaderLoaded);
            }
        }, 300);
    }

    @Override
    public void onAnggaranFragmentCallback(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        startActivity(intent);
    }

    @Override
    public void onHeaderListAdapterCallback(final Serializable object, final int mainMenuId, int position) {
        this.progressDialog.show();
        this.headerListSelection = position;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mainMenuId == 2) {
                    if (isHeaderLoaded == false)
                        mainLogic.setupHeaderViews(mainMenuList.get(1).getMainMenuId(), isHeaderLoaded);
                    else
                        finishedSetupHeaderListViews(headerList, mainMenuList.get(1).getMainMenuId());
                }else if (mainMenuId == 3) {
                    if (isHeaderLoaded == false)
                        mainLogic.setupHeaderViews(mainMenuList.get(2).getMainMenuId(), isHeaderLoaded);
                    else
                        finishedSetupHeaderListViews(headerList, mainMenuList.get(2).getMainMenuId());
                }else if (mainMenuId == 4) {
                    if (isHeaderLoaded == false)
                        mainLogic.setupHeaderViews(mainMenuList.get(3).getMainMenuId(), isHeaderLoaded);
                    else
                        finishedSetupHeaderListViews(headerList, mainMenuList.get(3).getMainMenuId());
                }
            }
        }, 200);

    }

    private void setupNavigationListSelection(final int position) {
        this.navigationSelection = (position == 4) ? this.navigationSelection : position;
        this.lv_content_navigationview.postDelayed(new Runnable() {
            @Override
            public void run() {
                lv_content_navigationview.setSelection(position);
                lv_content_navigationview.setItemChecked(position, true);
            }
        }, 100);
    }

    private void resetHeaderListSelection() {
        this.isHeaderLoaded = false;
        this.headerListSelection = 0;
    }

    private class ActionNavigationView implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            resetHeaderListSelection();
            setupNavigationListSelection(position);

            if (position == 4) {
                setupNavigationListSelection(0);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainLogic.setupSubmenuViews(mainMenuList.get(0).getMainMenuId());
                        GoToPersonalInfoSearchViews();
                    }
                }, 300);
            }else if (position == 5) {
                ShowLogoutDialog();
            }else{
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainLogic.setupHeaderViews(mainMenuList.get(position).getMainMenuId(), isHeaderLoaded);
                    }
                }, 300);
            }
        }
    }
}
