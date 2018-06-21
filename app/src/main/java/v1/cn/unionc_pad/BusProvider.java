package v1.cn.unionc_pad;

import com.squareup.otto.Bus;

/**
 * Created by qy on 2018/2/27.
 * otto
 * 为了避免重复创建Bus对象
 */

public class BusProvider {

    private volatile static Bus bus = null;

    private BusProvider() {
    }

    public static Bus getInstance(){
        if(null == bus){
            synchronized (BusProvider.class){
                bus = new Bus();
            }
        }
        return bus;
    }
}
