package com.example.jesus.markdowneditor.ServiceActions;

import android.content.Intent;

/**
 * Created by jesus on 29/11/16.
 */

public interface ServiceAction{
    public int READ = 0;
    public int UPSERT = 1;
    public int DELETE = 2;
    public void actionExecute();
}
