package kr.cafeIn.cafeorder.ext;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  public void send(String email, String otp) throws Exception {
    //이메일 속성 설정
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com"); //서버 호스트이름 설정
    properties.put("mail.smtp.port", "587");// 서버 포트 번호
    properties.put("mail.smtp.auth", "true");// SMTP 인증이 필요한지 여부
    properties.put("mail.smtp.starttls.enable", "true");//암호화 활성 여부

    //이메일 세션 설정
    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    //이메일 메세지 작성 및 보내기
    //MIME 는 JavaMail API 의 클래스 이다, 보낸 사람 및 받는 사람 주소 등 전자메일 메세지를 만들 수 있다.
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(email));
    message.setRecipients(RecipientType.TO, InternetAddress.parse(email));
    message.setSubject("Password Reset OTP");
    message.setText("당신의 OTP 입니다: " + otp);

    //SMTP 전송 프로토콜을 통해 이메일 메시지를 지정된 메일 서버로 보낸다.
    Transport.send(message);
  }
}
