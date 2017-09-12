package zone.com.sdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import zone.com.sdk.API.login.api.LoginAPI;
import zone.com.sdk.API.login.api.LoginImpl;
import zone.com.sdk.API.news.api.NewsAPI;
import zone.com.sdk.API.news.api.NewsImpl;
import zone.com.sdk.API.notifications.api.NotificationsAPI;
import zone.com.sdk.API.notifications.api.NotificationsImpl;
import zone.com.sdk.API.sites.api.SitesAPI;
import zone.com.sdk.API.sites.api.SitesImpl;
import zone.com.sdk.API.token.api.TokenAPI;
import zone.com.sdk.API.token.api.TokenImpl;
import zone.com.sdk.API.topic.api.TopicAPI;
import zone.com.sdk.API.topic.api.TopicImpl;
import zone.com.sdk.API.user.api.UserAPI;
import zone.com.sdk.API.user.api.UserImpl;

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
        entityList.add(new Entity(TopicAPI.class, new TopicImpl()));
        entityList.add(new Entity(NewsAPI.class, new NewsImpl()));
        entityList.add(new Entity(SitesAPI.class, new SitesImpl()));
        entityList.add(new Entity(UserAPI.class, new UserImpl()));
        entityList.add(new Entity(NotificationsAPI.class, new NotificationsImpl()));
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
//        proxy.getSites()
//                .enqueueObservable()
//                .subscribe(new Consumer<List<Sites>>() {
//                    @Override
//                    public void accept(List<Sites> sites) throws Exception {
//                        System.out.println(sites);
//                    }
//                });
    }

}
