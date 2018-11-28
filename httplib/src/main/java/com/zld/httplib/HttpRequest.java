package com.zld.httplib;

import com.zld.httplib.callback.IHttpCallback;
import com.zld.httplib.callback.IRequestCallback;
import com.zld.httplib.exception.ApiException;
import com.zld.httplib.exception.ERROR;
import com.zld.httplib.exception.ExceptionEngine;
import com.zld.httplib.util.NetworkUtil;
import com.zld.httplib.util.RxUtil;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/08/07
 *     @desc   :
 * </pre>
 */
public class HttpRequest implements IHttpRequest {

    private String url;
    private Map<String, Object> paramsMap = new HashMap<>();
    private Map<String, String> headersMap = new HashMap<>();
    private String jsonParam = "";
    private IHttpCallback httpCallback;
    private boolean isCheckNetStatus = true;
    private boolean isDebug = false;
    private String callbackUrl = "";
    private IRequestCallback requestCallback;

    public HttpRequest(@NonNull String url) {
        this.url = url;
        this.isDebug = HttpUtil.getInstance().isDebug();
    }

    public HttpRequest checkNetStatus(boolean isCheckNetStatus) {
        this.isCheckNetStatus = isCheckNetStatus;
        return this;
    }

    public HttpRequest params(@NonNull String key, Object value) {
        this.paramsMap.put(key, value);
        return this;
    }

    public HttpRequest params(@NonNull Map<String, Object> params) {
        this.paramsMap.putAll(params);
        return this;
    }

    public HttpRequest headers(@NonNull String key, String value) {
        this.headersMap.put(key, value);
        return this;
    }

    public HttpRequest headers(@NonNull Map<String, String> headers) {
        this.headersMap.putAll(headers);
        return this;
    }

    public HttpRequest jsonParam(String jsonParam) {
        this.jsonParam = jsonParam;
        return this;
    }

    public HttpRequest callback(IHttpCallback httpCallback) {
        this.httpCallback = httpCallback;
        return this;
    }

    public HttpRequest requestCallback(IRequestCallback requestCallback) {
        this.requestCallback = requestCallback;
        return this;
    }


    /**
     * get方法，可以取消请求
     *
     * @param compositeDisposable
     */
    @Override
    public void get(CompositeDisposable compositeDisposable) {
        get(compositeDisposable, true);
    }

    /**
     * post方法，可以取消请求
     *
     * @param compositeDisposable
     */
    @Override
    public void post(CompositeDisposable compositeDisposable) {
        post(compositeDisposable, true);
    }

    /**
     * put方法，可以取消请求
     *
     * @param compositeDisposable
     */
    @Override
    public void put(CompositeDisposable compositeDisposable) {
        put(compositeDisposable, true);
    }

    /**
     * delete方法，可以取消请求
     *
     * @param compositeDisposable
     */
    @Override
    public void delete(CompositeDisposable compositeDisposable) {
        delete(compositeDisposable, true);
    }

    @Override
    public void postJson(CompositeDisposable compositeDisposable) {
        postJson(compositeDisposable, true);
    }

    @Override
    public void putJson(CompositeDisposable compositeDisposable) {
        putJson(compositeDisposable, true);
    }

    /**
     * get方法，不可取消请求
     */
    @Override
    public void getNoCancel() {
        get(null, false);
    }

    /**
     * put方法，不可取消请求
     */
    @Override
    public void putNoCancel() {
        put(null, false);
    }

    /**
     * post方法，不可取消请求
     */
    @Override
    public void postNoCancel() {
        post(null, false);
    }

    /**
     * delete方法，不可取消请求
     */
    @Override
    public void deleteNoCancel() {
        delete(null, false);
    }

    /**
     * post方法，不可取消请求
     */
    @Override
    public void postJsonNoCancel() {
        postJson(null, false);
    }

    /**
     * put方法，不可取消请求
     */
    @Override
    public void putJsonNoCancel() {
        putJson(null, false);
    }

