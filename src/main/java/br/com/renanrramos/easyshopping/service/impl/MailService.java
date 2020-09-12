/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.renanrramos.easyshopping.config.util.JwtTokenUtil;
import br.com.renanrramos.easyshopping.constants.messages.MailContentMessages;
import br.com.renanrramos.easyshopping.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.model.User;
import lombok.AllArgsConstructor;

/**
 * @author renan.ramos
 *
 */
@Service
@AllArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${easy.verification}")
	private String verificationLink;

	public void sendEmail(User user) throws EasyShoppingException {
		String token = jwtTokenUtil.generateToken(user);
		sendEmail(token, user.getEmail());
	}
	
	@Async
	private void sendEmail(String token, String recipient) throws EasyShoppingException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("noreply-easyshopping@email.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject(MailContentMessages.MAIL_SUBJECT);
            messageHelper.setText(MailContentMessages.MAIL_BODY + verificationLink + token);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			throw new EasyShoppingException("Exception occurred when sending mail to " + recipient);
		}
	}
}
