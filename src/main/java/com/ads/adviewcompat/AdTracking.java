package com.ads.adviewcompat;

import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;

public class AdTracking extends AdListener implements com.facebook.ads.AdListener {

    private AdmobAdTracking admobAdTracking;
    private FacebookAudienceTracking facebookAudienceTracking;
    private AdProvider adProvider;
    private AdCallback adCallback;

    public AdTracking(Context context, AdCallback adCallback) {
        this.adCallback = adCallback;
        admobAdTracking = new AdmobAdTracking(context);
        facebookAudienceTracking = new FacebookAudienceTracking(context);
    }

    public void setAdProvider(AdProvider adProvider) {
        this.adProvider = adProvider;
        setLoaded(false);
    }

    public boolean isAdLoaded() {
        if (adProvider == AdProvider.AdMob) {
            return admobAdTracking.isAdLoaded();
        }
        return facebookAudienceTracking.isAdLoaded();
    }

    public void setLoaded(boolean loaded) {
        if (adProvider == AdProvider.AdMob) {
            admobAdTracking.setLoaded(loaded);
        } else {
            facebookAudienceTracking.setLoaded(loaded);
        }
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
        admobAdTracking.onAdClosed();
    }

    @Override
    public void onAdFailedToLoad(int i) {
        super.onAdFailedToLoad(i);
        admobAdTracking.onAdFailedToLoad(i);
        adCallback.onAdmobLoadFailed();
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
        admobAdTracking.onAdLeftApplication();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
        admobAdTracking.onAdOpened();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
        admobAdTracking.onAdLoaded();
        adCallback.onAdmobLoaded();
    }

    @Override
    public void onError(Ad ad, AdError adError) {
        facebookAudienceTracking.onError(ad, adError);
        adCallback.onFacebookAudienceLoadFailed(ad, adError);
    }

    @Override
    public void onAdLoaded(Ad ad) {
        facebookAudienceTracking.onAdLoaded(ad);
        adCallback.onFacebookAudienceLoaded(ad);
    }

    @Override
    public void onAdClicked(Ad ad) {
        facebookAudienceTracking.onAdClicked(ad);
    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }

    public interface AdCallback {
        void onAdmobLoaded();

        void onFacebookAudienceLoaded(Ad ad);

        void onAdmobLoadFailed();

        void onFacebookAudienceLoadFailed(Ad ad, AdError adError);
    }
}
