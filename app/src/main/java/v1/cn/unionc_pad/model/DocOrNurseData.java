package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class DocOrNurseData extends BaseData {

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    /**
     * {"message":"成功","data":{
     * "hasNurse":1,
     * "doctorMap":{"ClinicName":"北京市第一医院",
     *               "DoctImagePath":"http://192.168.21.93:8081/image/webServer/compress/94/15/9/996943a9-8abe-42c7-bdc5-1b378e938dce_医生头像4.jpg",
     *                "DoctName":"娜娜"},
     * "nurseMap":{"ClinicName":"北京市第一医院",
     *             "DoctImagePath":"http://192.168.21.93:8081/imagehttp://192.168.21.93:8081/image/webServer/compress/94/15/9/996943a9-8abe-42c7-bdc5-1b378e938dce_医生头像4.jpg",
     * "             DoctName":"999"},
     * "hasDoctor":1},"code":"4000"}
     */


    public class DataData {
        private datadatadata doctorMap;
        private datadatadata nurseMap;
        private String hasNurse;
        private String hasDoctor;

        public datadatadata getDoctorMap() {
            return doctorMap;
        }

        public void setDoctorMap(datadatadata doctorMap) {
            this.doctorMap = doctorMap;
        }

        public datadatadata getNurseMap() {
            return nurseMap;
        }

        public void setNurseMap(datadatadata nurseMap) {
            this.nurseMap = nurseMap;
        }

        public String getHasNurse() {
            return hasNurse;
        }

        public void setHasNurse(String hasNurse) {
            this.hasNurse = hasNurse;
        }

        public String getHasDoctor() {
            return hasDoctor;
        }

        public void setHasDoctor(String hasDoctor) {
            this.hasDoctor = hasDoctor;
        }

        public class datadatadata {
            private String ClinicName;
            private String DoctImagePath;
            private String DoctName;

            public String getClinicName() {
                return ClinicName;
            }

            public void setClinicName(String clinicName) {
                ClinicName = clinicName;
            }

            public String getDoctImagePath() {
                return DoctImagePath;
            }

            public void setDoctImagePath(String doctImagePath) {
                DoctImagePath = doctImagePath;
            }

            public String getDoctName() {
                return DoctName;
            }

            public void setDoctName(String doctName) {
                DoctName = doctName;
            }
        }
    }


}
