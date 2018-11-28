package com.zld.httplib.callback;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/08/07
 *     @desc   :
 * </pre>
 */
public interface IHttpCallback {

    void success(String url, String res);

    void error(String url, int code, String msg);
}
