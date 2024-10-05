package com.fixedasset.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Case Captcha Exception
 */
public class CaptchaException extends AuthenticationException {
    public CaptchaException(String msg) {
        super(msg);
    }
}
