package dynamicproxy;

public class KtvService implements IktvService{

    @Override
    public void momosing(String customer) {
        System.out.println(customer + "进行momosing动作！");
    }

}
