package mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import property.constMail;
/**
 * @author Ray
 *
 */
public class SMTPAuthenticator extends Authenticator {

	 protected PasswordAuthentication getPasswordAuthentication() {
		 
		 String id = constMail.gmailID.getString();
		 String pw = constMail.gmailPW.getString();

		return new PasswordAuthentication(id, pw);
	 }

}
