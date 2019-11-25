package com.yyy.bookManager.model.exceptions;

//注册和登入时的异常,都是调用父类的有参数构造器
public class LoginRegisterException extends RuntimeException {

    public LoginRegisterException() {
        super();
    }

    public LoginRegisterException(String message) {
        super(message);
    }

    public LoginRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginRegisterException(Throwable cause) {
        super(cause);
    }
}
