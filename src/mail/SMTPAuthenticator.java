package mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import property.enums.enumMail;
/**
 * @author Ray
 *
 */
public class SMTPAuthenticator extends Authenticator {

	 protected PasswordAuthentication getPasswordAuthentication() {
		 
		 String id = enumMail.gmailID.getString();
		 String pw = enumMail.gmailPW.getString();

		return new PasswordAuthentication(id, pw);
	 }

}
