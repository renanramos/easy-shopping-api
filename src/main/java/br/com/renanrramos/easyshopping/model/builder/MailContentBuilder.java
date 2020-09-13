/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import lombok.AllArgsConstructor;

/**
 * @author renan.ramos
 *
 */
@Component
@AllArgsConstructor
public class MailContentBuilder {

	@Autowired
	private TemplateEngine templateEngine;

	public String build(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		if (templateEngine.isInitialized()) {
			return templateEngine.process("mailTemplate.html", context);
		}
		templateEngine.addTemplateResolver(getTemplateResolver());
		return templateEngine.process("mailTemplate.html", context);
	}

	private ITemplateResolver getTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("utf-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}
}
