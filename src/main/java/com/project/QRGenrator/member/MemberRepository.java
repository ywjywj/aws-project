package com.project.QRGenrator.member;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.xspec.M;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class MemberRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    // 생성
    public void save(Member member) {
        dynamoDBMapper.save(member);
    }
    // 조회 일단 미정
    public boolean getMemberByPwd(String pwd) {
        Member member = dynamoDBMapper.load(Member.class, pwd);
        return  member != null;

    }

}
