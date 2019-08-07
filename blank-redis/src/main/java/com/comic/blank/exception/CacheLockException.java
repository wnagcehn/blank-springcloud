package com.comic.blank.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author wangchen
 * createAt 2019/5/28
 * updateAt 2019/5/28
 */
public class CacheLockException extends Throwable {

    @Getter
    @Setter
    private String msg;

    public CacheLockException(String msg) {
        this.msg = msg;
    }

    public CacheLockException() {

    }

}
 
