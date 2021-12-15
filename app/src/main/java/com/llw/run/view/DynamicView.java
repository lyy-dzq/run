package com.llw.run.view;

import com.llw.run.http.res.FriendRes;

import java.util.List;

public interface DynamicView {
    void onDataResponse(List<FriendRes> friendResList);

    void onShow(boolean show);
}
