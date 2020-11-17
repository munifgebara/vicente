package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.security.domain.dto.SentEmailDto;

public interface IEmailService {
    void send(SentEmailDto emailDto);

    void sendPasswordRecover(String email, String password);

    String getTemplate(SentEmailDto emailDto);
}
