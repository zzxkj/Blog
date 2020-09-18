package com.zzxkj.blog.WorkingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*设定返回的状态码，以便跳转404界面*/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException  extends  RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
