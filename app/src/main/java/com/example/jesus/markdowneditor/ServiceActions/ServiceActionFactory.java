package com.example.jesus.markdowneditor.ServiceActions;

import com.loopj.android.http.SyncHttpClient;

import java.util.HashMap;

/**
 * Created by jesus on 29/11/16.
 */

public class ServiceActionFactory {
    HashMap<Integer, ServiceAction> map = new HashMap<>();

    public ServiceActionFactory(){
        SyncHttpClient http = new SyncHttpClient();
        map.put(ServiceAction.DELETE, new ServiceActionDelete(http));
        map.put(ServiceAction.READ, new ServiceActionRead(http));
        map.put(ServiceAction.UPSERT, new ServiceActionUpsert(http));
    }

    public ServiceAction getServiceAction(int i){
        return map.get(i);
    }
}
