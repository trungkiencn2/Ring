package com.skyfree.ring.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.skyfree.ring.R;

import io.realm.Realm;

/**
 * Created by KienBeu on 4/2/2018.
 */

public class FragmentMoreApp extends Fragment {

    private WebView mWebview;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_more_app, container, false);
        mWebview = (WebView) mView.findViewById(R.id.webview);
        mWebview.loadUrl("https://play.google.com/store/apps/developer?id=Sky+Free+App");
        return mView;
    }
}
