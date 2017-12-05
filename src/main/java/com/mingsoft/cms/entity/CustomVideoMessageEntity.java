package com.mingsoft.cms.entity;

import com.mingsoft.basic.entity.BasicEntity;
import org.springframework.data.annotation.Transient;

import java.util.Date;

public class CustomVideoMessageEntity extends BasicEntity {
    private Integer messageId;

    private String comment;

    private Long createid;

    private String createname;

    private Date createtime;

    private Integer type;

    private Long pid;

    private Long videoid;

    private Integer oknum;

    private String createpic;

    public String getCreatepic() {
        return createpic;
    }

    public void setCreatepic(String createpic) {
        this.createpic = createpic;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname == null ? null : createname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getVideoid() {
        return videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public Integer getOknum() {
        return oknum;
    }

    public void setOknum(Integer oknum) {
        this.oknum = oknum;
    }

    @Transient
    private Integer limit;
    @Transient
    private Integer offset;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}