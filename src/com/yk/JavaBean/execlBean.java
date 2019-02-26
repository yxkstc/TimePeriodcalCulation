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
                ", Overtime='" + overtime + '\'' +
                '}';
    }
}
