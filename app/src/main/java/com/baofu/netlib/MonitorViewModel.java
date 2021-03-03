package com.baofu.netlib;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.baofu.netlib.bean.ConfigModelBean;
import com.example.netlibrary.BPRequest;
import com.example.netlibrary.BPRequestBody;
import com.example.netlibrary.BaseViewModel;
import com.example.netlibrary.BPListener;

import java.util.HashMap;
import java.util.Map;

public class MonitorViewModel extends BaseViewModel {
    public final String COOKIE = "jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzNhYzYxOGNmOWYzNDNkOGZkMDMyMzkiLCJpYXQiOjE2MDAzOTkzMjgsImV4cCI6MTYwMTYwODkyOH0.ImOPavAeqrTKFT-WOBmPefoKVV3c29DV1v5eZylNvXc";


    public MonitorViewModel(@NonNull Application application) {
        super(application);
    }


    public void request() {
        Map<String, String> param = new HashMap<>();
        param.put("code", "18933333333");
        param.put("password", "123456");

        BPRequestBody build = new BPRequestBody.Builder()
                .setMethod(BPRequest.Method.GET)
                .setUrl("https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website")
                .setParams(param)
                .setRequestTag(mRequestTag)
                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("a", response);
                        Log.e("time",System.currentTimeMillis()+"");
                        Toast.makeText(BaseApplication.getInstance(),"asdf",Toast.LENGTH_SHORT).show();
                    }
                })

//                .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
//                    @Override
//                    public void onResponse(ConfigModelBean response) {
//                        Toast.makeText(BaseApplication.getInstance(), response.getUpdated_at(), Toast.LENGTH_SHORT).show();
//                    }
//                })
                .build();


        mBaseModel.request(build);
//
    }

