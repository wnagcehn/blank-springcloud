package staticproxy;

public class RealObject implements Action {

    @Override
    public void doSomething() {
        System.out.println("do something");
    }
}
