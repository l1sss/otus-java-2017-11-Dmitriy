package ru.otus.slisenko.webserver.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessor {
    private static TemplateProcessor instance = new TemplateProcessor();
    private final Configuration configuration;

    private TemplateProcessor() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), "/tml/");
    }

    static TemplateProcessor instance() {
        return instance;
    }

    String getPage(String page, Map<String, Object> pageVariables) throws IOException {
        try (Writer writer = new StringWriter()) {
            Template template = configuration.getTemplate(page);
            template.process(pageVariables, writer);
            return writer.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
