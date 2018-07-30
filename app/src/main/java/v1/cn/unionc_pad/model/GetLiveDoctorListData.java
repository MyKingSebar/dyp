package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/26.
 */

public class GetLiveDoctorListData extends BaseData {

    //0-无，1-有
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    /**
     * {
     * "code": "4000",
     * "data": {
     * "videoDoctors": [
     * {
     * "DoctId": "60",
     * "OnlineState": "0",
     * "ClinicImagePath": "http://192.168.21.93:8081/image\\webServer\\compress\\101\\5\\6\\4fa93541-6011-466b-a8b0-691fa8e9e92c_8c46b0f9e49d36a994ce7a827f41adcc.jpg",
     * "ClinicName": "孟的测试医院",
     * "ClinicId": "779427",
     * "DoctName": "孟医生2",
     * "Telphone": "15615622222",
     * "DepartName": "儿科"
     * },
     * {
     * "DoctId": "58",
     * "OnlineState": "0",
     * "ClinicImagePath": "http://192.168.21.93:8081/image/webServer/compress/101/10/10/107497db-6193-466a-aa2d-3c21ee08701c_641947948088577221.jpg",
     * "ClinicName": "孟的测试医院",
     * "ClinicId": "779427",
     * "DoctName": "孟医生",
     * "Telphone": "15615611111",
     * "DepartName": "儿科"
     * }
     * ]
     * },
     * "message": "成功"
     * }
     */
    public class DataData {
        private List<DataDataData> videoDoctors;

        public List<DataDataData> getVideoDoctors() {
            return videoDoctors;
        }

        public void setVideoDoctors(List<DataDataData> videoDoctors) {
            this.videoDoctors = videoDoctors;
        }

        public class DataDataData {
            private String DoctId;
            private String OnlineState;
            private String ClinicImagePath;
            private String ClinicName;
            private String ClinicId;
            private String DoctName;
            private String Telphone;

            public String getDoctId() {
                return DoctId;
            }

            public void setDoctId(String doctId) {
                DoctId = doctId;
            }

            public String getOnlineState() {
                return OnlineState;
            }

            public void setOnlineState(String onlineState) {
                OnlineState = onlineState;
            }

            public String getClinicImagePath() {
                return ClinicImagePath;
            }

            public void setClinicImagePath(String clinicImagePath) {
                ClinicImagePath = clinicImagePath;
            }

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getClinicId() {
                return ClinicId;
            }

            public void setClinicId(String clinicId) {
                ClinicId = clinicId;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }

            public String getTelphone() {
                return Telphone;
            }

            public void setTelphone(String telphone) {
                Telphone = telphone;
            }

            public String getDepartName() {
                return DepartName;
            }

            public void setDepartName(String departName) {
                DepartName = departName;
            }

            private String DepartName;
        }
    }
}
