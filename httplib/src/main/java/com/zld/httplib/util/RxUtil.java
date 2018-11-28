package com.zld.httplib.util;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lingdong on 2018/2/2.
 */

public class RxUtil {

    /**
     * 处理线程切换
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//    /**
//     * 处理返回的基本的结果
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Function<BaseResult<T>, T> handleBaseResult() {
//        return new Function<BaseResult<T>, T>() {
//            @Override
//            public T apply(BaseResult<T> result) throws Exception {
//                if (result == null) {
//                    throw new ServerException(-1, "服务器错误");
//                } else {
//                    if (result.status != 1) {
//                        throw new ServerException(result.status, result.getMessage());
//                    }
//                }
//                return result.getData();
//            }
//        };
//    }
//
//    /**
//     * 处理返回的基本的结果
//     *
//     * @param
//     * @return
//     */
//    public static Function<BaseResult, EmptyResultBean> handleBaseResult2() {
//        return new Function<BaseResult, EmptyResultBean>() {
//            @Override
//            public EmptyResultBean apply(BaseResult baseResult) throws Exception {
//                EmptyResultBean bean = new EmptyResultBean();
//                bean.setStatus(baseResult.status);
//                bean.setMessage(baseResult.getMessage());
//                return bean;
//            }
//        };
//    }
//
//    public static <T> Function<XiaoYangResult<T>, T> handleXiaoYangResult() {
//        return new Function<XiaoYangResult<T>, T>() {
//            @Override
//            public T apply(XiaoYangResult<T> result) throws Exception {
//                if (!result.getSuccess()) {
//                    throw new ServerException(100, result.getMsg());
//                }
//                return result.getData();
//            }
//        };
//    }
}
