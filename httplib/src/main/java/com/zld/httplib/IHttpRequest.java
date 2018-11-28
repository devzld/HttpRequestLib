package com.zld.httplib;

import io.reactivex.disposables.CompositeDisposable;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/08/27
 *     @desc   :
 * </pre>
 */
interface IHttpRequest {
    void get(CompositeDisposable compositeDisposable);

    void post(CompositeDisposable compositeDisposable);

    void put(CompositeDisposable compositeDisposable);

    void delete(CompositeDisposable compositeDisposable);

    void postJson(CompositeDisposable compositeDisposable);

    void putJson(CompositeDisposable compositeDisposable);

    void getNoCancel();

    void putNoCancel();

    void postNoCancel();

    void deleteNoCancel();

    void postJsonNoCancel();

    void putJsonNoCancel();
}
