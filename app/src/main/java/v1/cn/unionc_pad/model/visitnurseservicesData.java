package v1.cn.unionc_pad.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class visitnurseservicesData extends BaseData {


    /**
     * {"code":"4000","data":{"
     * data":[{"ServiceName":"静脉采血","ServiceDesc":"用于常规或特殊检测、辅助诊断疾病等","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_007.png","S","ServiceId":"3"},{"ServiceName":"静脉输液","ServiceDesc":"专业护士静脉点滴","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_002.png","S","ServiceId":"2"},{"ServiceName":"留置针输液","ServiceDesc":"适用于输液疗程长，血管穿刺困难者","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_015.png","S","ServiceId":"4"},{"ServiceName":"导尿","ServiceDesc":"插尿管，尿管护理，更换尿管","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_004.png","S","ServiceId":"7"},{"ServiceName":"雾化治疗","ServiceDesc":"药物气化为雾，吸入治疗","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_018.png","S","ServiceId":"14"},{"ServiceName":"灌肠护理","ServiceDesc":"经肛门灌注液体，通便排气","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_014.png","S","ServiceId":"12"}]
     * },"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<ServiceData> data;

        public List<ServiceData> getData() {
            return data;
        }

        public void setData(List<ServiceData> data) {
            this.data = data;
        }
/**
 * "ServiceName":"静脉采血","ServiceDesc":"用于常规或特殊检测、辅助诊断疾病等","ServicePrice":"50.00","ServiceIcon":"","ServiceImage":"":"http://www.yihu365.com/images/nurseImg_007.png","ServiceId":"3"
 */
        /**
         * {"ServiceName":"静脉采血",
         * "ServiceDesc":"用于常规或特殊检测、辅助诊断疾病等",
         * "ServicePrice":"50.00",
         * "ServiceIcon":"",
         * "ServiceImage":"http://www.yihu365.com/images/nurseImg_007.png",
         * "ServiceId":"3"}
         */
        public static class ServiceData implements Serializable {
            private String ServiceName;
            private String ServiceDesc;
            private String ServicePrice;
            private String ServiceIcon;
            private String ServiceImage;
            private String ServiceId;

            public String getBuyCount() {
                return buyCount;
            }

            public void setBuyCount(String buyCount) {
                this.buyCount = buyCount;
            }

            private String buyCount;

            public String getServiceName() {
                return ServiceName;
            }

            public void setServiceName(String serviceName) {
                ServiceName = serviceName;
            }

            public String getServiceDesc() {
                return ServiceDesc;
            }

            public void setServiceDesc(String serviceDesc) {
                ServiceDesc = serviceDesc;
            }

            public String getServicePrice() {
                return ServicePrice;
            }

            public void setServicePrice(String servicePrice) {
                ServicePrice = servicePrice;
            }

            public String getServiceIcon() {
                return ServiceIcon;
            }

            public void setServiceIcon(String serviceIcon) {
                ServiceIcon = serviceIcon;
            }

            public String getServiceImage() {
                return ServiceImage;
            }

            public void setServiceImage(String serviceImage) {
                ServiceImage = serviceImage;
            }

            public String getServiceId() {
                return ServiceId;
            }

            public void setServiceId(String serviceId) {
                ServiceId = serviceId;
            }
        }
    }
}
