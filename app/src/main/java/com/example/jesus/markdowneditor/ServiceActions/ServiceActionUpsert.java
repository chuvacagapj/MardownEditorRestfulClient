package com.example.jesus.markdowneditor.ServiceActions;


import android.content.Intent;
import android.widget.Toast;

import com.example.jesus.markdowneditor.Model.MarkdownModel;
import com.google.gson.Gson;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jesus on 29/11/16.
 */

public class ServiceActionUpsert extends ServiceActionHttp {


    public ServiceActionUpsert(SyncHttpClient http) {
        super(http);
    }

    @Override
    public void actionExecute() {
        MarkdownModel m = MarkdownModel.getToUpsert();
        String json = new Gson().toJson(m);
        RequestParams params = new RequestParams();
        params.add("json", json);
        http.post(ENDPOINT, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MarkdownModel m = new Gson().fromJson(new String(responseBody), MarkdownModel.class);
                List<MarkdownModel> lista =  new LinkedList();
                lista.add(m);
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
