package v1.cn.unionc_pad.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class YuYueOkData extends BaseData {


    /**
     * {"code":"4000","data":{"orderId":"47","price":"101.01","serviceName":"静脉输液","serviceTime":"2018-08-17 18:00:00","serviceUserName":"孟宪良"},"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData implements Serializable {
       private String orderId;
       private String price;
       private String serviceName;
       private String serviceTime;
       private String serviceUserName;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getServiceUserName() {
            return serviceUserName;
        }

        public void setServiceUserName(String serviceUserName) {
            this.serviceUserName = serviceUserName;
        }
    }
}
