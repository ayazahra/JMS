package com.jasamarga.smartbook.callback;

import com.jasamarga.smartbook.object.PersonalInfo;

import java.util.List;

/**
 * Created by apridosandyasa on 10/11/16.
 */

public interface StrukturCabangCallback {
    void finishedSetupStruktrukCabangViews(PersonalInfo personalInfo, List<PersonalInfo> bawahanList);
}
