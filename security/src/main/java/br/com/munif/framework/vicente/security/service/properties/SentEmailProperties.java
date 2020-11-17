package br.com.munif.framework.vicente.security.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vic.sent-email")
public class SentEmailProperties {
    private String user;
    private String password;
    private String fileDir;
    private String recoverPasswordMessage;
    private String recoverPasswordSubject;

    public SentEmailProperties() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getRecoverPasswordMessage() {
        return recoverPasswordMessage;
    }

    public void setRecoverPasswordMessage(String recoverPasswordMessage) {
        this.recoverPasswordMessage = recoverPasswordMessage;
    }

    public String getRecoverPasswordSubject() {
        return recoverPasswordSubject;
    }

    public void setRecoverPasswordSubject(String recoverPasswordSubject) {
        this.recoverPasswordSubject = recoverPasswordSubject;
    }
}