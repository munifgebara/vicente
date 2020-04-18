package br.com.munif.framework.vicente.security.service.interfaces;

import br.com.munif.framework.vicente.application.VicServiceable;
import br.com.munif.framework.vicente.security.domain.Token;
import br.com.munif.framework.vicente.security.domain.User;
import br.com.munif.framework.vicente.security.dto.LoginDto;
import br.com.munif.framework.vicente.security.dto.LoginResponseDto;

public interface ITokenService extends VicServiceable<Token> {
    LoginResponseDto loginOnGoogle(String token);

    LoginResponseDto login(LoginDto login);

    Token createToken(User user);

    LoginResponseDto logout();

    Token findTokenByValue(String tokenValue);

    LoginResponseDto sigin(LoginDto login);
}
