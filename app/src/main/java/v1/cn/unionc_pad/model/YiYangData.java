package v1.cn.unionc_pad.model;

import java.util.List;

public class YiYangData {
    /**
     * {
     "status": true,
     "code": "200",
     "msg": "success",
     "data": [
     {
     "title": "数据线变身手机支架",
     "description": "设计师Hana He对手机的充电数据线进行了一个小改造，令其在给手机充电的时候能够充电手机支架的作用。虽然这个小改变看起来并没有多么的伟大，但是却是极为聪明的做法。",
     "keywords": "",
     "inserttime": "1529461103771",
     "pubtime": "2018-06-17",
     "aid": "9bb41c0c-1851-602c-ad5c-c27e1a0fdd27",
     "url": "https://dbapi.myhaowai.com/article/third?aid=9bb41c0c-1851-602c-ad5c-c27e1a0fdd27",
     "readNum": "10367",
     "commentNum": "0",
     "xiaokaName": "新鲜创意图志",
     "imgUrl": "https://img0.myhaowai.com/cover/2018/06/20/8554b89af197ea8268c7558713963bcb.jpeg",
     "xiaokaLogo": "https://img0.myhaowai.com/avatar/57769399d1890f09af820b634864c3a7.jpeg"      },
     {
     "title": "陈瀚谦：股市巨震，黄金避险亦避让世界杯锋芒",
     "aid": "fe1a1fdd-b593-9ac6-8992-c131f16816df",
     "keywords": "黄金,维持,区域,反弹,思路,整理,情况,刷新",
     "content_url": "https://file.myhaowai.com/file/2018/06/20/58113.json",
     "inserttime": "1529461301838",
     "pubtime": "2018-06-20",
     "new_source": "3",
     "url": "https://dbapi.myhaowai.com/article/third?aid=9bb41c0c-1851-602c-ad5c-c27e1a0fdd27",

     "description": "",
     "readNum": "12079",
     "xiaokaName": "chenhanqian",
     "imgUrl": "https://img2.myhaowai.cn/hwphoto/2018/06/20/1529461358247628.png",
     "xiaokaLogo": "https://img8.myhaowai.cn/hwphoto/2016/08/22/1471867078440904.jpg"
     }
     ]
     }

     */
    private String status;
    private String code;
    private String msg;
    private List<DataData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataData> getData() {
        return data;
    }

    public void setData(List<DataData> data) {
        this.data = data;
    }

    public class DataData{
        private String title;
        private String aid;
        private String keywords;
        private String content_url;
        private String inserttime;
        private String pubtime;
        private String new_source;
        private String url;
        private String description;
        private String readNum;
        private String xiaokaName;
        private String imgUrl;
        private String xiaokaLogo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

        public String getInserttime() {
            return inserttime;
        }

        public void setInserttime(String inserttime) {
            this.inserttime = inserttime;
        }

        public String getPubtime() {
            return pubtime;
        }

        public void setPubtime(String pubtime) {
            this.pubtime = pubtime;
        }

        public String getNew_source() {
            return new_source;
        }

        public void setNew_source(String new_source) {
            this.new_source = new_source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getReadNum() {
            return readNum;
        }

        public void setReadNum(String readNum) {
            this.readNum = readNum;
        }

        public String getXiaokaName() {
            return xiaokaName;
        }

        public void setXiaokaName(String xiaokaName) {
            this.xiaokaName = xiaokaName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getXiaokaLogo() {
            return xiaokaLogo;
        }

        public void setXiaokaLogo(String xiaokaLogo) {
            this.xiaokaLogo = xiaokaLogo;
        }
    }
}