    private void get(CompositeDisposable compositeDisposable, boolean canCancel) {
        callbackUrl = isDebug ? url : "";
        if (isCheckNetStatus) {
            if (!NetworkUtil.isNetworkAvailable(HttpUtil.getInstance().getContext())) {
                httpCallback.error(callbackUrl, ERROR.NO_NETWORK_ERROR, "网络不给力，请检查网络连接");
//                Toast.makeText(HttpUtil.getInstance().getContext(), "网络不给力，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preRequestCallback();
        Disposable disposable = HttpUtil.getInstance().getService().get(url, paramsMap, headersMap)
                .compose(RxUtil.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        afterRequestCallback();
                        if (httpCallback != null) {
                            httpCallback.success(callbackUrl, s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        afterRequestCallback();
                        ApiException ae = ExceptionEngine.handleException(throwable);
                        if (HttpUtil.getInstance().isDebug()) {
                            httpCallback.error(callbackUrl, ae.code, ae.message + "\n" + ae.throwable.getMessage());
                        } else {
                            httpCallback.error(callbackUrl, ae.code, ae.message);
                        }
                    }
                });

        if (compositeDisposable != null && canCancel) {
            compositeDisposable.add(disposable);
        }
    }

    private void post(CompositeDisposable compositeDisposable, boolean canCancel) {
        callbackUrl = isDebug ? url : "";
        if (isCheckNetStatus) {
            if (!NetworkUtil.isNetworkAvailable(HttpUtil.getInstance().getContext())) {
                httpCallback.error(callbackUrl, ERROR.NO_NETWORK_ERROR, "网络不给力，请检查网络连接");
//                Toast.makeText(HttpUtil.getInstance().getContext(), "网络不给力，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preRequestCallback();
        Disposable disposable = HttpUtil.getInstance().getService().post(url, paramsMap, headersMap)
                .compose(RxUtil.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        afterRequestCallback();
                        if (httpCallback != null) {
                            httpCallback.success(callbackUrl, s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        afterRequestCallback();
                        ApiException ae = ExceptionEngine.handleException(throwable);
                        if (HttpUtil.getInstance().isDebug()) {
                            httpCallback.error(callbackUrl, ae.code, ae.message + "\n" + ae.throwable.getMessage());
                        } else {
                            httpCallback.error(callbackUrl, ae.code, ae.message);
                        }
                    }
                });
        if (compositeDisposable != null && canCancel) {
            compositeDisposable.add(disposable);
        }
    }

    private void put(CompositeDisposable compositeDisposable, boolean canCancel) {
        callbackUrl = isDebug ? url : "";
        if (isCheckNetStatus) {
            if (!NetworkUtil.isNetworkAvailable(HttpUtil.getInstance().getContext())) {
                httpCallback.error(callbackUrl, ERROR.NO_NETWORK_ERROR, "网络不给力，请检查网络连接");
//                Toast.makeText(HttpUtil.getInstance().getContext(), "网络不给力，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preRequestCallback();
        Disposable disposable = HttpUtil.getInstance().getService().put(url, paramsMap, headersMap)
                .compose(RxUtil.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        afterRequestCallback();
                        if (httpCallback != null) {
                            httpCallback.success(callbackUrl, s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        afterRequestCallback();
                        ApiException ae = ExceptionEngine.handleException(throwable);
                        if (HttpUtil.getInstance().isDebug()) {
                            httpCallback.error(callbackUrl, ae.code, ae.message + "\n" + ae.throwable.getMessage());
                        } else {
                            httpCallback.error(callbackUrl, ae.code, ae.message);
                        }
                    }
                });
        if (compositeDisposable != null && canCancel) {
            compositeDisposable.add(disposable);
        }
    }

    private void delete(CompositeDisposable compositeDisposable, boolean canCancel) {
        callbackUrl = isDebug ? url : "";
        if (isCheckNetStatus) {
            if (!NetworkUtil.isNetworkAvailable(HttpUtil.getInstance().getContext())) {
                httpCallback.error(callbackUrl, ERROR.NO_NETWORK_ERROR, "网络不给力，请检查网络连接");
//                Toast.makeText(HttpUtil.getInstance().getContext(), "网络不给力，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preRequestCallback();
        Disposable disposable = HttpUtil.getInstance().getService().delete(url, paramsMap, headersMap)
                .compose(RxUtil.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        afterRequestCallback();
                        if (httpCallback != null) {
                            httpCallback.success(callbackUrl, s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        afterRequestCallback();
                        ApiException ae = ExceptionEngine.handleException(throwable);
                        if (HttpUtil.getInstance().isDebug()) {
                            httpCallback.error(callbackUrl, ae.code, ae.message + "\n" + ae.throwable.getMessage());
                        } else {
                            httpCallback.error(callbackUrl, ae.code, ae.message);
                        }
                    }
                });

        if (compositeDisposable != null && canCancel) {
            compositeDisposable.add(disposable);
        }
    }

    private void postJson(CompositeDisposable compositeDisposable, boolean canCancel) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                jsonParam);

        callbackUrl = isDebug ? url : "";
        if (isCheckNetStatus) {
            if (!NetworkUtil.isNetworkAvailable(HttpUtil.getInstance().getContext())) {
                httpCallback.error(callbackUrl, ERROR.NO_NETWORK_ERROR, "网络不给力，请检查网络连接");
//                Toast.makeText(HttpUtil.getInstance().getContext(), "网络不给力，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preRequestCallback();
        Disposable disposable = HttpUtil.getInstance().getService().postJson(url, body, headersMap)
                .compose(RxUtil.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        afterRequestCallback();
                        if (httpCallback != null) {
                            httpCallback.success(callbackUrl, s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        afterRequestCallback();
                        ApiException ae = ExceptionEngine.handleException(throwable);
                        if (httpCallback != null) {
                            if (HttpUtil.getInstance().isDebug()) {
                                httpCallback.error(callbackUrl, ae.code, ae.message + "\n" + ae.throwable.getMessage());
                            } else {
                                httpCallback.error(callbackUrl, ae.code, ae.message);
                            }
                        }
                    }
                });
        if (compositeDisposable != null && canCancel) {
            compositeDisposable.add(disposable);
        }
    }

    private void putJson(CompositeDisposable compositeDisposable, boolean canCancel) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                jsonParam);

        callbackUrl = isDebug ? url : "";
        if (isCheckNetStatus) {
            if (!NetworkUtil.isNetworkAvailable(HttpUtil.getInstance().getContext())) {
                httpCallback.error(callbackUrl, ERROR.NO_NETWORK_ERROR, "网络不给力，请检查网络连接");
//                Toast.makeText(HttpUtil.getInstance().getContext(), "网络不给力，请检查网络连接", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        preRequestCallback();
        Disposable disposable = HttpUtil.getInstance().getService().putJson(url, body, headersMap)
                .compose(RxUtil.<String>io_main())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        afterRequestCallback();
                        if (httpCallback != null) {
                            httpCallback.success(callbackUrl, s);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        afterRequestCallback();
                        ApiException ae = ExceptionEngine.handleException(throwable);
                        if (httpCallback != null) {
                            if (HttpUtil.getInstance().isDebug()) {
                                httpCallback.error(callbackUrl, ae.code, ae.message + "\n" + ae.throwable.getMessage());
                            } else {
                                httpCallback.error(callbackUrl, ae.code, ae.message);
                            }
                        }
                    }
                });
        if (compositeDisposable != null && canCancel) {
            compositeDisposable.add(disposable);
        }
    }

    private void preRequestCallback() {
        if (requestCallback != null) {
            requestCallback.onPreRequest();
        }
    }

    private void afterRequestCallback() {
        if (requestCallback != null) {
            requestCallback.onAfterRequest();
        }
    }
}
