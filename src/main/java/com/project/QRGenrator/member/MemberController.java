package com.project.QRGenrator.member;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/")
    public String home(Model model, MemberDto memberDto){
        model.addAttribute("MemberDto",memberDto);
        return "create";
    }
    @PostMapping("/search/pi")
    public String search(@ModelAttribute("MemberDto") MemberDto memberDto){
        //db read 호출

        Member member = new Member(memberDto.getPwd());
        if(memberService.getMemberByPwd(member.getPwd())){
            return "qr";
        }else{
            return "false";
        }
//        model.addAttribute("qrUrl",qrUrl);

    }
    @GetMapping("/search")
    public String create(Model model, MemberDto memberDto){
        model.addAttribute("MemberDto",memberDto);
        return "index";
    }
    @PostMapping("/qrGRT")
    public String newQr(@ModelAttribute("MemberDto") MemberDto memberDto) throws WriterException, IOException {
        Member member = new Member(memberDto.getPwd());
        int width = 200;
        int height = 200;
//        String fileName;
        BitMatrix matrix = new MultiFormatWriter().encode(member.getPwd(), BarcodeFormat.QR_CODE, width, height);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
//                MatrixToImageWriter.writeToStream(matrix, "PNG", out);
//                String datetimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//                fileName = datetimeStr + "qr";
//                File temp = new File(savePath + "/" + fileName + ".png");
               URL url = memberService.upLoad(bufferedImage,member);
               memberService.sendSms(url,memberDto.getPhone());
                return "redirect:/";
        }
        //db insert 호출
//        return "redirect:/";
    }



}
