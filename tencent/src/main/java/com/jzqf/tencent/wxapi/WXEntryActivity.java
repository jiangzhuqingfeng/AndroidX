package com.jzqf.tencent.wxapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.jzqf.tencent.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxentry);
        WeiXin.getInstance().getApi().handleIntent(getIntent(), new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {
                Toast.makeText(getBaseContext(),JSON.toJSONString(baseReq),Toast.LENGTH_LONG).show();
                Log.i("WXEntryActivity onReq", "onReq--->"+JSON.toJSONString(baseReq));
            }

            @Override
            public void onResp(BaseResp baseResp) {
                Toast.makeText(getBaseContext(),"onResp--->"+JSON.toJSONString(baseResp),Toast.LENGTH_LONG).show();
                Log.i("WXEntryActivity onResp", JSON.toJSONString(baseResp));
            }
        });
    }
}
