package zone.com.retrofitlib.https.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * [2017] by Zone
 * <p>
 * builder.sslSocketFactory(Insecure.createInsecureTrustManager2()).
 * hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
 */
public class Insecure {

    //    客户端不内置证书
    public static void createInsecureTrustManager2(OkHttpClient.Builder okHttpClient) {
        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        TrustManager[] trustManagers = null;
        try {
            trustManagers = new TrustManager[]{createInsecureTrustManager()};
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        if (trustManagers != null && trustManagers.length > 0)
            okHttpClient.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0]);
        else
            okHttpClient.sslSocketFactory(sslContext.getSocketFactory());
        okHttpClient.hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }


    /**
     * 为了解决客户端不信任服务器数字证书的问题，网络上大部分的解决方案都是让客户端不对证书做任何检查，
     * 这是一种有很大安全漏洞的办法
     */
    private static X509TrustManager createInsecureTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }
}
