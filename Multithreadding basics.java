import java.util.function.*;

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable is running");
    }
}

@FunctionalInterface
interface MyRunnableInterface{
    void run();
}

class MyThread extends Thread{
    public MyThread(String tname){
        super(tname);
    }

    @Override
    public void run() {
        System.out.printf("%s is running",Thread.currentThread().getName());
    }
}

class Main {
    public static void main(String[] args) {
        // using an implemented class of runnable
        MyRunnable tr = new MyRunnable();
        Thread t = new Thread(tr);
        t.start();

        // using thread class
        Thread t1 = new MyThread("Thread1");
        t1.start();

        // using anonymous class
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("\nAnonymous Runnable is running in thread: %s",Thread.currentThread().getName());
            }
        }, "Anonymous Thread");
        t2.start();

        // using lambda expression
        Thread t3 = new Thread(() -> System.out.printf("\nLambda Runnable is running in thread: %s",Thread.currentThread().getName()), "Lambda Thread");
        t3.start();
    }
}

/* Output
Runnable is running
Thread1 is running
Anonymous Runnable is running in thread: Anonymous Thread
Lambda Runnable is running in thread: Lambda Thread
*/