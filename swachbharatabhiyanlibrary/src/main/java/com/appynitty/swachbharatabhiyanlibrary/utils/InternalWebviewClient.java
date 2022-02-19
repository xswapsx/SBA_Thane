package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.riaylibrary.custom_component.MyProgressDialog;

/**
 * Created by Ayan Dey on 19/10/18.
 */
public class InternalWebviewClient extends WebViewClient {

    private static final String TAG = "InternalWebviewClient";
    private MyProgressDialog progressDialog = null;
    private final Context mContext;
    private final Boolean mShowProgress;

    public InternalWebviewClient(Context context, Boolean showProgress){
        this.mContext = context;
        this.progressDialog = new MyProgressDialog(context, R.drawable.progress_bar, false);
        this.mShowProgress = showProgress;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(progressDialog != null && mShowProgress){
            progressDialog.show();
        }
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.hide();
            progressDialog = null;
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.hide();
            progressDialog = null;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url.contains("tel:")){

            mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            return true;

        }else if(url.contains("mailto:")){

            mContext.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
            return true;

        }else if(url.contains("market:") || url.contains("//play.google.com")){

            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            return true;

        }else{
            return false;
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        String stringUrl = request.getUrl().toString();

        if(stringUrl.contains("tel:")){

            mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(stringUrl)));
            return true;

        }else if(stringUrl.contains("mailto:")){

            mContext.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(stringUrl)));
            return true;

        }else if(stringUrl.contains("market:") || stringUrl.contains("//play.google.com")){

            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stringUrl)));
            return true;

        }else{
            return false;
        }
    }
}
