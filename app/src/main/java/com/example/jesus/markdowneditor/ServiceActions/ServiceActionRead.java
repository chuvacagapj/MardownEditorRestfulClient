package com.example.jesus.markdowneditor.ServiceActions;

import com.example.jesus.markdowneditor.Model.MarkdownModel;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jesus on 29/11/16.
 */

public class ServiceActionRead extends ServiceActionHttp {

    public ServiceActionRead(SyncHttpClient http) {
        super(http);
    }

    @Override
    public void actionExecute(){
        super.http.get(ENDPOINT, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Type listType = new TypeToken<ArrayList<MarkdownModel>>(){}.getType();
                List lista = new Gson().fromJson(response, listType);
                MarkdownModel.store(lista);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Fallo la llamada");
                System.out.println(statusCode);
                System.out.println(new String(responseBody));
            }
        });
    }
}
