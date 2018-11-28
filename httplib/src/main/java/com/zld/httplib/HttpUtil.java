package com.zld.httplib;

import android.content.Context;

import com.zld.httplib.interfaze.IHttpLoader;
import com.zld.httplib.service.CommonService;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/08/06
 *     @desc   :
 * </pre>
 */
public class HttpUtil {

    private IHttpLoader httpLoader;
    private Context context;
    private boolean isDebug = false;

    private HttpUtil() {

    }

    public static HttpUtil getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static HttpUtil instance = new HttpUtil();
    }

    public void init(Context context, boolean isDebug,IHttpLoader httpLoader) {
        this.context = context;
        this.httpLoader = httpLoader;
        this.isDebug = isDebug;
    }

    public CommonService getService() {
        if (httpLoader != null) {
            return httpLoader.getRetrofit().create(CommonService.class);
        } else {
            throw new RuntimeException("请先调用HttpUtil初始化方法init");
        }
    }

    public boolean isDebug() {
        return isDebug;
    }

    public Context getContext() {
        if (context != null) {
            return context;
        } else {
            throw new RuntimeException("请先调用HttpUtil初始化方法init");
        }
    }
}
