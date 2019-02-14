package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.adapter.BawahanListAdapter;
import com.jasamarga.smartbook.adapter.RekanListAdapter;
import com.jasamarga.smartbook.callback.BawahanListAdapterCallback;
import com.jasamarga.smartbook.callback.PersonalInfoDetailCallback;
import com.jasamarga.smartbook.callback.RekanListAdapterCallback;
import com.jasamarga.smartbook.logic.PersonalInfoDetailLogic;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 8/4/16.
 */
public class PersonalInfoFragment extends DialogFragment implements AppBarLayout.OnOffsetChangedListener,
        PersonalInfoDetailCallback, BawahanListAdapterCallback, RekanListAdapterCallback {

    private final static String TAG = PersonalInfoFragment.class.getSimpleName();
    private Context context;
    private View view;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer, ll_contentatasan_personalinfo,
                        ll_contentbawahan_personalinfo, ll_content_rekan_personalinfo;
    private RelativeLayout rl_contentpersonal_personalinfo, rl_borderpersonal_personalinfo,
                            rl_borderpersonal1_personalinfo, rl_borderpersonal2_personalinfo;
    private AppBarLayout abl_personalinfo;
    private Toolbar toolbar_personalinfo;
    private NestedScrollView nsv_contentcontainer_personalinfo;
    private CardView cv_hierarki_personalinfo;
    private CircleImageView civ_pict_personalinfo, civ_atasan_personalinfo,
                            civ_personal_personalinfo;
    private CustomTextView tv_name_personalinfo, tv_jabatan_personalinfo, tv_statuspeg_personalinfo,
                    tv_alamatkantor_personalinfo, tv_tempatttl_personalinfo, tv_genderkelamin_personalinfo,
                    tv_alamat_personalinfo, tv_telp_personalinfo, tv_title_personalinfo,
                    tv_atasan_personalinfo, tv_personal_personalinfo;
    private RecyclerView rv_rekan_personalinfo;
    private LinearLayoutManager rv_rekan_personalinfo_llm;
    private RekanListAdapter lv_rekan_personalinfo_adapter;
    private RecyclerView rv_bawahan_personalinfo;
    private LinearLayoutManager rv_bawahan_personalinfo_llm;
    private BawahanListAdapter rv_bawahan_personalinfo_adapter;
    private PersonalInfo personalInfo;
    private PersonalInfo atasanInfo;
    private List<PersonalInfo> peerList = new ArrayList<>();
    private List<PersonalInfo> bawahanList = new ArrayList<>();
    private PersonalInfoDetailLogic personalInfoDetailLogic;
    private String params;
    private Target loadedBitmap, loadedBitmap1;

    public PersonalInfoFragment() {

    }

    @SuppressLint("ValidFragment")
    public PersonalInfoFragment(Context context, String personNpp) {
        this.context = context;
        this.params = personNpp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.width = (int) (Utility.DisplayUtility.getScreenWidth(this.context) * 0.97);
        windowParams.height = (int) (Utility.DisplayUtility.getScreenHeight(this.context) * 0.97);
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_personalinfo, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        toolbar_personalinfo        = (Toolbar) view.findViewById(R.id.toolbar_personalinfo);
        mTitleContainer = (LinearLayout) view.findViewById(R.id.ll_title_personalinfo);
        abl_personalinfo   = (AppBarLayout) view.findViewById(R.id.abl_personalinfo);
        this.nsv_contentcontainer_personalinfo = (NestedScrollView) view.findViewById(R.id.nsv_contentcontainer_personalinfo);
        this.cv_hierarki_personalinfo = (CardView) view.findViewById(R.id.cv_hierarki_personalinfo);
        this.civ_pict_personalinfo = (CircleImageView) view.findViewById(R.id.civ_pict_personalinfo);
        this.tv_name_personalinfo = (CustomTextView) view.findViewById(R.id.tv_name_personalinfo);
        this.tv_jabatan_personalinfo = (CustomTextView) view.findViewById(R.id.tv_jabatan_personalinfo);
        this.tv_statuspeg_personalinfo = (CustomTextView) view.findViewById(R.id.tv_statuspeg_personalinfo);
        this.tv_alamatkantor_personalinfo = (CustomTextView) view.findViewById(R.id.tv_alamatkantor_personalinfo);
        this.tv_tempatttl_personalinfo = (CustomTextView) view.findViewById(R.id.tv_tempatttl_personalinfo);
        this.tv_genderkelamin_personalinfo = (CustomTextView) view.findViewById(R.id.tv_genderkelamin_personalinfo);
        this.tv_alamat_personalinfo = (CustomTextView) view.findViewById(R.id.tv_alamat_personalinfo);
        this.tv_telp_personalinfo = (CustomTextView) view.findViewById(R.id.tv_telp_personalinfo);
        this.tv_title_personalinfo = (CustomTextView) view.findViewById(R.id.tv_title_personalinfo);
        this.ll_content_rekan_personalinfo = (LinearLayout) view.findViewById(R.id.ll_content_rekan_personalinfo);
        this.ll_contentatasan_personalinfo = (LinearLayout) view.findViewById(R.id.ll_contentatasan_personalinfo);
        this.civ_atasan_personalinfo = (CircleImageView) view.findViewById(R.id.civ_atasan_personalinfo);
        this.tv_atasan_personalinfo = (CustomTextView) view.findViewById(R.id.tv_atasan_personalinfo);
        this.rl_contentpersonal_personalinfo = (RelativeLayout) view.findViewById(R.id.rl_contentpersonal_personalinfo);
        this.rl_borderpersonal_personalinfo = (RelativeLayout) view.findViewById(R.id.rl_borderpersonal_personalinfo);
        this.rl_borderpersonal1_personalinfo = (RelativeLayout) view.findViewById(R.id.rl_borderpersonal1_personalinfo);
        this.rl_borderpersonal2_personalinfo = (RelativeLayout) view.findViewById(R.id.rl_borderpersonal2_personalinfo);
        this.civ_personal_personalinfo = (CircleImageView) view.findViewById(R.id.civ_personal_personalinfo);
        this.tv_personal_personalinfo = (CustomTextView) view.findViewById(R.id.tv_personal_personalinfo);
        this.rv_rekan_personalinfo = (RecyclerView) view.findViewById(R.id.rv_rekan_personalinfo);
        this.ll_contentbawahan_personalinfo = (LinearLayout) view.findViewById(R.id.ll_contentbawahan_personalinfo);
        this.rv_bawahan_personalinfo = (RecyclerView) view.findViewById(R.id.rv_bawahan_personalinfo);

        abl_personalinfo.addOnOffsetChangedListener(this);

        this.toolbar_personalinfo.inflateMenu(R.menu.menu_personalinfo);

        startAlphaAnimation(this.tv_title_personalinfo, 0, View.INVISIBLE);

        this.personalInfoDetailLogic = new PersonalInfoDetailLogic(this.context, this);
        this.personalInfoDetailLogic.setupPersonalDetailInfoViews(this.params);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(this.tv_title_personalinfo, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(this.tv_title_personalinfo, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onRekanListAdapterCallback(int position) {
        this.personalInfoDetailLogic.setupPersonalDetailInfoViews(this.peerList.get(position).getNpp());
        nsv_contentcontainer_personalinfo.post(new Runnable() {
            @Override
            public void run() {
                abl_personalinfo.setExpanded(true, true);
                nsv_contentcontainer_personalinfo.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onBawahanListAdapterCallback(int position) {
        this.personalInfoDetailLogic.setupPersonalDetailInfoViews(this.bawahanList.get(position).getNpp());
        nsv_contentcontainer_personalinfo.post(new Runnable() {
            @Override
            public void run() {
                abl_personalinfo.setExpanded(true, true);
                nsv_contentcontainer_personalinfo.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void finishedSetupPersonalDetailInfoViews(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;

        this.tv_name_personalinfo.setText(this.personalInfo.getNama());
        this.tv_jabatan_personalinfo.setText(this.personalInfo.getJabatan());
        this.tv_statuspeg_personalinfo.setText(this.personalInfo.getNpp() + ", " + this.personalInfo.getStatus_desc());
        this.tv_alamatkantor_personalinfo.setText(this.personalInfo.getKantor_alamat());
        //this.tv_tempatttl_personalinfo.setText(this.personalInfo.getTempatlahir() + ", " + Utility.DateUtility.parseFormatDate(this.personalInfo.getTanggallahir()));

        this.tv_tempatttl_personalinfo.setText(this.personalInfo.getTempatlahir() + ", " + this.convertDate(this.personalInfo.getTanggallahir()));

        this.tv_genderkelamin_personalinfo.setText(this.personalInfo.getAgama() + ", " + (this.personalInfo.getJenis_kelamin().equals("M") ? "Laki-laki" : "Perempuan"));
        this.tv_alamat_personalinfo.setText("-"); //setText(this.personalInfo.getPersonAddress());
        this.tv_telp_personalinfo.setText("-"); //setText(this.personalInfo.getPersonTelp());
        this.tv_telp_personalinfo.setTextColor(this.context.getResources().getColor(R.color.colorBlueMicrosoft));
        this.tv_telp_personalinfo.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        this.tv_title_personalinfo.setText(this.personalInfo.getNama());

        if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                civ_pict_personalinfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                civ_personal_personalinfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        this.civ_pict_personalinfo.setTag(this.loadedBitmap);
        this.civ_personal_personalinfo.setTag(this.loadedBitmap);

        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" +this.personalInfo.getUrlfoto())));

        Picasso.with(this.context)
                .load(url.toString())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap);

        this.tv_telp_personalinfo.setOnClickListener(new ActionCallPhoneNumber());
        this.toolbar_personalinfo.setOnMenuItemClickListener(new ActionMenuClickHirarki());

        this.tv_personal_personalinfo.setText(this.personalInfo.getNama());
        this.civ_personal_personalinfo.setOnClickListener(new ActionPictPersonalListener());

        this.personalInfoDetailLogic.setupAtasanViews(this.personalInfo.getNpp_atasan());
    }

    @Override
    public void finishedSetupAtasanViews(PersonalInfo personalInfo) {
        this.atasanInfo = personalInfo;

        if (this.atasanInfo.getNpp() == null) {
            this.ll_contentatasan_personalinfo.setVisibility(View.GONE);
            this.rl_borderpersonal2_personalinfo.setVisibility(View.GONE);
        }else {
            this.ll_contentatasan_personalinfo.setVisibility(View.VISIBLE);
            this.rl_borderpersonal2_personalinfo.setVisibility(View.VISIBLE);
        }

        if (this.loadedBitmap1 == null) this.loadedBitmap1 = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                civ_atasan_personalinfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        this.civ_atasan_personalinfo.setTag(this.loadedBitmap1);

        CharSequence urlAtasan = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" + this.atasanInfo.getUrlfoto())));

        Picasso.with(this.context)
                .load(urlAtasan.toString())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap1);

        this.tv_atasan_personalinfo.setText(this.atasanInfo.getNama());

        this.civ_atasan_personalinfo.setOnClickListener(new ActionPictAtasanListener());

        this.personalInfoDetailLogic.setupPeerListViews(this.atasanInfo.getNpp());
    }

    @Override
    public void finishedSetupPeerListViews(List<PersonalInfo> personalInfoList) {
        this.peerList = personalInfoList;


        if (this.peerList.size() > 0) {
            for (int i = 0; i < this.peerList.size(); i++) {
                if (this.peerList.get(i).getNpp().equals(this.personalInfo.getNpp())) {
                    this.peerList.remove(i);
                }
            }

            this.rl_borderpersonal_personalinfo.setVisibility(View.VISIBLE);
            this.rv_rekan_personalinfo.setVisibility(View.VISIBLE);
        }else {
            this.rl_borderpersonal_personalinfo.setVisibility(View.GONE);
            this.rv_rekan_personalinfo.setVisibility(View.GONE);
        }

        this.rv_rekan_personalinfo_llm = new LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        this.rv_rekan_personalinfo.setHasFixedSize(true);
        this.rv_rekan_personalinfo.setLayoutManager(this.rv_rekan_personalinfo_llm);

        this.lv_rekan_personalinfo_adapter = new RekanListAdapter(this.context, this.peerList, this);
        this.rv_rekan_personalinfo.setAdapter(this.lv_rekan_personalinfo_adapter);

        this.personalInfoDetailLogic.setupBawahanListViews(this.personalInfo.getNpp());
    }

    @Override
    public void finishedSetupBawahanListViews(List<PersonalInfo> personalInfoList) {
        this.bawahanList = personalInfoList;

        if (this.bawahanList.size() > 0) {
            this.rl_borderpersonal1_personalinfo.setVisibility(View.VISIBLE);
            this.ll_contentbawahan_personalinfo.setVisibility(View.VISIBLE);
        }else{
            this.rl_borderpersonal1_personalinfo.setVisibility(View.GONE);
            this.ll_contentbawahan_personalinfo.setVisibility(View.GONE);
        }

        this.rv_bawahan_personalinfo_llm = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        this.rv_bawahan_personalinfo.setHasFixedSize(true);
        this.rv_bawahan_personalinfo.setLayoutManager(this.rv_bawahan_personalinfo_llm);

        this.rv_bawahan_personalinfo_adapter = new BawahanListAdapter(this.context, this.bawahanList, this);
        this.rv_bawahan_personalinfo.setAdapter(this.rv_bawahan_personalinfo_adapter);
    }

    private String convertDate (String sourceDateTxt)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(sourceDateTxt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy");
        String resultDate = targetFormat.format(sourceDate);
        return resultDate;
    }

    private class ActionPictAtasanListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            params = atasanInfo.getNpp();
            personalInfoDetailLogic.setupPersonalDetailInfoViews(params);
            nsv_contentcontainer_personalinfo.post(new Runnable() {
                @Override
                public void run() {
                    abl_personalinfo.setExpanded(true, true);
                    nsv_contentcontainer_personalinfo.scrollTo(0, 0);
                }
            });
        }
    }

    private class ActionPictPersonalListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            params = personalInfo.getNpp();
            personalInfoDetailLogic.setupPersonalDetailInfoViews(params);
            nsv_contentcontainer_personalinfo.post(new Runnable() {
                @Override
                public void run() {
                    abl_personalinfo.setExpanded(true, true);
                    nsv_contentcontainer_personalinfo.scrollTo(0, 0);
                }
            });
        }
    }

    private class ActionMenuClickHirarki implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_hirarki) {
                nsv_contentcontainer_personalinfo.post(new Runnable() {
                    @Override
                    public void run() {
                        abl_personalinfo.setExpanded(false, true);
                        nsv_contentcontainer_personalinfo.scrollTo(0, cv_hierarki_personalinfo.getBottom());
                    }
                });

                return true;
            }

            return false;
        }
    }

    private class ActionCallPhoneNumber implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            if (!personalInfo.getTelp1().equals("")) {
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:" + personalInfo.getTelp1()));
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(callIntent);
//            }
        }
    }

}
