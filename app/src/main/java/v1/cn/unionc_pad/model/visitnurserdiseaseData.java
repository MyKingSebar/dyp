package v1.cn.unionc_pad.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class visitnurserdiseaseData extends BaseData {


    /**
     * {"code":"4000","data":{"
     * <p>
     * data":[{"DiseaseName":"无","Sort":"1","DiseaseId":"2"},{"DiseaseName":"感冒","Sort":"2","DiseaseId":"3"},{"DiseaseName":"肺部及呼吸道疾病","Sort":"3","DiseaseId":"4"},{"DiseaseName":"肠胃系统疾病","Sort":"4","DiseaseId":"5"},{"DiseaseName":"神经系统疾病","Sort":"5","DiseaseId":"6"},{"DiseaseName":"肝胆疾病","Sort":"6","DiseaseId":"7"},{"DiseaseName":"风湿骨病","Sort":"7","DiseaseId":"8"},{"DiseaseName":"泌尿系统疾病","Sort":"8","DiseaseId":"9"},{"DiseaseName":"脑血管病","Sort":"9","DiseaseId":"10"},{"DiseaseName":"心血管病","Sort":"10","DiseaseId":"11"},{"DiseaseName":"妇科疾病","Sort":"11","DiseaseId":"12"},{"DiseaseName":"皮肤病","Sort":"12","DiseaseId":"13"},{"DiseaseName":"营养不良及贫血腹泻","Sort":"13","DiseaseId":"14"},{"DiseaseName":"糖尿病","Sort":"14","DiseaseId":"15"},{"DiseaseName":"免疫系统疾病","Sort":"15","DiseaseId":"16"},{"DiseaseName":"昏迷","Sort":"16","DiseaseId":"17"},{"DiseaseName":"性传播疾病","Sort":"17","DiseaseId":"18"},{"DiseaseName":"肿瘤","Sort":"18","DiseaseId":"19"},{"DiseaseName":"其他","Sort":"999999","DiseaseId":"1"}]
     * <p>
     * },"message":"成功"}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<DiseaseData> data;

        public List<DiseaseData> getData() {
            return data;
        }

        public void setData(List<DiseaseData> data) {
            this.data = data;
        }

        /**
         * "DiseaseName":"无","Sort":"1","DiseaseId":"2"
         */
        public static class DiseaseData {
            private String DiseaseName;
            private String Sort;
            private String DiseaseId;

            public String getDiseaseName() {
                return DiseaseName;
            }

            public void setDiseaseName(String diseaseName) {
                DiseaseName = diseaseName;
            }

            public String getSort() {
                return Sort;
            }

            public void setSort(String sort) {
                Sort = sort;
            }

            public String getDiseaseId() {
                return DiseaseId;
            }

            public void setDiseaseId(String diseaseId) {
                DiseaseId = diseaseId;
            }
        }
    }
}
