package v1.cn.unionc_pad.model;

/**
 * Created by qy on 2018/3/26.
 */

public class videohasprescriptionData extends BaseData {


    /**
     * {"code":"4000","data":{"hasPrescription":1},"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private String hasPrescription;

        public String getHasPrescription() {
            return hasPrescription;
        }

        public void setHasPrescription(String hasPrescription) {
            this.hasPrescription = hasPrescription;
        }
    }


}
