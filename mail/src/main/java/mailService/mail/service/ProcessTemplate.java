package mailService.mail.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import java.io.IOException;
import java.util.Map;

public class ProcessTemplate {
    private Configuration configuration;

    public ProcessTemplate(Configuration configuration) {
        this.configuration = configuration;
    }

    public final String setDataInTemplate(String template, Map<String, Object> data) {
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");

        StringBuilder templateContent = new StringBuilder();
        try {
            templateContent.append(
                    FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(template), data));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return templateContent.toString();
    }

    public String[] getAllCustomAttributes() {
        return configuration.getCustomAttributeNames();
    }
    public Template customProcessTemplateMethod(Map<String, String> requestData, String templateName) throws IOException {
        return configuration.getTemplate(templateName);
    }
}