public final class PingPongByVolatile {
    private volatile long pingValue = -1;
    private volatile long pongValue = -1;

    public static void main(final String[] args) {
        PingPongByVolatile pongByVolatile = new PingPongByVolatile();
        pongByVolatile.go();
    }

    private void go() {
        final Thread pongThread = new Thread(new PongRunner());
        final Thread pingThread = new Thread(new PingRunner());
        pongThread.setName("pong-thread");
        pongThread.setName("ping-thread");
        pongThread.start();
        pingThread.start();
    }

    public class PingRunner implements Runnable {
        public void run() {
            while (true) {
                for (int i = 0; i < 20; ++i) {
                    int j = i;
                    if (i > 10) {
                        j = 20 - i;
                    }
                    System.out.println("thread_1: " + j);
                    pingValue = i;
                    while (i != pongValue) {
                    }
                    sleep();
                }
            }
        }
    }

    public class PongRunner implements Runnable {
        public void run() {
            while (true) {
                for (int i = 0; i < 20; ++i) {
                    int j = i;
                    if (i > 10) {
                        j = 20 - i;
                    }
                    while (i != pingValue) {
                    }
                    pongValue = i;
                    System.out.println("thread_2: " + j);
                    sleep();
                }
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

}