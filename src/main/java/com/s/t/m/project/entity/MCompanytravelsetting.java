package com.s.t.m.project.entity;


import java.util.Date;

/**
 * 单位差旅设置表
 */
public class MCompanytravelsetting {
    /**
     * ID
     */
    private Integer id;

    /**
     * 单位差旅等级ID
     */
    private Integer travellevelid;

    /**
     * 飞机等级（只保存最高等级）
     */
    private String planelevel;

    /**
     * 火车等级（只保存最高级）
     */
    private String trainlevel;

    /**
     * 创建用户ID
     */
    private String createuserid;

    /**
     * 创建用户姓名
     */
    private String createrealname;

    /**
     * 创建时间
     */
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTravellevelid() {
        return travellevelid;
    }

    public void setTravellevelid(Integer travellevelid) {
        this.travellevelid = travellevelid;
    }

    public String getPlanelevel() {
        return planelevel;
    }

    public void setPlanelevel(String planelevel) {
        this.planelevel = planelevel == null ? null : planelevel.trim();
    }

    public String getTrainlevel() {
        return trainlevel;
    }

    public void setTrainlevel(String trainlevel) {
        this.trainlevel = trainlevel == null ? null : trainlevel.trim();
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid == null ? null : createuserid.trim();
    }

    public String getCreaterealname() {
        return createrealname;
    }

    public void setCreaterealname(String createrealname) {
        this.createrealname = createrealname == null ? null : createrealname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}