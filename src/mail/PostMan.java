package mail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;

/**
 * @author Ray
 *
 */
public class PostMan {

	 public static void sendCeriticationJoin(String to, String toPage) {
	  
		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-mm-dd hh:mm:ss"); 
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		String today = sdfCurrent.format(currentTime); 
		 
	  String from = "gooddaumi@gmail.com";    // 메일 보내는 사람
	  String cc = "";     // 참조
	  String subject = "위젯스토어의 가입 인증메일 입니다.";
	  String content = new StringBuilder("안녕하세요.  회원님의 가입 인증을 위해 다음 url에 접속하시면 가입이 마무리됩니다. 만일 가입하지 않으셨는데 메일이 도착하셨다면 관리자에 문의 하시기 바랍니다.\n")
			  .append(toPage).toString();
	  
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
	

	 public static void sendWelcomeMail(String uName, String to) {
	  
		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-mm-dd hh:mm:ss"); 
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		String today = sdfCurrent.format(currentTime); 
		 
	  String from = "gooddaumi@gmail.com";    // 메일 보내는 사람
	  String cc = "";     // 참조
	  String subject =  "위젯스토어에 가입하실것을 환영합니다.";
	  String content = uName+"의 위젯스토어 가입을 축하드립니다. 위젯스토어 서비스의 사용방법은 사이트를 참고해주세요. 감사합니다.";
	  
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
	

	 public static void sendFailEvaluation(String wName, String uName, String failReason, String to) {
	  
		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-mm-dd hh:mm:ss"); 
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		String today = sdfCurrent.format(currentTime); 
		 
	  String from = "gooddaumi@gmail.com";    // 메일 보내는 사람
	  String cc = "";     // 참조
	  String subject =  new StringBuilder("[WidetStore] 위젯'").append(wName).append("'의 위젯심사에 실패하였습니다.").toString();// 제목
	  String content = new StringBuilder("안녕하세요. 위젯스토어에 올리신' ").append(wName).append("'위젯이 '").append(failReason).append("'떄문에 실패하였습니다.\n")
			  								.append("실패원인을 해결하셔서 다시 업로드하여 주시기 바랍니다. \n\n\n").toString();

	  
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
	
	
 public static void sendCertificationNumber(String AuthNumber) {
  
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