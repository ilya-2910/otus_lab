public class Main {

    public static void main(String[] args) {
        Main counter = new Main();
        counter.go();
    }

    private String last = "thread_1";

    private synchronized void print(String thread) {
        while (true) {
            for (int i = 0; i <= 20; i++) {
                int j = i;
                if (i > 10) {
                    j = 20 - i;
                }
                try {
                    while (last.equals(thread)) {
                        this.wait();
                    }
                    System.out.println(thread + ":" + j);
                    last = thread;
                    notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void go() {
        Thread thread1 = new Thread(() -> print("thread_1"));
        Thread thread2 = new Thread(() -> print("thread_2"));
        thread1.start();
        thread2.start();
    }

}
