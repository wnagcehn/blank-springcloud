package com.comic.blankredis.rediscallback.Exception;
/*
 * Copyright (c) 2019
 * FileName: Lock.java
 * Author:   wangchen
 * Date:     2019年7月17日
 */

import java.io.Serializable;

public class LockInsideExecutedException extends RuntimeException {

    private static final long serialVersionUID = -5994568918045984557L;

    public LockInsideExecutedException() {
        super();
    }

    public LockInsideExecutedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockInsideExecutedException(Throwable cause) {
        super(cause);
    }

}
