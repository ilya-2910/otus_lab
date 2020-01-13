import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        TestRunner.test("TestTest");
    }

    private static List<Method> befores = new ArrayList<>();
    private static List<Method> afters = new ArrayList<>();
    private static List<Method> tests = new ArrayList<>();

    public static void test(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getMethods();

            for(Method mt : methods) {
                if (mt.isAnnotationPresent(Before.class)) {
                    befores.add(mt);
                }

                if (mt.isAnnotationPresent(Test.class)) {
                    tests.add(mt);
                }

                if (mt.isAnnotationPresent(After.class)) {
                    afters.add(mt);
                }
            }

            int allCount = 0;
            int successedCount = 0;
            int failedCount = 0;
            for (Method test : tests) {
                try {
                    Object instance = clazz.getDeclaredConstructors()[0].newInstance();
                    for (Method before : befores) {
                        before.invoke(instance);
                    }

                    test.invoke(instance);

                    for (Method after : afters) {
                        after.invoke(instance);
                    }
                    successedCount++;
                } catch (Exception e) {
                    failedCount++;
                }
            }
            System.out.println("all=" + tests.size());
            System.out.println("successedCount=" + successedCount);
            System.out.println("failedCount=" + failedCount);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
