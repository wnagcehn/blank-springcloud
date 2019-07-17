package dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicProxy {

    public static <T> T getProxy(Object target, Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), target.getClass().getInterfaces(),
                new TimeCSInvocationHandler(target));
    }

}
