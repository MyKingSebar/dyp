package v1.cn.unionc_pad.model;

/**
 * Created by qy on 2018/3/30.
 */

public class JiGuangData {


    /**
     * pushCategory 1-活动，2-直播，3-医生，4-护士
     * startTime=2018-07-03 08:00:00,
     * httpPullUrl1=http://flv0251f594.live.126.net/live/d9333eef4fc24c43aa68c64b9e292e10.flv?netease=flv0251f594.live.126.net,
     * liveTitle=直播标题14,
     * banner=http://192.168.21.93:8081/image直播banner
     */
    private String startTime;
    private String httpPullUrl1;
    private String pushCategory;
    private String liveTitle;
    private String banner;

    private String clinicName;
    private String doctName;
    private String doctImagePath;
    private String imIdentifier;


    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDoctName() {
        return doctName;
    }

    public void setDoctName(String doctName) {
        this.doctName = doctName;
    }

    public String getDoctImagePath() {
        return doctImagePath;
    }

    public void setDoctImagePath(String doctImagePath) {
        this.doctImagePath = doctImagePath;
    }

    public String getImIdentifier() {
        return imIdentifier;
    }

    public void setImIdentifier(String imIdentifier) {
        this.imIdentifier = imIdentifier;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getHttpPullUrl1() {
        return httpPullUrl1;
    }

    public void setHttpPullUrl1(String httpPullUrl1) {
        this.httpPullUrl1 = httpPullUrl1;
    }

    public String getPushCategory() {
        return pushCategory;
    }

    public void setPushCategory(String pushCategory) {
        this.pushCategory = pushCategory;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
