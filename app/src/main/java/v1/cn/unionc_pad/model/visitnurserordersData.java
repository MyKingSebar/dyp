package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class visitnurserordersData extends BaseData {


    /**
     * {
     "code": "4000",
     "data": {
     "ordersCount": 1,
     "orders": [
     {
     "DoctId": "62",
     "Price": "0.00",
     "ServiceName": "静脉输液",
     "DiseaseName": "感冒",
     "OrderId": "32",
     "DoctName": "999",
     "Status":"1"
     }
     ]
     },
     "message": "成功"
     }
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<DataDataData> orders;
        private String ordersCount;

        public String getOrdersCount() {
            return ordersCount;
        }

        public void setOrdersCount(String ordersCount) {
            this.ordersCount = ordersCount;
        }

        public List<DataDataData> getOrders() {
            return orders;
        }

        public void setOrders(List<DataDataData> orders) {
            this.orders = orders;
        }



        public static class DataDataData {
            private String DoctId;
            private String Price;
            private String ServiceName;
            private String DiseaseName;
            private String OrderId;
            private String DoctName;
            private String Status;

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String price) {
                Price = price;
            }

            public String getServiceName() {
                return ServiceName;
            }

            public void setServiceName(String serviceName) {
                ServiceName = serviceName;
            }

            public String getDiseaseName() {
                return DiseaseName;
            }

            public void setDiseaseName(String diseaseName) {
                DiseaseName = diseaseName;
            }

            public String getOrderId() {
                return OrderId;
            }

            public void setOrderId(String orderId) {
                OrderId = orderId;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String status) {
                Status = status;
            }
        }
    }
}
