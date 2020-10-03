package com.ads.adviewcompat;

public class AdInfo {

    private final String fbId;
    private final String admobAdUnitId;

    public AdInfo(String admobAdUnitId, String fbId) {
        this.fbId = fbId;
        this.admobAdUnitId = admobAdUnitId;
    }

    public String getFbId() {
        return fbId;
    }

    public String getAdmobAdUnitId() {
        return admobAdUnitId;
    }
}
