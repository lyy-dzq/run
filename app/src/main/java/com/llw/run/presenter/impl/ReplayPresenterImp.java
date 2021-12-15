package com.llw.run.presenter.impl;

import android.util.Log;

import com.google.gson.JsonObject;
import com.llw.run.http.BaseDataResponse;
import com.llw.run.http.NetService;
import com.llw.run.http.RetrofitServiceManager;
import com.llw.run.presenter.ReplayPresenter;
import com.llw.run.view.ReplayView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplayPresenterImp implements ReplayPresenter {

    private ReplayView replayView;

    public ReplayPresenterImp(ReplayView replayView) {
        this.replayView = replayView;
    }

    @Override
    public void replay(JsonObject jsonObject) {
        RetrofitServiceManager.getInstance()
                .create(NetService.class)
                .replay(jsonObject)
                .enqueue(new Callback<BaseDataResponse>() {
                    @Override
                    public void onResponse(Call<BaseDataResponse> call, Response<BaseDataResponse> response) {
                        Log.e("replay", "onResponse");


                        if (response.body() == null) {
                            replayView.onReplayResponse(false);
                        } else {
                            if (response.body().getCode() == 200) {
                                replayView.onReplayResponse(true);

                            } else {
                                replayView.onReplayResponse(false);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<BaseDataResponse> call, Throwable t) {
                        Log.e("replay", "onFailure");
                        replayView.onReplayResponse(false);

                    }
                });
    }
}