//    public void requestStringPost() {
//        String url ="http://103.4.29.27:8080/1/api/oauth/account_token";
//        Map<String,String> param=new HashMap<>();
//        param.put("code","18933333333");
//        param.put("password","123456");
//        mBaseModel.requestString(Request.Method.POST, url, null, param, false, mRequestTag, new BPListener.OnResponseStrListener() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("a",response);
//            }
//
//            @Override
//            public void onCache(String response) {
//
//            }
//
//            @Override
//            public void onNotModify() {
//
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//
//            }
//        });
//    }
//
//    public void requestGson(){
//        mBaseModel.requestGson(BPRequest.Method.GET, "http://mm.leal.wang/rate/market/api", null, null, NationaDebtBean.class, false, mRequestTag,new BPListener.OnResponseListener<NationaDebtBean>() {
//            @Override
//            public void onResponse(NationaDebtBean response) {
//                Log.e("a",response.data);
//            }
//
//            @Override
//            public void onCache(NationaDebtBean response) {
//
//            }
//
//            @Override
//            public void onNotModify() {
//
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//
//            }
//        });
//    }
//    public void requestGsonPOST(){
//        String url ="http://103.4.29.27:8080/1/api/oauth/account_token";
//        Map<String,String> param=new HashMap<>();
//        param.put("code","18933333333");
//        param.put("password","123456");
//        mBaseModel.requestGson(BPRequest.Method.POST, url, null, param, AccountTokenBean.class, false, mRequestTag, new BPListener.OnResponseListener<AccountTokenBean>() {
//            @Override
//            public void onResponse(AccountTokenBean response) {
//                Log.e("a","token:"+response.data.token);
//
//            }
//
//            @Override
//            public void onCache(AccountTokenBean response) {
//
//            }
//
//            @Override
//            public void onNotModify() {
//
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//
//            }
//        });
//    }
//
//    /**
//     * 获取所有的便宜组合
//     */
//    public void requestCheap(final String date,String paramMc,String paramPe,String paramPb,String paramDyr) {
//
//        JSONObject paramJsonObject = null;
//        try {
//            if (TextUtils.isEmpty(paramPe)) {
//                paramPe="10";
//            }
//            if (TextUtils.isEmpty(paramPb)) {
//                paramPb="1.5";
//            }
//            if (TextUtils.isEmpty(paramDyr)) {
//                paramDyr="0.03";
//            }
//
//
//            paramJsonObject = new JSONObject();
//            paramJsonObject.put("sortName", "priceMetrics.latest.pm.pb_wo_gw");
//            paramJsonObject.put("sortOrder", "desc");
//            paramJsonObject.put("pageIndex", "0");
//            paramJsonObject.put("pageSize", "300");
//            paramJsonObject.put("areaCode", "cn");
//
//            JSONObject range = new JSONObject();
//            range.put("market", "a");
//            paramJsonObject.put("ranges", range);
//
//
//            JSONArray filterListJa = new JSONArray();
//            paramJsonObject.put("filterList", filterListJa);
//            //pe-ttm扣非
//            JSONObject pe = new JSONObject();
//            pe.put("id", "pm.d_pe_ttm");
//            pe.put("min", 0);
//            pe.put("max", Integer.parseInt(paramPe));
//            if (!TextUtils.isEmpty(date)) {
//                pe.put("date", date);
//            }
//            filterListJa.put(pe);
//            //pb不含商誉
//            JSONObject pb = new JSONObject();
//            pb.put("id", "pm.pb_wo_gw");
//            pb.put("min", 0);
//            pb.put("max", Double.parseDouble(paramPb));
//            if (!TextUtils.isEmpty(date)) {
//                pb.put("date", date);
//            }
//            filterListJa.put(pb);
//            //股息率
//            JSONObject dyr = new JSONObject();
//            dyr.put("id", "pm.dyr");
//            dyr.put("min", Double.parseDouble(paramDyr));
//            if (!TextUtils.isEmpty(date)) {
//                dyr.put("date", date);
//            }
//            filterListJa.put(dyr);
//            //股价
//            JSONObject sp = new JSONObject();
//            sp.put("id", "pm.sp");
//            if (!TextUtils.isEmpty(date)) {
//                sp.put("date", date);
//            }
//            filterListJa.put(sp);
//            //pb不含商誉分位点10年
//            JSONObject pbpos = new JSONObject();
//            pbpos.put("id", "pm.pb_wo_gw_pos10");
////            pbpos.put("max", 0.2);
//            if (!TextUtils.isEmpty(date)) {
//                pbpos.put("date", date);
//            }
//            filterListJa.put(pbpos);
//
//            //市值
//            if(!TextUtils.isEmpty(paramMc)){
//                JSONObject mc = new JSONObject();
//                mc.put("id", "pm.mc");
//                //单位元
//                mc.put("min", Long.parseLong(paramMc)*100000000);
//                if (!TextUtils.isEmpty(date)) {
//                    mc.put("date", date);
//                }
//                filterListJa.put(mc);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        Map<String, String> header = new HashMap<>();
////        header.put("content-type", "application/json;charset=UTF-8");
//        header.put("cookie", COOKIE);
//        String url = "https://www.lixinger.com/api/analyt/screener/stock";
//
//
//        mBaseModel.requestJsonRequest(com.android.volley.Request.Method.POST, url, header, paramJsonObject,false,mRequestTag, new BPListener.OnResponseStrListener() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.e("tag",response);
//            }
//
//            @Override
//            public void onCache(String response) {
//
//            }
//
//            @Override
//            public void onNotModify() {
//
//            }
//
//            @Override
//            public void onErrorResponse(String error) {
//
//            }
//        });
//
//
//    }
//
//
//    /**
//     * 获取股价
//     */
//    public void requestSp(final String start, String end, String stockId) {
//
//        JSONObject paramJsonObject = null;
//        try {
//            paramJsonObject = new JSONObject();
//
//            JSONArray ids = new JSONArray();
//            paramJsonObject.put("stockIds", ids);
//            ids.put(Integer.parseInt(stockId));
//
//            paramJsonObject.put("dateFlag", "day");
//            paramJsonObject.put("granularity", "fs");
//
//            JSONArray filters = new JSONArray();
//            filters.put("fc_rights");
//            paramJsonObject.put("metricNames", filters);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////        {
////            "stockIds": [603288],
////            "dateFlag": "day",
////                "granularity": "fs",
////                "metricNames": ["d_pe_ttm", "lxr_fc_rights", "fc_rights", "bc_rights", "sp", "mc", "cmc"]
////        }
//
//        Map<String, String> header = new HashMap<>();
////        header.put("content-type", "application/json;charset=UTF-8");
//        header.put("cookie", COOKIE);
//        String url = "https://www.lixinger.com/api/analyt/company/price-metrics/load";
//
//        mBaseModel.requestJsonRequest(Request.Method.POST, url, header, paramJsonObject, false, mRequestTag,new BPListener.OnResponseStrListener() {
//            @Override
//            public void onResponse(String s) {
//                Log.e("a",s);
//
//            }
//
//            @Override
//            public void onCache(String s) {
//
//            }
//
//            @Override
//            public void onNotModify() {
//
//            }
//
//            @Override
//            public void onErrorResponse(String volleyError) {
//                Log.e("FlashBackViewModel", volleyError + "");
//            }
//        });
//    }


}