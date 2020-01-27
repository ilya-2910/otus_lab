import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private List<Method> befores = new ArrayList<>();
    private List<Method> afters = new ArrayList<>();
    private List<Method> tests = new ArrayList<>();

    private enum Status {
        SUCCESS,
        BEFORE_ERROR,
        IN_TEST_ERROR,
        AFTER_ERROR,
    }

    public void test(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getMethods();

            collectMethods(methods);

            int successedCount = 0;
            for (Method test : tests) {
                Object instance = clazz.getDeclaredConstructors()[0].newInstance();

                Status status = Status.SUCCESS;
                try {
                    for (Method before : befores) {
                        before.invoke(instance);
                    }
                } catch (Exception e) {
                    status = Status.BEFORE_ERROR;
                }

                try {
                    test.invoke(instance);
                } catch (Exception e) {
                    status = Status.IN_TEST_ERROR;
                }

                if (Status.IN_TEST_ERROR != status) {
                    try {
                        for (Method after : afters) {
                            after.invoke(instance);
                        }
                    } catch (Exception e) {
                        status = Status.AFTER_ERROR;
                    }
                }

                if (status == Status.SUCCESS) successedCount++;
            }
            System.out.println("all=" + tests.size());
            System.out.println("successedCount=" + successedCount);
            System.out.println("failedCount=" + (tests.size() - successedCount));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void collectMethods(Method[] methods) {
        befores.clear();
        tests.clear();
        afters.clear();

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
    }


}
