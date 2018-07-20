package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class HasUnfinishedAppointData extends BaseData {

//0-无，1-有
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public class DataData{
    private String hasUnfinishedAppoint;

        public String getHasUnfinishedAppoint() {
            return hasUnfinishedAppoint;
        }

        public void setHasUnfinishedAppoint(String hasUnfinishedAppoint) {
            this.hasUnfinishedAppoint = hasUnfinishedAppoint;
        }
    }
}
