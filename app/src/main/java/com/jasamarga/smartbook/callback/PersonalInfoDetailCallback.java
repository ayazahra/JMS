package com.jasamarga.smartbook.callback;

import com.jasamarga.smartbook.object.PersonalInfo;

import java.util.List;

/**
 * Created by apridosandyasa on 8/13/16.
 */
public interface PersonalInfoDetailCallback {
    void finishedSetupPersonalDetailInfoViews(PersonalInfo personalInfo);
    void finishedSetupAtasanViews(PersonalInfo personalInfo);
    void finishedSetupPeerListViews(List<PersonalInfo> personalInfoList);
    void finishedSetupBawahanListViews(List<PersonalInfo> personalInfoList);
}
