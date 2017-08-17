package zone.com.sdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import zone.com.sdk.API.login.api.LoginAPI;
import zone.com.sdk.API.login.api.LoginImpl;
import zone.com.sdk.API.token.api.TokenAPI;
import zone.com.sdk.API.token.api.TokenImpl;

/**
 * [2017] by Zone
 */

public class Diycode {

    public static List<Entity> entityList = new ArrayList<>();

    public static class Entity {
        public final Class interfaceClass;
        public final Object instance;

        public Entity(Class interfaceClass, Object instance) {
            this.interfaceClass = interfaceClass;
            this.instance = instance;
        }
    }

    static {
        entityList.add(new Entity(TokenAPI.class, new TokenImpl()));
        entityList.add(new Entity(LoginAPI.class, new LoginImpl()));
    }

    static APICall mAPICall;

    public static synchronized APICall getInstance() {

        if (mAPICall == null) {
            synchronized (Diycode.class) {
                if (mAPICall == null) {
                    mAPICall = generate();
                }
            }
        }
        return mAPICall;
    }


    private static APICall generate() {
        Diycode aPIController = new Diycode();
        InvocationHandler handler = new DyHandler(aPIController);

        Class<?>[] interfaces = new Class[entityList.size()];
        for (int i = 0; i < entityList.size(); i++) {
            interfaces[i] = entityList.get(i).interfaceClass;
        }
        //创建动态代理对象
        APICall proxy = (APICall) Proxy.newProxyInstance(
                aPIController.getClass().getClassLoader(),
                new Class<?>[]{APICall.class}, handler);
        return proxy;
    }


    public static void main(String[] args) {
        Diycode aPIController = new Diycode();
        InvocationHandler handler = new DyHandler(aPIController);

        Class<?>[] interfaces = new Class[entityList.size()];
        for (int i = 0; i < entityList.size(); i++) {
            interfaces[i] = entityList.get(i).interfaceClass;
        }
        //创建动态代理对象
        APICall proxy = (APICall) Proxy.newProxyInstance(
                aPIController.getClass().getClassLoader(),
                new Class<?>[]{APICall.class}, handler);

//        proxy.getPics("5", "5", 2);
//        proxy.getPics("5", "5");
    }

}
