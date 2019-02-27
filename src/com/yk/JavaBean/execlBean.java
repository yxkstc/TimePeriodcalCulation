package com.yk.JavaBean;

public class execlBean {
    private String company;
    private String username;
    private String processname;
    private String processcoding;
    private String auditnode;
    private String starttime;
    private String endtime;
    private String overtime;
    private String system_shijiancha;
    private String system_chaoshi;

    public String getSystem_shijiancha() {
        return system_shijiancha;
    }

    public void setSystem_shijiancha(String system_shijiancha) {
        this.system_shijiancha = system_shijiancha;
    }

    public String getSystem_chaoshi() {
        return system_chaoshi;
    }

    public void setSystem_chaoshi(String system_chaoshi) {
        this.system_chaoshi = system_chaoshi;
    }

    public String getSystem_sfchaoshi() {
        return system_sfchaoshi;
    }

    public void setSystem_sfchaoshi(String system_sfchaoshi) {
        this.system_sfchaoshi = system_sfchaoshi;
    }

    private String system_sfchaoshi;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getProcesscoding() {
        return processcoding;
    }

    public void setProcesscoding(String processcoding) {
        this.processcoding = processcoding;
    }

    public String getAuditnode() {
        return auditnode;
    }

    public void setAuditnode(String auditnode) {
        this.auditnode = auditnode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    @Override
    public String toString() {
        return "execlBean{" +
                "company='" + company + '\'' +
                ", username='" + username + '\'' +
                ", processname='" + processname + '\'' +
                ", processcoding='" + processcoding + '\'' +
                ", auditnode='" + auditnode + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", overtime='" + overtime + '\'' +
                ", system_shijiancha='" + system_shijiancha + '\'' +
                ", system_chaoshi='" + system_chaoshi + '\'' +
                ", system_sfchaoshi='" + system_sfchaoshi + '\'' +
                '}';
    }
}
