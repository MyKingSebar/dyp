package v1.cn.unionc_pad.model;

/**
 * Created by qy on 2018/3/8.
 */

public class UpdateBaiduFileData extends BaseData {

private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public class DataData{
        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getAddrLongitude() {
            return addrLongitude;
        }

        public void setAddrLongitude(String addrLongitude) {
            this.addrLongitude = addrLongitude;
        }

        public String getAddrLatitude() {
            return addrLatitude;
        }

        public void setAddrLatitude(String addrLatitude) {
            this.addrLatitude = addrLatitude;
        }

        public String getIMToken() {
            return IMToken;
        }

        public void setIMToken(String IMToken) {
            this.IMToken = IMToken;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

      private String identifier;//融云id
     private String addrLongitude;
     private String addrLatitude;
     private String IMToken;//融云token
     private String addr;
     private String token;//app的token
     private String hasGuardian;//是否有监护人  0没有，1有

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        private String userId;//老人userid


        public String getHasGuardian() {
            return hasGuardian;
        }

        public void setHasGuardian(String hasGuardian) {
            this.hasGuardian = hasGuardian;
        }
    }
    /**
     * {
     "code": "4000",
     "data": {
     "identifier": "f4ebc7a8fea64bbb9ab16d4a15fbb123",
     "addrLongitude": "",
     "addrLatitude": "",
     "IMToken": "7r0i5+Hth+/HWCLb70KPiPuNOWHnRrpx7CQzHOOVIXL1mMiLCFvlNFVv5CNigKf8S1sIm3G6TlbBz2G5fQhbyD9qyT/D/w7FFx8pFcSk0/SxnM53kcir78o7u/nIpIgE",
     "addr": "北京",
     "token": "r+xxQh3eoNklMHgwlEh+icDX+tc3NaYGQXgJU+A6k/I="
     },
     "message": "成功"
     }
     */

}
