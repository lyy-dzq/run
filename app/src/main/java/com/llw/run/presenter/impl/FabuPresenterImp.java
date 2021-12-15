package com.llw.run.presenter.impl;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.llw.run.http.BaseDataResponse;
import com.llw.run.http.NetService;
import com.llw.run.http.RetrofitServiceManager;
import com.llw.run.presenter.FabuPresenter;
import com.llw.run.view.FabuView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FabuPresenterImp implements FabuPresenter {

    private FabuView fabuView;

    public FabuPresenterImp(FabuView fabuView) {
        this.fabuView = fabuView;
    }

    @Override
    public void fabu(String uid, List<String> pic, String issue) {

        JsonArray jsonArray = new JsonArray();
        for (String image : pic) {
            jsonArray.add(image);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", uid);
        jsonObject.add("pics", jsonArray);
        jsonObject.addProperty("issue", issue);

        RetrofitServiceManager.getInstance()
                .create(NetService.class)
                .insertDynamic(jsonObject)
                .enqueue(new Callback<BaseDataResponse>() {
                    @Override
                    public void onResponse(Call<BaseDataResponse> call, Response<BaseDataResponse> response) {
                        Log.e("insertDynamic", "onResponse");

                        if (response.body().getCode() == 200) {
                            fabuView.onFabuResponse(true);

                        } else {
                            fabuView.onFabuResponse(false);
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseDataResponse> call, Throwable t) {
                        Log.e("insertDynamic", "onFailure");
                        fabuView.onFabuResponse(false);

                    }
                });
    }
}
