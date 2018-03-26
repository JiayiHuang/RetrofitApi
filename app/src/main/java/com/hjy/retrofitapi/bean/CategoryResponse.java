package com.hjy.retrofitapi.bean;

import com.hjy.retrofitapi.bean.base.BaseResponseBean;

import java.util.List;

/**
 * <pre>
 *     author : HJY
 *     time   : 2018/03/13/10:37
 *     desc   : 文件描述
 *     version: 当前版本号
 * </pre>
 */
public class CategoryResponse extends BaseResponseBean {
    private CategoryBody body;

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public CategoryBody getBody() {
        return body;
    }

    public void setBody(CategoryBody body) {
        this.body = body;
    }

    public class CategoryBody {
        private List<CategoryBean> data;

        public List<CategoryBean> getData() {
            return data;
        }

        public void setData(List<CategoryBean> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "CategoryBody{" +
                    "data=" + data +
                    '}';
        }
    }

    public class CategoryBean {
        private String cid;
        private String cname;
        private String ad_json;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getAd_json() {
            return ad_json;
        }


        public void setAd_json(String ad_json) {

            this.ad_json = ad_json;
        }

        @Override
        public String toString() {
            return "BodyData{" +
                    "cid='" + cid + '\'' +
                    ", cname='" + cname + '\'' +
                    ", ad_json='" + ad_json + '\'' +
                    '}';
        }

    }
}
