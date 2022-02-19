package com.riaylibrary.custom_component;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.webkit.WebView;


/**
 * Created by Ayan Dey on 26/10/18.
 */
public class WebviewInitialize {

    private final Context mContext;
    private final WebView webView;

    public WebviewInitialize(Context mContext, WebView webView) {
        this.mContext = mContext;
        this.webView = webView;
    }

    public void InitiateDefaultWebview(String url){

        webView.setWebViewClient(new InternalWebviewClient(mContext));
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);

        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });


        webView.loadUrl(url);
    }

    public boolean webviewGoBack() {

        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }else
            return false;
    }
}


