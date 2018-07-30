package v1.cn.unionc_pad.ui.health;

public interface IRespCallBack {

    public final int DO=1;

    public boolean callback(int callCode, Object... param);
}
