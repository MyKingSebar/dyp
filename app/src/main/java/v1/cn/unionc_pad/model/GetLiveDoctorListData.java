package v1.cn.unionc_pad.model;

import java.io.Serializable;
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
     "code": "4000",
     "data": {
     "videoDoctors": [
     {
     "DoctImagePath": ": "http://192.168.21.93:8081/image/webServer/compress/79/7/0/98e3a481-7255-46a9-8f17-2ea4dc77ed80_20001.jpg",
     ",
     "DoctId": "34",
     "DoctUserId": "85",
     "EvaluateCount": "0",
     "ProfessName": "知名专家",
     "Telphone": "13502212321",
     "DepartName": "儿科",
     "AtteCount": "0",
     "Identifier": "91c6cfe42e7a44dd97be1654bed5540b",
     "Major": "大幅度上冻豆腐受到",
     "OnlineState": "2",
     "ClinicName": "望京新世界",
     "ClinicId": "779417",
     "ServicePrice": "0.00",
     "DoctName": "王天明",
     "Distance": "2.52"
     }
     ],
     "videoDoctorsCount": 1
     },
     "message": "成功"
     }
     */
    public class DataData {
        private List<DataDataData> videoDoctors;

        public List<DataDataData> getVideoDoctors() {
            return videoDoctors;
        }

        public void setVideoDoctors(List<DataDataData> videoDoctors) {
            this.videoDoctors = videoDoctors;
        }

        public class DataDataData implements Serializable {
            private String DoctId;
            private String DoctImagePath;
            private String EvaluateCount;
            private String ProfessName;
            private String Telphone;
            private String DepartName;
            private String AtteCount;
            private String Major;
            private String OnlineState;
            private String ClinicName;
            private String ClinicId;
            private String DoctName;
            private String Identifier;
            private String Distance;
            private String ServicePrice;
            private String Remarks;

            public String getRemarks() {
                return Remarks;
            }

            public void setRemarks(String remarks) {
                Remarks = remarks;
            }

            public String getDistance() {
                return Distance;
            }

            public void setDistance(String distance) {
                Distance = distance;
            }

            public String getServicePrice() {
                return ServicePrice;
            }

            public void setServicePrice(String servicePrice) {
                ServicePrice = servicePrice;
            }

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String identifier) {
                Identifier = identifier;
            }

            public String getDoctImagePath() {
                return DoctImagePath;
            }

            public void setDoctImagePath(String doctImagePath) {
                DoctImagePath = doctImagePath;
            }

            public String getEvaluateCount() {
                return EvaluateCount;
            }

            public void setEvaluateCount(String evaluateCount) {
                EvaluateCount = evaluateCount;
            }

            public String getProfessName() {
                return ProfessName;
            }

            public void setProfessName(String professName) {
                ProfessName = professName;
            }

            public String getAtteCount() {
                return AtteCount;
            }

            public void setAtteCount(String atteCount) {
                AtteCount = atteCount;
            }

            public String getMajor() {
                return Major;
            }

            public void setMajor(String major) {
                Major = major;
            }

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

        }
    }
}
