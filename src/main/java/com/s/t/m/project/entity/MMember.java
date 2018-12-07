package com.s.t.m.project.entity;

import java.util.Date;

public class MMember {
    /**
     * 会员卡号
     */
    private String cardnum;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String userpwd;

    /**
     * 性别（1为男，0为女）
     */
    private Byte gender;

    /**
     * 国籍
     */
    private String country;

    /**
     * 会员级别（vip cip 普通）
     */
    private String memberclass;

    /**
     * 会员类型（数据字典 GroupId=7）
     */
    private String membertype;

    /**
     * 民族
     */
    private String nation;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 证件号码（密文）
     */
    private String papernum;

    /**
     * 手机号码（密文）
     */
    private String cellphone;

    /**
     * 备用手机（密文）
     */
    private String backupcellphone;

    /**
     * 电话号码
     */
    private String phonenum;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 职务
     */
    private String position;

    /**
     * 乘机人姓名（航信系统使用，生僻字用拼音代替）
     */
    private String passengername;

    /**
     * 行政级别（GroupID=31）
     */
    private String administrativelevel;

    /**
     * 职称
     */
    private String jobtitle;

    /**
     * 本人是否为领导秘书
     */
    private String issecretary;

    /**
     * 会员所属单位
     */
    private String companyid;

    /**
     * 部门ID
     */
    private Integer departmentid;

    /**
     * 人员类型
     */
    private String personneltype;

    /**
     * 会员来源（GroupID=42）
     */
    private String memberfrom;

    /**
     * HR人员编号
     */
    private String hrcode;

    /**
     * 是否可用
     */
    private Boolean isenable;

    /**
     * 创建用户的票台ID
     */
    private Integer ticketofficeid;

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

    /**
     * 修改时间
     */
    private Date updatetime;

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd == null ? null : userpwd.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getMemberclass() {
        return memberclass;
    }

    public void setMemberclass(String memberclass) {
        this.memberclass = memberclass == null ? null : memberclass.trim();
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype == null ? null : membertype.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPapernum() {
        return papernum;
    }

    public void setPapernum(String papernum) {
        this.papernum = papernum == null ? null : papernum.trim();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public String getBackupcellphone() {
        return backupcellphone;
    }

    public void setBackupcellphone(String backupcellphone) {
        this.backupcellphone = backupcellphone == null ? null : backupcellphone.trim();
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum == null ? null : phonenum.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getPassengername() {
        return passengername;
    }

    public void setPassengername(String passengername) {
        this.passengername = passengername == null ? null : passengername.trim();
    }

    public String getAdministrativelevel() {
        return administrativelevel;
    }

    public void setAdministrativelevel(String administrativelevel) {
        this.administrativelevel = administrativelevel == null ? null : administrativelevel.trim();
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle == null ? null : jobtitle.trim();
    }

    public String getIssecretary() {
        return issecretary;
    }

    public void setIssecretary(String issecretary) {
        this.issecretary = issecretary == null ? null : issecretary.trim();
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getPersonneltype() {
        return personneltype;
    }

    public void setPersonneltype(String personneltype) {
        this.personneltype = personneltype == null ? null : personneltype.trim();
    }

    public String getMemberfrom() {
        return memberfrom;
    }

    public void setMemberfrom(String memberfrom) {
        this.memberfrom = memberfrom == null ? null : memberfrom.trim();
    }

    public String getHrcode() {
        return hrcode;
    }

    public void setHrcode(String hrcode) {
        this.hrcode = hrcode == null ? null : hrcode.trim();
    }

    public Boolean getIsenable() {
        return isenable;
    }

    public void setIsenable(Boolean isenable) {
        this.isenable = isenable;
    }

    public Integer getTicketofficeid() {
        return ticketofficeid;
    }

    public void setTicketofficeid(Integer ticketofficeid) {
        this.ticketofficeid = ticketofficeid;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}