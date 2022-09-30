package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.domain.dto.LoginDto;
import br.com.munif.framework.vicente.security.domain.dto.LoginResponseDto;
import br.com.munif.framework.vicente.security.domain.dto.RefreshTokenDto;

import java.util.Map;

public interface ITokenService extends VicServiceable<Token> {
    LoginResponseDto loginOnGoogle(String token);

    LoginResponseDto login(LoginDto login);
    RefreshTokenDto refreshToken(RefreshTokenDto refreshTokenDto);

    Token createToken(User user);

    LoginResponseDto logout();

    Token findTokenByValue(String tokenValue);

    LoginResponseDto sigin(LoginDto login);

    void recoverPassword(String id);

    Map searchTicket(String id);

    Map lostPassword(String ticket, String password);

    Map changeOrganization(String organizationId);
}
