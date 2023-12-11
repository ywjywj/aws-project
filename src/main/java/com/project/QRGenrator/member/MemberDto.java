package com.project.QRGenrator.member;


public class MemberDto {
    private String pwd;
    private String phone;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public MemberDto(String pwd, String phone) {
        this.pwd = pwd;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public MemberDto() {
    }
}
