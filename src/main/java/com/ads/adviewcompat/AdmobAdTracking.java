package com.ads.adviewcompat;

import android.content.Context;

import com.google.android.gms.ads.AdListener;

public class AdmobAdTracking extends AdListener {

    private boolean adLoaded;
    private Context context;

    public AdmobAdTracking(Context context) {
        this.context = context;
    }

    public boolean isAdLoaded() {
        return adLoaded;
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        adLoaded = false;
        AdTrackingSender.sendTrakingEvent(context, AdTrackingSender.ADMOB_ADS, AdTrackingSender.AD_UNIT_CLICK);
    }

    @Override
    public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
        adLoaded = false;
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        adLoaded = true;
        AdTrackingSender.sendTrakingEvent(context, AdTrackingSender.ADMOB_ADS, AdTrackingSender.AD_UNIT_VIEW);
    }

    public void setLoaded(boolean loaded) {
        adLoaded = loaded;
    }
}
