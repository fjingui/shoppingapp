package com.bean.list;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ApkVersion {
    private int version_code;
    private String version_name;
    private String version_desc;
    private String publish_date;
    private String min_version_name;
    private String apk_path;

    public int getVersion_code() {
        return version_code;
    }
    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }
    public String getVersion_name() {
        return version_name;
    }
    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
    public String getVersion_desc() {
        return version_desc;
    }
    public void setVersion_desc(String version_desc) {
        this.version_desc = version_desc;
    }
    public String getPublish_date() {
        return publish_date;
    }
    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getMin_version_name() {
        return min_version_name;
    }
    public void setMin_version_name(String min_version_name) {
        this.min_version_name = min_version_name;
    }
    public String getApk_path() {
        return apk_path;
    }
    public void setApk_path(String apk_path) {
        this.apk_path = apk_path;
    }

}
