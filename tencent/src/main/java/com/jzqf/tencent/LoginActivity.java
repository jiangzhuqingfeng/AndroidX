package com.jzqf.tencent;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jzqf.tencent.qq.QQ;
import com.jzqf.tencent.wxapi.WeiXin;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class LoginActivity extends AppCompatActivity implements IView {
    private TextView mResultTv;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "1107785607";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mResultTv = findViewById(R.id.login_result_tv);
        WeiXin.getInstance().registerToWx(this, APP_ID);
//        QQ.getInstance().register(APP_ID,getApplication(),this);
        findViewById(R.id.login_qq_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QQ.getInstance().login(LoginActivity.this);
            }
        });
        findViewById(R.id.login_weixin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeiXin.getInstance().loginWeiXin();
            }
        });
        findViewById(R.id.login_weixinRequest_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeiXin.getInstance().sendRequest();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable disposable = rxPermissions.request(Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Toast.makeText(getBaseContext(), "已授权", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        QQ.getInstance().getTencent().onActivityResult(requestCode, resultCode, data)
    }

    @Override
    public void show(String text) {
        mResultTv.setText(text);
    }

    @Override
    public void show(String tag, String text) {
        mResultTv.setText(tag + "\n" + text);
    }
}
