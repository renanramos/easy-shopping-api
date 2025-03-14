/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 11/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.service.impl;

import br.com.renanrramos.easyshopping.core.domain.constants.MailContentConstantMessages;
import br.com.renanrramos.easyshopping.infra.controller.exceptionhandler.exception.EasyShoppingException;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author renan.ramos
 *
 */
@Service
@AllArgsConstructor
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${easy.verification}")
    private String verificationLink;

    public MailService() {
        // Intentionally empty
    }

    public void sendEmail(String token, UserEntity user) throws EasyShoppingException {
        sendEmail(token, user.getEmail());
    }

    @Async
    private void sendEmail(String token, String recipient) throws EasyShoppingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("easyshoppingpucminas@gmail.com");
        message.setTo(recipient);
        message.setSubject(MailContentConstantMessages.MAIL_SUBJECT);
        message.setText(MailContentConstantMessages.MAIL_BODY + verificationLink + token + "/");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new EasyShoppingException("Exception occurred when sending mail to " + recipient);
        }
    }
}
