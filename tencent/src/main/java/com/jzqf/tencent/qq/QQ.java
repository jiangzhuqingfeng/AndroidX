package com.jzqf.tencent.qq;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.jzqf.tencent.IView;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

public class QQ {
    private Tencent mTencent;
    public static QQ mInstance;
    private IView mView;

    public static QQ getInstance() {
        if (mInstance == null) {
            synchronized (QQ.class) {
                if (mInstance == null) {
                    mInstance = new QQ();
                }
            }
        }
        return mInstance;
    }

    public Tencent getTencent() {
        return mTencent;
    }

    public QQ register(String appId, Application application, IView view) {
        this.mView = view;
        mTencent = Tencent.createInstance(appId, application);
        return this;
    }

    public void login(AppCompatActivity appCompatActivity) {
        if (!mTencent.isSessionValid()) {
            mTencent.login(appCompatActivity, "all", new BaseUiListener());
        }
    }

    class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            showResult("onComplete", JSON.toJSONString(o));
        }

        @Override
        public void onError(UiError e) {
            showResult("onError: code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            showResult("onCancel", "");
        }
    }

    private void showResult(String value) {

    }

    private void showResult(String onComplete, String toJSONString) {

    }

    private class BaseApiListener implements IRequestListener {
        @Override
        public void onComplete(JSONObject jsonObject) {
            showResult("IRequestListener.onComplete:", jsonObject.toString());
        }

        @Override
        public void onIOException(IOException e) {
            showResult("IRequestListener.onIOException:", e.getMessage());
        }

        @Override
        public void onMalformedURLException(MalformedURLException e) {
            showResult("IRequestListener.onMalformedURLException", e.toString());
        }

        @Override
        public void onJSONException(JSONException e) {
            showResult("IRequestListener.onJSONException:", e.getMessage());
        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e) {

        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException e) {

        }

        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {

        }

        @Override
        public void onUnknowException(Exception e) {

        }
    }
}
