package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class getAddressData extends BaseData {


    /**
     * {"code":"4000","data":{"addrList":[{"IsDefault":"1","AddrId":"6","Telphone":"15615672135","Addr":"北京市霍营小区","Name":"张三"}]},"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<DataDataData> addrList;

        public List<DataDataData> getAddrList() {
            return addrList;
        }

        public void setAddrList(List<DataDataData> addrList) {
            this.addrList = addrList;
        }

        /**
         * "DiseaseName":"无","Sort":"1","DiseaseId":"2"
         */
        public static class DataDataData {
            private String IsDefault;
            private String AddrId;
            private String Telphone;
            private String Addr;
            private String Name;

            public String getIsDefault() {
                return IsDefault;
            }

            public void setIsDefault(String isDefault) {
                IsDefault = isDefault;
            }

            public String getAddrId() {
                return AddrId;
            }

            public void setAddrId(String addrId) {
                AddrId = addrId;
            }

            public String getTelphone() {
                return Telphone;
            }

            public void setTelphone(String telphone) {
                Telphone = telphone;
            }

            public String getAddr() {
                return Addr;
            }

            public void setAddr(String addr) {
                Addr = addr;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }
        }
    }
}
