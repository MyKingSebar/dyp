package v1.cn.unionc_pad.model;

/**
 * Created by qy on 2018/3/6.
 */

public class LoginUpdateEventData {

    private boolean update;

    public LoginUpdateEventData(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
