package dynamicproxy;

/**
 * 示例代码
 */
public class MainClient {


    public static void main(String[] args){
        IktvService ktvService = new KtvService();
        IktvService ktvProxy = DynamicProxy.getProxy(ktvService, IktvService.class);
        ktvProxy.momosing("zhaofeng");
    }
}
