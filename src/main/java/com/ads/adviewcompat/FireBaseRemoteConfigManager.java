package com.ads.adviewcompat;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class FireBaseRemoteConfigManager {
    public static final String AD_RATE = "ad_distribution";
    public static final String FACEBOOK = "facebook";
    public static final String ADMOB = "admob";

    private final FirebaseRemoteConfig fbConfig;
    private static FireBaseRemoteConfigManager instance;
    private AdProviderRateManager adProviderRateManager;
    private RemoteKeyNameConvention remoteKeyNameConvention;
    private AdMapping adMapping;

    public FireBaseRemoteConfigManager(String productKey, AdMapping adMapping) {
        this.adMapping = adMapping;
        fbConfig = FirebaseRemoteConfig.getInstance();

        remoteKeyNameConvention = new RemoteKeyNameConvention(productKey);
        adProviderRateManager = new AdProviderRateManager(fbConfig, remoteKeyNameConvention);

        Map<String, Object> dfConfigs = new HashMap<>();
        adMapping.configDefaultRemoteConfig(dfConfigs, remoteKeyNameConvention);
        dfConfigs.put(remoteKeyNameConvention.getRemoteKeyName(AD_RATE), "");
        fbConfig.setDefaults(dfConfigs);
    }

    public AdProvider getAdProvider() {
        return adProviderRateManager.getAdProvider();
    }

    public void fetch() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fbConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            fbConfig.activateFetched();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 10000L);
    }

    public FirebaseRemoteConfig getFbConfig() {
        return fbConfig;
    }

    public AdInfo getAdInfo(String baseAdName) {
        String fbKey = remoteKeyNameConvention.getRemoteKeyName(FACEBOOK + "_" + baseAdName);
        String admobKey = remoteKeyNameConvention.getRemoteKeyName(ADMOB + "_" + baseAdName);
        String fbId = fbConfig.getString(fbKey);
        String admobId = fbConfig.getString(admobKey);
        return new AdInfo(admobId, fbId);
    }

    public static FireBaseRemoteConfigManager getInstance(String productKey, AdMapping adMapping) {
        if (instance == null) {
            instance = new FireBaseRemoteConfigManager(productKey, adMapping);
        }
        return instance;
    }

    public boolean getBoolean(String secureShield) {
        return fbConfig.getBoolean(remoteKeyNameConvention.getRemoteKeyName(secureShield));
    }

    public static final class RemoteKeyNameConvention {

        private String productCode;

        public RemoteKeyNameConvention(String productCode) {
            this.productCode = productCode;
        }

        public String getRemoteKeyName(String basename) {
            return productCode + "_" + (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR.toLowerCase() + "_") + basename;
        }

    }

}
