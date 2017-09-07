package com.jasamarga.smartbook.callback;

import com.jasamarga.smartbook.object.BocData;
import com.jasamarga.smartbook.object.Cabang;
import com.jasamarga.smartbook.object.Highlight;
import com.jasamarga.smartbook.object.MainMenu;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.Submenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public interface MainCallback {
    void finishedSetupMainViews(List<MainMenu> mainMenuList,
                                List<Cabang> cabangList,
                                List<Cabang> headerList);
    void finishedSetupSubmenuViews(List<Submenu> submenuList, int mainMenuId);
    void finishedSetupHeaderListViews(List<Cabang> cabangList, int mainMenuId);
    void finishedSetupPusatContentViews(String kontent, int mainMenuId, int subMenuId, int position);
    void finishedSetupCabangContentViews(String kontent, int mainMenuId, int subMenuId, int kantorId, int position);
    void finishedSetupPenghargaanViews(HashMap<String, Object> contentPenghargaan, int mainMenuId, int subMenuId, int position);
    void finishedSetupBOCBOD(List<BocData> bocDataList, int mainMenuId, int subMenuId, int kantorId, int position);
    void finishedSetupSlideViews(List<Highlight> highlightList);
    void showPersonalInfoFromSrukturCabangViews(PersonalInfo personalInfo);
    void failedSetupMainViews();
    void failedSetupSlideViews();
}
