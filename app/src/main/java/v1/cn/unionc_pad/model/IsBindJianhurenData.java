package v1.cn.unionc_pad.model;

/**
 * Created by qy on 2018/3/8.
 */

public class IsBindJianhurenData extends BaseData {

private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    /**
     * {"code":"4000","data":{"hasGuardian":1,"guardian":[{"ElderlyUserId":"123","GuardianName":"孟宪良","GuardianUserId":"17","GuardianRoleName":"朋友","GuardianTelphone":"15033111957"}]},"message":"成功"}
     */
    public class DataData{
private String hasGuardian;
private datadatadata guardian;

        public datadatadata getGuardian() {
            return guardian;
        }

        public void setGuardian(datadatadata guardian) {
            this.guardian = guardian;
        }

        public String getHasGuardian() {
            return hasGuardian;
        }

        public void setHasGuardian(String hasGuardian) {
            this.hasGuardian = hasGuardian;
        }

        public class datadatadata{
            private String ElderlyUserId;
            private String GuardianName;
            private String GuardianUserId;
            private String GuardianRoleName;
            private String GuardianTelphone;

            public String getElderlyUserId() {
                return ElderlyUserId;
            }

            public void setElderlyUserId(String elderlyUserId) {
                ElderlyUserId = elderlyUserId;
            }

            public String getGuardianName() {
                return GuardianName;
            }

            public void setGuardianName(String guardianName) {
                GuardianName = guardianName;
            }

            public String getGuardianUserId() {
                return GuardianUserId;
            }

            public void setGuardianUserId(String guardianUserId) {
                GuardianUserId = guardianUserId;
            }

            public String getGuardianRoleName() {
                return GuardianRoleName;
            }

            public void setGuardianRoleName(String guardianRoleName) {
                GuardianRoleName = guardianRoleName;
            }

            public String getGuardianTelphone() {
                return GuardianTelphone;
            }

            public void setGuardianTelphone(String guardianTelphone) {
                GuardianTelphone = guardianTelphone;
            }
        }
    }



}
