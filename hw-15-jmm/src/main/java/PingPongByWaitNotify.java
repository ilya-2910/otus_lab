public class PingPongByWaitNotify {

    public static void main(String[] args) {
        PingPongByWaitNotify counter = new PingPongByWaitNotify();
        counter.go();
    }

    private String last = "thread_2";

    private synchronized void print(String thread) {
        for (int i = 1; i < 20; i++) {
            int j = i;
            if (i > 10) {
                j = 20 - i;
            }
            try {
                while (last.equals(thread)) {
                    if (last.equals("thread_2")) {
                        Thread.sleep(2400);
                    } else if (last.equals("thread_1")) {
                        Thread.sleep(800);
                    }
                    this.wait();
                }
                System.out.println(thread + ": " + j);
                last = thread;
                sleep();
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
