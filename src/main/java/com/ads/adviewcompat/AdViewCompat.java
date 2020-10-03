package com.ads.adviewcompat;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.Arrays;


public class AdViewCompat extends ViewGroup implements  AdTracking.AdCallback {

    private String fbId;
    private final String fbType;
    private String admobAdUnitId;
    private final String admobSize;
    private int heightDimension;
    private AdProvider adProvider;
    private View adView;
    private NativeAd nativeAd;
    private NativeAdView.Type fbNativeType;
    private AdTracking adTracking;

    public AdViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AdViewCompat,
                0, 0);
        fbId = a.getString(R.styleable.AdViewCompat_fbId);
        fbType = a.getString(R.styleable.AdViewCompat_fbType);
        admobAdUnitId = a.getString(R.styleable.AdViewCompat_admobAdUnitId);
        admobSize = a.getString(R.styleable.AdViewCompat_admobSize);
        a.recycle();
        adTracking = new AdTracking(context, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        if (adView != null) {
            measureChild(adView, MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(heightDimension, MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(width, heightDimension + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int w = r - l;
        final int h = b - t;
        if (adView != null) {
            adView.layout(w / 2 - adView.getMeasuredWidth() / 2, h / 2 - adView.getMeasuredHeight() / 2, w / 2 + adView.getMeasuredWidth() / 2, h / 2 + adView.getMeasuredHeight() / 2);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (adView != null && adView instanceof NativeExpressAdView) {
            ((NativeExpressAdView) adView).resume();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (adView != null && adView instanceof NativeExpressAdView) {
            ((NativeExpressAdView) adView).pause();
        }
    }

    public void setAdProvider(AdProvider adProvider, AdInfo adInfo) {
        fbId = adInfo.getFbId();
        admobAdUnitId = adInfo.getAdmobAdUnitId();
        setAdProvider(adProvider);
    }

    public void setAdProvider(AdProvider adProvider) {
        adTracking.setAdProvider(adProvider);
        if (this.adProvider == null || this.adProvider != adProvider) {
            this.adProvider = adProvider;
            removeView(adView);
            Log.d("fs","provider "+adProvider);
            if (adProvider == AdProvider.AdMob) {
                adView = new NativeExpressAdView(getContext());
                adView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                ((NativeExpressAdView) adView).setAdUnitId(admobAdUnitId);
                AdSize adsSize = parseAdsSize(admobSize);

                ((NativeExpressAdView) adView).setAdSize(adsSize);
                heightDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, adsSize.getHeight(), getContext().getResources().getDisplayMetrics());
                addView(adView);
            } else {
                fbNativeType = getNativeFbType();
                adView = (NativeAdsFacebook) LayoutInflater.from(getContext()).inflate(getNativeFbView(fbNativeType), this, false);
                adView.setVisibility(INVISIBLE);
                addView(adView);
                nativeAd = new NativeAd(getContext(), fbId);

                AdSettings.addTestDevice("c504255f50faeaf7686352d08586be7a");
                AdSettings.addTestDevice("10b663919d20309fd875c3db01b8d88c");
                AdSettings.addTestDevice("dcd2eeab5c6b1b88910b88c0aa851df4");
                AdSettings.addTestDevice("3256CD20E2F3177A4F71124FD709C3FF");
            }
        }
    }

    private int getNativeFbView(NativeAdView.Type fbNativeType) {
        switch (fbNativeType) {
            case HEIGHT_100:
                return R.layout.fb_ad_layout_100;
            case HEIGHT_120:
                return R.layout.fb_ad_layout_100;
            case HEIGHT_300:
                return R.layout.fb_ad_layout_100;
            case HEIGHT_400:
                return R.layout.fb_ad_layout_100;
            default:
                throw new IllegalArgumentException("fbType must be in 100, 120,300,400");
        }
    }

    public void load() {
        if (adProvider == null) {
            return;
        }
        if (adTracking.isAdLoaded()) {
            return;
        }
        if (adProvider == AdProvider.AdMob) {

            ((NativeExpressAdView) adView).loadAd(TestDeviceAdmob.getRequest());
        } else if (adProvider == AdProvider.FbAudience) {
            nativeAd.loadAd();
        }
    }

    public static AdSize parseAdsSize(String admobSize) {
        if (!TextUtils.isEmpty(admobSize) && admobSize.contains("x")) {
            try {
                int width = Integer.valueOf(admobSize.substring(0, admobSize.indexOf("x")));
                int height = Integer.valueOf(admobSize.substring(admobSize.indexOf("x") + 1, admobSize.length()));
                return new AdSize(width, height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private NativeAdView.Type getNativeFbType() {
        switch (fbType) {
            case "100":
                heightDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getContext().getResources().getDisplayMetrics());
                return NativeAdView.Type.HEIGHT_100;
            case "120":
                heightDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getContext().getResources().getDisplayMetrics());
                return NativeAdView.Type.HEIGHT_120;
            case "300":
                heightDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getContext().getResources().getDisplayMetrics());
                return NativeAdView.Type.HEIGHT_300;
            case "400":
                heightDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getContext().getResources().getDisplayMetrics());
                return NativeAdView.Type.HEIGHT_400;
            default:
                throw new IllegalArgumentException("fbType must be in 100, 120,300,400");
        }
    }

    @Override
    public void onAdmobLoaded() {

    }

    @Override
    public void onFacebookAudienceLoaded(Ad ad) {
        if (adView != null && adView instanceof NativeAdsFacebook) {
            ((NativeAdsFacebook) adView).bind(ad, nativeAd);
            adView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onAdmobLoadFailed() {

    }

    @Override
    public void onFacebookAudienceLoadFailed(Ad ad, AdError adError) {
        setAdProvider(AdProvider.AdMob);
        load();
    }
}
