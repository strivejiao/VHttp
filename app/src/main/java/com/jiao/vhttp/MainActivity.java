package com.jiao.vhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiao.vhttp.http.VHttp;
import com.jiao.vhttp.http.callback.ACallback;
import com.jiao.vhttp.http.interceptor.HttpLogInterceptor;
import com.jiao.vhttp.http.request.PostRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNet();
        findViewById(R.id.bt_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    private void initNet() {
        Map<String, String> map = new HashMap<>();
        map.put("mKey", "mobile");
        map.put("version", "V1.0%281%29");
        map.put("imei", "351670062419259");
        map.put("clientModel", "%E4%B8%89%E6%98%9F+GT-I9507V");
        map.put("userName", "pangkai");
        map.put("nettype", "NETWORK_WIFI");
        map.put("fingerDevice", "MIS-%2BjmMpdU%2FJTVBwqxk9boIcOuGWBBtV4VOJjfvo9Wf2EEK0NEOSpBEGcvzB4Y3lUsU");
        map.put("userKey", "");
        map.put("userTag", "201510271916301ab8ce20");
        VHttp.init(this);
        VHttp.CONFIG()
                .baseUrl("http://192.168.187.189:8090")
                .httpHeaders(map)
                .interceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY));
    }

    private void getData() {
//        VHttp.BASE(new ApiMyPostRequest("/crmfangapp/message/sysmsg")
//                .tag("getMessages")
//                .addForm("json", "{\"cityId\":\"2\"}"))
//                .request(new ACallback<MessageModel>() {
//                             @Override
//                             public void onSuccess(MessageModel data) {
//                                 if (data != null) {
//
//                                 }
//                             }
//
//                             @Override
//                             public void onFail(int errCode, String errMsg) {
//                                 Log.d("11111", errMsg);
//                             }
//                         }
//                );
        VHttp.BASE(new PostRequest("/crmfangapp/message/sysmsg")
                .tag("getMessages")
                .addForm("json", "{\"cityId\":\"2\"}"))
                .request(new ACallback<String>() {
                             @Override
                             public void onSuccess(String data) {
                                 if (data != null) {

                                 }
                             }

                             @Override
                             public void onFail(int errCode, String errMsg) {
                                 Log.d("11111", errMsg);
                             }
                         }
                );
    }
}
