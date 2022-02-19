package com.appynitty.swachbharatabhiyanlibrary.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
//                AUtils.hideSnackBar();
                Toast.makeText(context, "Back Online!", Toast.LENGTH_SHORT).show();
                Log.e("NetworkChangeReceiver", "Online Connect Intenet ");
            } else {
//                AUtils.showSnackBar(findViewById(R.id.parent));
                Toast.makeText(context, "Your are offline!", Toast.LENGTH_SHORT).show();
                Log.e("NetworkChangeReceiver", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
