package com.jzqf.tencent.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信SDK
 * 2020/1/3 9:22
 *
 * @author LiuWeiHao
 * Email 872235631@qq.com
 */
public class WeiXin {
    private static WeiXin mInstance;

    public static WeiXin getInstance() {
        if (mInstance == null) {
            synchronized (WeiXin.class) {
                if (mInstance == null) {
                    mInstance = new WeiXin();
                }
            }
        }
        return mInstance;
    }


    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    public IWXAPI getApi() {
        return api;
    }

    public void registerToWx(final Context context, final String appId) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, appId, true);
        // 将应用的appId注册到微信
        api.registerApp(appId);
        //建议动态监听微信启动广播进行注册到微信
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(appId);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }

    /**
     * 第三方 app 主动发送消息给微信，发送完成之后会切回到第三方 app 界面。
     */
    public void sendRequest() {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = "需要分享的内容XXXXXXXX";
//用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = "WXMediaMessage文本";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());  //transaction字段用与唯一标示一个请求
        req.message = msg;
        //TODO 这是啥
        req.scene = 1;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    /**
     * 微信向第三方 app 请求数据，第三方 app 回应数据之后会切回到微信界面。
     *
     * @param appCompatActivity UI界面
     */
    public void sendResponse(AppCompatActivity appCompatActivity) {
        // 初始化一个 WXTextObject 对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = "请求文本";
        // 用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(textObj);
        msg.description = "WXMediaMessage文本内容";
        // 构造一个Resp
        GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
        // 将req的transaction设置到resp对象中，其中bundle为微信传递过来的Intent所带的内容，通过getExtras()方法获取
        resp.transaction = new GetMessageFromWX.Req(appCompatActivity.getIntent().getExtras()).transaction;
        resp.message = msg;
        //调用api接口，发送数据到微信
        api.sendResp(resp);
    }

    public void loginWeiXin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }
}
