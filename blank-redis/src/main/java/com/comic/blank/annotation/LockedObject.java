package com.comic.blank.annotation;

import java.lang.annotation.*;

/**
 * 用于注解商品ID等基本类型的参数
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockedObject {
    //不需要值
}
