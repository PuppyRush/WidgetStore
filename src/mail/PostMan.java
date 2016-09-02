package mail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;

/**
 * @author Ray
 *
 */
public class PostMan {

 public static void sendMail(String AuthNumber) {
  
	SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-mm-dd hh:mm:ss"); 
	Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
	String today = sdfCurrent.format(currentTime); 
	 
  String from = "gooddaumi@gmail.com";    // 메일 보내는 사람
  String to = "gooddaumi@naver.com";   // 메일 보낼사람
  String cc = "";     // 참조
  String subject = "[WidetStore] 비밀번호 찾기 인증번호 안내 메일입니다.";// 제목
  String content =
   "안녕하세요. 위젯스토어에서 비밀번호 찾기를 위한 인증번호를 전송합니다.\n"
   + "인증번호를 진행중인 화면에 입력해주세요\n"
   + "인증번호는 발송된 시점으로부터 24시간동안 유효합니다. \n\n"
   + "인증번호 :" + AuthNumber +"\n"
   + "발급시간 :" + today +"\n"
   + "감사합니다";// 내용
  
  if(from.trim().equals("")) {
   System.out.println("보내는 사람을 입력하지 않았습니다.");
  }
  else if(to.trim().equals("")) {
   System.out.println("받는 사람을 입력하지 않았습니다.");
  }
  else {
   try {
	   SMTPConnector mt = new SMTPConnector();
    
    // 메일보내기
    mt.sendEmail(from, to, cc, subject, content);
    System.out.println("메일 전송에 성공하였습니다.");
   }
   catch(MessagingException me) {
    System.out.println("메일 전송에 실패하였습니다.");
    System.out.println("실패 이유 : " + me.getMessage());
   }
   catch(Exception e) {
    System.out.println("메일 전송에 실패하였습니다.");
    System.out.println("실패 이유 : " + e.getMessage());
   }
  }
 }
}