package com.coding.zxm.libweb.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.zxm.libweb.x5.X5WebView;
import com.tencent.smtt.sdk.WebView;
import com.zxm.utils.core.log.MLogger;

import java.util.logging.Logger;


/**
 * Created by ZhangXinmin on 2018/12/7.
 * Copyright (c) 2018 . All rights reserved.
 * <p>
 * A fragment that displays a WebView{@link X5WebView}.
 * The WebView{@link X5WebView} is automically paused or resumed when the Fragment is paused or resumed.
 * </p>
 */
public class X5WebViewFragment extends Fragment implements X5WebView.WebViewListener {

    private static final String TAG = X5WebViewFragment.class.getSimpleName();
    public static final String PARAMS_WEBVIEW_URL = "webview_url";

    private Context mContext;
    private X5WebView mWebView;
    private boolean mIsWebViewAvailable;
    private String mUrl;
    private boolean mIsPageFinished;

    public static X5WebViewFragment newInstance(@NonNull String url) {
        X5WebViewFragment fragment = new X5WebViewFragment();
        Bundle args = new Bundle();
        if (!TextUtils.isEmpty(url)) {
            args.putString(PARAMS_WEBVIEW_URL, url);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParamsAndValus();
    }

    private void initParamsAndValus() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(PARAMS_WEBVIEW_URL)) {
                mUrl = args.getString(PARAMS_WEBVIEW_URL);
            }
        }

        if (TextUtils.isEmpty(mUrl)) {
            Activity activity = getActivity();
            if (activity != null) {
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                if (fm != null) {
                    fm.popBackStack();
                }
            }
        }

        mIsPageFinished = false;

    }

    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new X5WebView(getContext());
        mWebView.setWebViewListener(this);

        mIsWebViewAvailable = true;

        return mWebView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsWebViewAvailable && mWebView != null
                && !TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView{@link X5WebView}.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView{@link X5WebView}.
     */
    public X5WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

    @Override
    public void onProgressChange(WebView view, int newProgress) {

    }

    @Override
    public void onPageFinish(WebView view) {
        mIsPageFinished = true;
        MLogger.e(TAG, "onPageFinish");

    }
}
