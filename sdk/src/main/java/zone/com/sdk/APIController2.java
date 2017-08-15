package zone.com.sdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import zone.com.sdk.API.gank.api.GankAPI;
import zone.com.sdk.API.gank.api.GankImpl;
import zone.com.sdk.API.gank2.api.Gank2API;
import zone.com.sdk.API.gank2.api.Gank2Impl;

/**
 * [2017] by Zone
 */

public class APIController2 {

    static APIController2 instance;

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
        instance = new APIController2();
        entityList.add(new Entity(GankAPI.class, new GankImpl()));
        entityList.add(new Entity(Gank2API.class, new Gank2Impl()));
    }

    public static APIController2 getSingleInstance() {
        return instance;
    }

    public static void main(String[] args) {
        APIController2 aPIController = new APIController2();
        InvocationHandler handler = new DyHandler(aPIController);

        Class<?>[] interfaces = new Class[entityList.size()];
        for (int i = 0; i < entityList.size(); i++) {
            interfaces[i] = entityList.get(i).interfaceClass;
        }
        //创建动态代理对象
        APICall proxy = (APICall) Proxy.newProxyInstance(
                aPIController.getClass().getClassLoader(),
                new Class<?>[]{APICall.class}, handler);
        proxy.getPics("5","5", 2);
        proxy.getPics("5", "5");
    }

}
