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
    private String host;
    private String port;
    private String auth;
    private String starttls;

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getStarttls() {
        return starttls;
    }

    public void setStarttls(String starttls) {
        this.starttls = starttls;
    }
}