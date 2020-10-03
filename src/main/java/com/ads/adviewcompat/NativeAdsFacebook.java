package com.ads.adviewcompat;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.NativeAd;

public class NativeAdsFacebook extends RelativeLayout {
    private ImageView nativeAdIcon;
    private TextView nativeAdTitle;
    private TextView nativeAdBody;
    private TextView nativeAdSocialContext;
    private TextView nativeAdCallToAction;

    public NativeAdsFacebook(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        nativeAdIcon = (ImageView) findViewById(R.id.native_ad_icon);
        nativeAdTitle = (TextView) findViewById(R.id.native_ad_title);
        nativeAdBody = (TextView) findViewById(R.id.native_ad_body);
        nativeAdSocialContext = (TextView) findViewById(R.id.native_ad_social_context);
        nativeAdCallToAction = (TextView) findViewById(R.id.native_ad_call_to_action);
        ViewCompat.setElevation(nativeAdCallToAction,5);
//        nativeAdCallToAction.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//
//            }
//        });
    }

    public void bind(Ad ad, NativeAd nativeAd) {
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        nativeAdTitle.setText(nativeAd.getAdTitle());
        nativeAdBody.setText(nativeAd.getAdBody());
        // Downloading and setting the ad icon.
        NativeAd.Image adIcon = nativeAd.getAdIcon();
        NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);
        nativeAd.unregisterView();
        nativeAd.registerViewForInteraction(this);
    }

}
