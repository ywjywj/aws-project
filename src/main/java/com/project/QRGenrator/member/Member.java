package com.project.QRGenrator.member;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

//tablename도 변경해줘야하는 사항
@DynamoDBTable(tableName = "testdynamo")
public class Member {

    @DynamoDBHashKey
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Member() {
    }

    public Member(String pwd) {
        this.pwd = pwd;
    }
}
