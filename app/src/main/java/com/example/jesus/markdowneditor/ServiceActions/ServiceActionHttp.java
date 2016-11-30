package com.example.jesus.markdowneditor.ServiceActions;

import com.loopj.android.http.SyncHttpClient;

/**
 * Created by jesus on 29/11/16.
 */

public abstract class ServiceActionHttp implements ServiceAction {

    public static String ENDPOINT = "https://infinite-beyond-81397.herokuapp.com/mdown/";

    protected SyncHttpClient http;

    public ServiceActionHttp(SyncHttpClient http){
        this.http = http;
    }

}
