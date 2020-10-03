package com.ads.adviewcompat;

import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;

import java.util.Random;

final class AdProviderRateManager {
    private float randomValue;
    private AdProviderRate adProviderRate;

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FireBaseRemoteConfigManager.RemoteKeyNameConvention remoteKeyNameConvention;

    public AdProviderRateManager(FirebaseRemoteConfig firebaseRemoteConfig, FireBaseRemoteConfigManager.RemoteKeyNameConvention remoteKeyNameConvention) {
        this.firebaseRemoteConfig = firebaseRemoteConfig;
        this.remoteKeyNameConvention = remoteKeyNameConvention;
        Random random = new Random();
        randomValue = random.nextFloat();
    }

    private AdProviderRate getAdProviderRate() {
        String adProviderRateValue = firebaseRemoteConfig.getString(remoteKeyNameConvention.getRemoteKeyName(FireBaseRemoteConfigManager.AD_RATE));
        if (!TextUtils.isEmpty(adProviderRateValue)) {
            Gson gson = new Gson();
            adProviderRate = gson.fromJson(adProviderRateValue, AdProviderRate.class);
        } else {
            adProviderRate = new AdProviderRate();
            adProviderRate.setAdmobRate(1F);
            adProviderRate.setFacebookRate(0F);
        }
        return adProviderRate;
    }

    protected float getAdmobRate() {
        getAdProviderRate();
        return adProviderRate.getAdmobRate();
    }

    protected float getFacebookRate() {
        getAdProviderRate();
        return adProviderRate.getFacebookRate();
    }

    public AdProvider getAdProvider() {
        float admobRate = getAdmobRate();
        float facebookRate = getFacebookRate();

        return getAdProvider(admobRate, facebookRate, randomValue);
    }

    protected static AdProvider getAdProvider(float admobRate, float facebookRate, float randomValue) {

        if (randomValue <= admobRate) {
            return AdProvider.AdMob;
        } else if (randomValue <= (admobRate + facebookRate)) {
            return AdProvider.FbAudience;
        } else {
            return AdProvider.AdMob;
        }
//        return AdProvider.FbAudience;
    }
}
