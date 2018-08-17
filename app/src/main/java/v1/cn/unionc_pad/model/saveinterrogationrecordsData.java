package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class saveinterrogationrecordsData extends BaseData {


    /**
     * 增加返回参数recordId
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
       private  String recordId;

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }
    }
}
