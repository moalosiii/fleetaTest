package com.example.fleetatest;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class ApplicationClass extends Application {
    public static final String APPLICATION_ID = "E79D0792-0F95-E9CF-FFC6-BEE9E583EF00";
    public static final String API_KEY = "8E2D807F-CEDE-4B0D-A825-F0A4892B0B2D";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser User;
    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );

    }
}
