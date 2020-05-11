public class PingPongByVolatile {

    public static void main(String[] args) {
        PingPongByVolatile counter = new PingPongByVolatile();
        counter.go();
    }

    private volatile String last = "thread_2";

    private void print(String thread) {
        for (int i = 1; i < 20; i++) {
            int j = i;
            if (i > 10) {
                j = 20 - i;
            }
            while (last.equals(thread)) {
            }
            System.out.println(thread + ":" + j);
            last = thread;
            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void go() {
        Thread thread1 = new Thread(() -> print("thread_1"));
        Thread thread2 = new Thread(() -> print("thread_2"));
        thread1.start();
        thread2.start();
    }

}
