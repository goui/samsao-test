package co.samsao.reporter;

import android.app.Application;
import android.content.Context;

import co.samsao.reporter.network.NetworkService;

/**
 * Custom application containing the retrofit object.
 */
public class MyApplication extends Application {

    private NetworkService mNetworkService;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public NetworkService getNetworkService() {
        if (mNetworkService == null) {
            mNetworkService = NetworkService.Factory.create();
        }
        return mNetworkService;
    }
}
