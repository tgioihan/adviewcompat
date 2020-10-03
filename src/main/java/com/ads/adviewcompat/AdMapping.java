package com.ads.adviewcompat;

import java.util.Map;

public abstract class AdMapping {


    public abstract void configDefaultRemoteConfig(Map<String, Object> dfConfigs, FireBaseRemoteConfigManager.RemoteKeyNameConvention remoteKeyNameConvention);
}
