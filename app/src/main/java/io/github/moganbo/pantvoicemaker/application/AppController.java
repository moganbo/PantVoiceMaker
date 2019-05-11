package io.github.moganbo.pantvoicemaker.application;

import android.app.Application;
import android.content.Context;

import io.github.moganbo.pantvoicemaker.utils.LogUtil;

public class AppController extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d();
        appContext = getApplicationContext();
    }

    /**
     * 自身のコンテキスト取得
     *
     * @return 自身のコンテキスト
     */
    public static Context getAppContext() {
        return appContext;
    }
}
