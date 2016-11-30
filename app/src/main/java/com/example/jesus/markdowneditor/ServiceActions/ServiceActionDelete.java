package com.example.jesus.markdowneditor.ServiceActions;

import android.widget.Toast;

import com.example.jesus.markdowneditor.Model.MarkdownModel;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jesus on 29/11/16.
 */

public class ServiceActionDelete extends ServiceActionHttp {
    public ServiceActionDelete(SyncHttpClient http) {
        super(http);
    }

    @Override
    public void actionExecute() {
        List<MarkdownModel> list = MarkdownModel.getToDelete();
        for (MarkdownModel m: list) {
            int id = m.getId().intValue();

            http.delete(ENDPOINT + id, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
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
}
