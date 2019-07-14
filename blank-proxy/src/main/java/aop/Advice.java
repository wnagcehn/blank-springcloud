package aop;

import java.lang.reflect.Method;

public interface Advice {

    /**
     * 用户要怎么增强被代理对象，功能写在这里
     * 该定义一个什么样的方法，能让用户写增强功能
     **/
    Object invoke(Object target, Method method, Object[] args) throws Exception;

}
