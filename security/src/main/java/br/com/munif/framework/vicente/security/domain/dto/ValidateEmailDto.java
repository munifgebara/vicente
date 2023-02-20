package br.com.munif.framework.vicente.security.domain.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ValidateEmailDto {
    public String email;
    public String code;
    public ZonedDateTime expiration;
}
