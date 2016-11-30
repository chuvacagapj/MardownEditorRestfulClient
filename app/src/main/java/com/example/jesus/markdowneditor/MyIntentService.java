package com.example.jesus.markdowneditor;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.example.jesus.markdowneditor.ServiceActions.ServiceAction;
import com.example.jesus.markdowneditor.ServiceActions.ServiceActionFactory;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private ServiceActionFactory factory;

    public MyIntentService() {
        super("MyIntentService");
        factory = new ServiceActionFactory();
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     * @param action is an constant of ServiceAction
     */
    public static void startAction(Context context, int action) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.putExtra("action", action);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final int actionId = intent.getIntExtra("action", 0);
            System.out.println("onHandleIntent " + actionId);
            ServiceAction action = factory.getServiceAction(actionId);
            action.actionExecute();
            sendMessege();
        }
    }

    public void sendMessege(){
        Intent intent = new Intent("Dataset-changed");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
