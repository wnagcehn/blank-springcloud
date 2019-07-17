package staticproxy;

public class MainClient {

    public static void main(String[] args){
        Proxy x = new Proxy(new RealObject());
        x.doSomething();
    }

}
