package com.zld.httplib.callback;

/**
 * Created by lingdong on 2018/11/27.
 */
public interface IRequestCallback {

    void onPreRequest();

    void onAfterRequest();
}
