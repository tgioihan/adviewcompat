package com.ads.adviewcompat;

import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;

public class FacebookAudienceTracking implements AdListener {

    private boolean adLoaded;
    private Context context;

    public FacebookAudienceTracking(Context context) {
        this.context = context;
    }

    public boolean isAdLoaded() {
        return adLoaded;
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        adLoaded = false;
    }

    @Override
    public void onAdLoaded(Ad ad) {
        adLoaded = true;
        AdTrackingSender.sendTrakingEvent(context, AdTrackingSender.FACEBOOK_ADS, AdTrackingSender.AD_UNIT_VIEW);
    }

    @Override
    public void onAdClicked(Ad ad) {
        AdTrackingSender.sendTrakingEvent(context, AdTrackingSender.FACEBOOK_ADS, AdTrackingSender.AD_UNIT_CLICK);
    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }

    public void setLoaded(boolean loaded) {
        adLoaded = loaded;
    }
}
