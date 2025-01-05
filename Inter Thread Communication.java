//package Inter_Thread_Communication;

class sharedObjectChannel {
    private String message = "";
    private boolean empty = true;

    public synchronized String take() {
        while (empty) {
            try {
                System.out.println(Thread.currentThread().getName() + " thread waiting for new message\n");
                wait(4000); // wait till a new message is received

                if (empty)
                    return ""; // if no message is received in 4 seconds, return empty string
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception caught");
            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void put(String message_) {
        while (!empty) {
            try {
                wait(); // wait till the current message is consumed
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception caught");
            }
        }
        empty = false;
        this.message = message_;
        notifyAll();
    }
}

class Producer extends Thread {
    private sharedObjectChannel channel;

    public Producer(sharedObjectChannel channel_, String tname_) {
        super(tname_);
        this.channel = channel_;
    }

    @Override
    public void run() {
        String[] messages = { "Hello", "World", "Goodbye" };

        for (String message : messages) {
            System.out.println(Thread.currentThread().getName() + " thread sending: " + message);
            channel.put(message);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception caught");
            }
        }
    }
}

class Consumer extends Thread {
    private sharedObjectChannel channel;

    public Consumer(sharedObjectChannel channel_, String tname_) {
        super(tname_);
        this.channel = channel_;
    }

    @Override
    public void run() {

        while (true) {
            String message = channel.take();

            if (message.isEmpty()) {
                System.out.println("No message received, terminating thread: " + Thread.currentThread().getName());
                break;
            }
            System.out.println(Thread.currentThread().getName() + " thread received: " + message);
        }
    }
}

public class MainThread {
    public static void main(String[] args) {
        sharedObjectChannel channel = new sharedObjectChannel();
        Thread producer = new Producer(channel, "Producer");
        Thread consumer = new Consumer(channel, "Consumer");

        producer.start();
        consumer.start();
    }
}

/*Output
Consumer thread waiting for new message

Producer thread sending: Hello
Consumer thread received: Hello
Consumer thread waiting for new message

Producer thread sending: World
Consumer thread received: World
Consumer thread waiting for new message

Producer thread sending: Goodbye
Consumer thread received: Goodbye
Consumer thread waiting for new message

No message received, terminating thread: Consumer
*/