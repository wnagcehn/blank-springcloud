package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 获取打印时间
 */
public class TimeCSInvocationHandler implements InvocationHandler{

    private Object target;

    public TimeCSInvocationHandler(Object target){
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        // 反射调用目标对象的该方法
        Object ret = method.invoke(target, args);
        System.out.println("记录：" + this.target.getClass() + "耗时：" + (System.currentTimeMillis()-start) + " 毫秒");
        return ret;
    }

}
