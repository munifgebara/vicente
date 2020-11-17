package br.com.munif.framework.vicente.security.domain.dto;

import java.util.HashMap;
import java.util.Map;

public class SentEmailDto {
    private String to;
    private String subject = "New E-mail";
    private Map<String, String> body = new HashMap<>();
    private String template = "email.template.html";
    private String file;
    private String fileName;

    public SentEmailDto() {
    }

    public SentEmailDto(String to, String subject) {
        this.to = to;
        this.subject = subject;
    }

    public SentEmailDto append(String key, String value) {
        this.body.put(key, value);
        return this;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
