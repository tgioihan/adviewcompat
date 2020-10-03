package com.ads.adviewcompat;

import com.google.android.gms.ads.AdRequest;

import java.util.Arrays;

public class TestDeviceAdmob {
    public static AdRequest getRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice("D5005A83871F3B4696894927246FF9AD");
        builder.addTestDevice("3256CD20E2F3177A4F71124FD709C3FF");
        return  builder.build();
    }
}
