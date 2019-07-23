package staticproxy;

public class Proxy implements Action {

    private RealObject realObject;

    public Proxy(RealObject realObject){
        this.realObject = realObject;
    }

    @Override
    public void doSomething() {
        System.out.println("proxy do");
        realObject.doSomething();
    }

}
