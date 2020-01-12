import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Interceptor <T> implements InvocationHandler {

    private T realObject;

    private List<Method> methods;

    public static Logging createMyClass() {
        return Interceptor.getProxy(new TestLogging(), Logging.class);
    }

    public Interceptor(T t) {
        this.realObject = t;
        methods = Arrays.stream(t.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methods.contains(realObject.getClass().getMethod(method.getName(), method.getParameterTypes()))) {
            System.out.println("executed method: calculation, param: " + args[0]);
        }
        Object result = method.invoke(realObject, args);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T t, Class<? super T> interfaceType) {
        Interceptor handler = new Interceptor(t);
        if (handler.getMethods().size() > 0) {
            return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                    new Class<?>[]{interfaceType}, handler
            );
        } else {
            return t;
        }
    }

    public List<Method> getMethods() {
        return methods;
    }
}
