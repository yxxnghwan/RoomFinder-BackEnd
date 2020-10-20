package com.roomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomfinder.vo.MailVO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mail")
public class MailController {
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	@Async
	public void sendCertificationMail(String email, String certificationStr) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("RoomFinder@roomfinder.com"); // NAVER, DAUM, NATE일 경우 넣어줘야 함
		simpleMessage.setTo(email);
		simpleMessage.setSubject(" RoomFinder 이메일 인증입니다. ");
		simpleMessage.setText("인증번호: " + certificationStr);
		javaMailSender.send(simpleMessage);
		System.out.println("인증번호 발송완료");
	}
	
	@PostMapping("/certification")
	public String sendCertificationNumber(HttpServletRequest request, HttpServletResponse response, @RequestBody MailVO vo) {
		System.out.println("sendCertificationNumber 요청");
		System.out.println(vo);
		String certificationStr = "";
		for(int i = 0; i < 6; ++i) {
			int rand = (int)(Math.random()*10);
			certificationStr += rand;
		}
		sendCertificationMail(vo.getEmail(), certificationStr);
		
		return certificationStr;
	}
}
