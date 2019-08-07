package com.comic.blank.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockedComplexObject {

    //含有成员变量的复杂对象中需要加锁的成员变量，如一个商品对象的商品ID
    String field() default "";

}
