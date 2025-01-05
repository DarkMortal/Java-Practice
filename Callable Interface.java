import java.util.concurrent.Callable;

class MyCallable implements Callable<Integer>{
    String name;

    public MyCallable(String name_){
        this.name = name_;
    }

    @Override
    public Integer call() {
        System.out.printf("Welcome %s to the world of Java!", this.name);
        return 100;
    }
}

public class Main{
    public static void main(String[] args) {
        Callable<Integer> newCallable = () -> {
            System.out.println("Hello and welcome!");
            return 100;
        };
        Callable<Integer> newCallable2 = new Callable<Integer>() {
            @Override
            public Integer call() {
                return 200;
            }
        };
        MyCallable myCallable = new MyCallable("John Doe");
        try {
            System.out.println(newCallable.call());
            System.out.println(newCallable2.call());
            System.out.println(myCallable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/* Output
Hello and welcome!
100
200
Welcome John Doe to the world of Java!100
 */