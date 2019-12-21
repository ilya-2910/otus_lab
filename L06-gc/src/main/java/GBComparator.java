import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Date;
import java.util.List;

public class GBComparator {

    public static void main(String[] args) throws Exception {
        new GBComparator().start();
    }

    private GcInfo youngGcInfo;
    private GcInfo oldGcInfo;

    private static String G1 = "G1";
    private static String PARALLEL = "PARALLEL GC";

    public void start() {
        Date startDate = new Date();
        // "G1" or "parralel"
        switchOnMonitoring(G1);
        try {
            generateOOM();
        } catch (Throwable e) {
            Date endDate = new Date();
            System.out.println("execution time sec=" + (endDate.getTime() - startDate.getTime()) / 1000);

            printInfo(youngGcInfo);
            printInfo(oldGcInfo);
        }
    }

    private void printInfo(GcInfo gcInfo) {
        System.out.println("******" + gcInfo.getName() + "*******");
        System.out.println("all gc pause (throughput) ms=" + (gcInfo.getDuration()));
        System.out.println("count gc pause=" + (gcInfo.getCount()));
        System.out.println("maxPause (latency)=" + (gcInfo.getMaxPause()));
    }

    public void generateOOM() throws Exception {
        int arraySize = 0;
        for (int i = 1; i < 100000; i++) {
            System.out.println("Iteration " + i + " Free Mem: " + Runtime.getRuntime().freeMemory());
            int[] memoryFillIntVar = new int[arraySize];
            for (int j = 0; j < arraySize; j++) {
                memoryFillIntVar[j] = 0;
            }
            arraySize = arraySize + 10000;
            System.out.println("Required Memory for next loop: " + arraySize);
            Thread.sleep(500);
        }
    }

    private void switchOnMonitoring(String type) {
        if (type == G1) {
            youngGcInfo = new GcInfo("Young Generation");
            oldGcInfo = new GcInfo("Old Generation");
        } else if (type == PARALLEL) {
            youngGcInfo = new GcInfo("PS Scavenge");
            oldGcInfo = new GcInfo("PS MarkSweep");
        }


        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    GcInfo gcInfo = null;
                    if (gcName.contains(oldGcInfo.getName())) {
                        gcInfo = oldGcInfo;
                    } else if (gcName.contains(youngGcInfo.getName())) {
                        gcInfo = youngGcInfo;
                    }

                    gcInfo.setCount(gcInfo.getCount() + 1);
                    gcInfo.setDuration(gcInfo.getDuration() + duration);
                    if (duration > gcInfo.getMaxPause()) {
                        gcInfo.setMaxPause(duration);
                    }
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    class GcInfo {
        private final String name;
        int count;
        long duration;
        long maxPause;

        public GcInfo(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public long getMaxPause() {
            return maxPause;
        }

        public void setMaxPause(long maxPause) {
            this.maxPause = maxPause;
        }

        public String getName() {
            return name;
        }
    }

}
