package com.comic.blankredis.rediscallback.Exception;
/*
 * Copyright (c) 2019
 * FileName: Lock.java
 * Author:   wangchen
 * Date:     2019年7月17日
 */

public class LockCantObtainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LockCantObtainException() {
        super();
    }

    public LockCantObtainException(String message) {
        super(message);
    }

}
