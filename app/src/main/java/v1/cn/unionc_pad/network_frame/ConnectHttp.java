package v1.cn.unionc_pad.network_frame;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.network_frame.core.RetrofitConfigure;

/**
 * Created by qy on 2018/1/31.
 */

public class ConnectHttp<T> {

    //创建 Unionc API 接口的一个实例。
    public static UnionAPI getUnionAPI() {
        return RetrofitConfigure.unioncRetrofit.create(UnionAPI.class);
    }
    public static UnionAPI getUnionappAPI() {
        return RetrofitConfigure.unioncRetrofitapp.create(UnionAPI.class);
    }


    /**
     * 连接网络
     * @param observable
     * @param baseObserver
     */
    public static <T> void connect(Observable<T> observable, BaseObserver<T> baseObserver) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((baseObserver));
    }
}
