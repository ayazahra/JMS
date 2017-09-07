package com.jasamarga.smartbook.callback;

import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.PersonalInfoSearch;

import java.util.List;

/**
 * Created by apridosandyasa on 8/10/16.
 */
public interface PersonalInfoCallback {
    void finishedSetupPersonalInfoViews(List<PersonalInfo> personalInfoList);
    void finishedMorePersonalInfoViews(List<PersonalInfo> personalInfoList);
    void failedSetupPersonalInfoViews();
}
