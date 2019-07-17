package aop;

import java.lang.reflect.Method;

public class TimeCsAdvice implements Advice {

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Exception {
        long start = System.currentTimeMillis();
        // 反射调用目标对象的该方法
        Object ret = method.invoke(target, args);
        System.out.println("记录：" + target.getClass().getName() + "耗时：" + (System.currentTimeMillis()-start) + " 毫秒");
        return ret;
    }

}
