package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class getuserinfoData extends BaseData {


    /**
     * {"code":"4000","data":{"WorkingHours":"工作时间：上午9:00-下午18:00","
     * ServicePhone":"010-8888888",
     * "UserName":"测试实名",
     * "HeadImage":"":"http://192.168.21.93:8080/unionWeb/image/webServer/43/10/0/f393ef11-05bb-478c-a843-cae95f087d79_1-15020711540016.jpg",
     * "I","IsCertification":"1","
     * DoctorCount":0,"
     * EvaluateCount":2,
     * "Gender":"0","
     * ClinicCount":1,
     * "Telphone":"155****3516"},"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private String WorkingHours;
        private String ServicePhone;
        private String UserName;
        private String HeadImage;
        private String IsCertification;
        private String DoctorCount;
        private String EvaluateCount;
        private String Gender;
        private String ClinicCount;
        private String Telphone;

        public String getWorkingHours() {
            return WorkingHours;
        }

        public void setWorkingHours(String workingHours) {
            WorkingHours = workingHours;
        }

        public String getServicePhone() {
            return ServicePhone;
        }

        public void setServicePhone(String servicePhone) {
            ServicePhone = servicePhone;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getHeadImage() {
            return HeadImage;
        }

        public void setHeadImage(String headImage) {
            HeadImage = headImage;
        }

        public String getIsCertification() {
            return IsCertification;
        }

        public void setIsCertification(String isCertification) {
            IsCertification = isCertification;
        }

        public String getDoctorCount() {
            return DoctorCount;
        }

        public void setDoctorCount(String doctorCount) {
            DoctorCount = doctorCount;
        }

        public String getEvaluateCount() {
            return EvaluateCount;
        }

        public void setEvaluateCount(String evaluateCount) {
            EvaluateCount = evaluateCount;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getClinicCount() {
            return ClinicCount;
        }

        public void setClinicCount(String clinicCount) {
            ClinicCount = clinicCount;
        }

        public String getTelphone() {
            return Telphone;
        }

        public void setTelphone(String telphone) {
            Telphone = telphone;
        }
    }
}
