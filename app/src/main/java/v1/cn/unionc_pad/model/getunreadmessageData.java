package v1.cn.unionc_pad.model;

/**
 * Created by qy on 2018/3/8.
 */

public class getunreadmessageData extends BaseData {


    /**
     * {"code":"4000","data":{"nurseMessage":{"unAcceptCount":0,"nurseMessageTotalCount":4,"unEvaluateCount":2,"acceptCount":2}},"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private DataDataData nurseMessage;

        public DataDataData getNurseMessage() {
            return nurseMessage;
        }

        public void setNurseMessage(DataDataData nurseMessage) {
            this.nurseMessage = nurseMessage;
        }

        public class DataDataData {
            private String unAcceptCount;
            private String nurseMessageTotalCount;
            private String unEvaluateCount;
            private String acceptCount;

            public String getUnAcceptCount() {
                return unAcceptCount;
            }

            public void setUnAcceptCount(String unAcceptCount) {
                this.unAcceptCount = unAcceptCount;
            }

            public String getNurseMessageTotalCount() {
                return nurseMessageTotalCount;
            }

            public void setNurseMessageTotalCount(String nurseMessageTotalCount) {
                this.nurseMessageTotalCount = nurseMessageTotalCount;
            }

            public String getUnEvaluateCount() {
                return unEvaluateCount;
            }

            public void setUnEvaluateCount(String unEvaluateCount) {
                this.unEvaluateCount = unEvaluateCount;
            }

            public String getAcceptCount() {
                return acceptCount;
            }

            public void setAcceptCount(String acceptCount) {
                this.acceptCount = acceptCount;
            }
        }
    }
}
