package ru.otus.cachehw;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CacheInterceptor<T, C> implements InvocationHandler {

    private T realObject;

    private List<Method> methods;

    HwCache<String, C> cache = new MyCache<>();

    public CacheInterceptor(T t) {
        this.realObject = t;
        methods = Arrays.stream(t.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Cacheble.class))
                .collect(Collectors.toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methods.contains(realObject.getClass().getMethod(method.getName(), method.getParameterTypes()))) {
            String key = args[0].toString();
            C cachedValue = cache.get(key);
            if (cachedValue != null) {
                System.out.println("cachedValue: " + "(param:" + args[0] + ",cachedValue:" + cachedValue);
                return cachedValue;
            } else {
                System.out.println("get cachedValue from source");
                C value = (C) method.invoke(realObject, args);
                cache.put(key, value);
                return value;
            }
        } else {
            return method.invoke(realObject, args);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T t, Class<? super T> interfaceType) {
        CacheInterceptor handler = new CacheInterceptor(t);
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
