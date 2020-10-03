package com.ads.adviewcompat;

import android.content.Context;

public class AdTrackingSender {

    protected static final String ADMOB_ADS = "ADMOB_ADS";
    protected static final String VUNGLE_ADS = "VUNGLE_ADS";
    protected static final String UNITY_ADS = "UNITY_ADS";
    protected static final String FACEBOOK_ADS = "FACEBOOK_ADS";

    protected static final String AD_UNIT_VIEW = "AD_UNIT_VIEW";
    protected static final String AD_UNIT_COMPLETE = "AD_UNIT_COMPLETE";
    protected static final String AD_UNIT_CLICK = "AD_UNIT_CLICK";

    protected static void sendTrakingEvent(Context context, String provider, String action) {
//        Config config = ConfigService.getInstance(context).getConfig();
//        Call<Object> call = ServiceGenerator.createService(AdTrackingInterface.class).sendAdTracking(provider, config.deviceId, config.productId, config.channelId, config.subChannelId, config.clientVersion, action);
//        call.enqueue(new NetWorkCallback<Object>() {
//            @Override
//            public void onResponse(Object result) {
//
//            }
//
//            @Override
//            public void onFailure(NetWorkError e) {
//
//            }
//        });
    }
}
