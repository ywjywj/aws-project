package com.project.QRGenrator.member;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Value("${application.bucket.name}")
    private String bucketName; 

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private AmazonSNS snsClient;

    public URL upLoad(BufferedImage bufferedImage,Member member) throws IOException {
//        File fileObj = convertMultiPartFileToFile(bufferedImage);
        //파일 전환 후 전송 해당 클래스들 파악하기

//        String datetimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        String fileName = datetimeStr + "qr";
        String fileName = UUID.randomUUID().toString();
        File fileObj = new File(fileName+".png");
        ImageIO.write(bufferedImage,"png",fileObj);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        memberRepository.save(member);
        System.out.println(url);
        return url;
    }

    public boolean getMemberByPwd(String pwd) {
        boolean check = memberRepository.getMemberByPwd(pwd);
        return check;

    }

    public void sendSms(URL url,String phone) {
        PublishRequest request = new PublishRequest();
//        request.setMessageStructure("URL");
        request.setMessage(url.toString());
        request.setPhoneNumber(phone);

        snsClient.publish(request);
    }
}
