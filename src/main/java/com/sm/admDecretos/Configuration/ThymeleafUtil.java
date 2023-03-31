package com.sm.admDecretos.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class ThymeleafUtil {

    @Autowired
    public static TemplateEngine templateEngine;

    public static String getProcessedHtml(Map<String, Object> model,String templateName) {

        Context context = new Context();

        if (model != null) {
            model.forEach((key,value) -> context.setVariable(key, value));
            return templateEngine.process(templateName, context);
        }
        return "";

    }
}