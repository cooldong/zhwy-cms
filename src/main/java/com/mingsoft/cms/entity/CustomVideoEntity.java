package com.mingsoft.cms.entity;

import com.mingsoft.basic.entity.BasicEntity;

import java.util.Date;

public class CustomVideoEntity extends BasicEntity {
    private Long videoId;

    private String name;

    private Integer type;

    private String url1;

    private String des1;

    private Long uploadid1;

    private String uploadname1;

    private String uploadpic1;

    private Date uploadtime1;

    private String url2;

    private String des2;

    private Long uploadid2;

    private String uploadname2;

    private String uploadpic2;

    private Date uploadtime2;

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1 == null ? null : url1.trim();
    }

    public String getDes1() {
        return des1;
    }

    public void setDes1(String des1) {
        this.des1 = des1 == null ? null : des1.trim();
    }

    public Long getUploadid1() {
        return uploadid1;
    }

    public void setUploadid1(Long uploadid1) {
        this.uploadid1 = uploadid1;
    }

    public String getUploadname1() {
        return uploadname1;
    }

    public void setUploadname1(String uploadname1) {
        this.uploadname1 = uploadname1 == null ? null : uploadname1.trim();
    }

    public String getUploadpic1() {
        return uploadpic1;
    }

    public void setUploadpic1(String uploadpic1) {
        this.uploadpic1 = uploadpic1 == null ? null : uploadpic1.trim();
    }

    public Date getUploadtime1() {
        return uploadtime1;
    }

    public void setUploadtime1(Date uploadtime1) {
        this.uploadtime1 = uploadtime1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2 == null ? null : url2.trim();
    }

    public String getDes2() {
        return des2;
    }

    public void setDes2(String des2) {
        this.des2 = des2 == null ? null : des2.trim();
    }

    public Long getUploadid2() {
        return uploadid2;
    }

    public void setUploadid2(Long uploadid2) {
        this.uploadid2 = uploadid2;
    }

    public String getUploadname2() {
        return uploadname2;
    }

    public void setUploadname2(String uploadname2) {
        this.uploadname2 = uploadname2 == null ? null : uploadname2.trim();
    }

    public String getUploadpic2() {
        return uploadpic2;
    }

    public void setUploadpic2(String uploadpic2) {
        this.uploadpic2 = uploadpic2 == null ? null : uploadpic2.trim();
    }

    public Date getUploadtime2() {
        return uploadtime2;
    }

    public void setUploadtime2(Date uploadtime2) {
        this.uploadtime2 = uploadtime2;
    }
}