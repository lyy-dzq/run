package com.llw.run.presenter.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.llw.run.http.NetService;
import com.llw.run.http.RetrofitServiceManager;
import com.llw.run.http.res.FriendRes;
import com.llw.run.presenter.DynamicPresenter;
import com.llw.run.view.DynamicView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DymicPresenterImp implements DynamicPresenter {

    private DynamicView dynamicView;

    public DymicPresenterImp(DynamicView dynamicView) {
        this.dynamicView = dynamicView;
    }

    @Override
    public void getDynamicList() {
        RetrofitServiceManager.getInstance()
                .create(NetService.class)
                .getDynamic()
                .enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        Log.e("getDynamicList", "onResponse");

                        List<FriendRes> friendResList = new ArrayList<>();

                        for (int i = 0; i < response.body().size(); i++) {
                            FriendRes friendRes = new Gson().fromJson(response.body().get(i).toString(), FriendRes.class);

                            friendResList.add(friendRes);
                        }

                        dynamicView.onDataResponse(friendResList);


                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Log.e("getDynamicList", "onFailure");


                    }
                });
    }
}
