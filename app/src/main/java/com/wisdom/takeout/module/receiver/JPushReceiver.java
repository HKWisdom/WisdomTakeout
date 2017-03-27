package com.wisdom.takeout.module.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wisdom.takeout.utils.OrderObservable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by HKWisdom on 2017/3/24.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

            String mString = bundle.getString(JPushInterface.EXTRA_MESSAGE);

            //            Log.d(TAG, "收到了自定义消息。消息内容是：" + mString);

            String extraMessage = bundle.getString(JPushInterface.EXTRA_EXTRA);
            processData(extraMessage);
            Log.d(TAG, "onReceive: " + extraMessage);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

            Log.d(TAG, "用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            //            Intent i = new Intent(context, TestActivity.class);  //自定义打开的界面
            //            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //            context.startActivity(i);

        } else {

            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void processData(String extraMessage) {

        Map<String, String> orderMap = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(extraMessage);
            String orderId = jsonObject.getString("orderId");
            String type = jsonObject.getString("type");

            orderMap.put("orderId", orderId);
            orderMap.put("type", type);

            OrderObservable.getInstance().newExtraMsg(orderMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
