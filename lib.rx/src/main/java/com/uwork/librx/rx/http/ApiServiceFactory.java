package com.uwork.librx.rx.http;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 李栋杰
 * @time 2017/7/29  10:09
 * @desc ${TODD}
 */
public enum ApiServiceFactory {
    INSTANCE;

    private static final String TAG = "ApiServiceFactory";

    public static final int RETRY_TIMES = 5;
    private static final int DEFAULT_TIMEOUT = 15;
    private static final int DEFAULT_READ_TIMEOUT = 30;
    private final Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, Object> apiServiceMap;


    public <T> T create(Context context, String baseUrl, Class<T> clz) {
        Retrofit retrofit = retrofitMap.get(baseUrl);
        if (retrofit == null) {
            synchronized (this) {
                if (retrofit == null) {
                    //打印拦截器
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    //logging.setLevel(GApplication.getInstance().isDebug()?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);


                    // 公私密匙
                    //MarvelSigningInterceptor signingInterceptor = new MarvelSigningInterceptor(KeyValue.MARVEL_PUBLIC_KEY, KeyValue.MARVEL_PRIVATE_KEY);
                    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

                    okHttpClient.addNetworkInterceptor(new HTTPInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)//设置出现错误进行重新连接。;
                            //.addInterceptor(signingInterceptor)//加密处理
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory())
//                            .sslSocketFactory(MySSLSocketFactory.getSocketFactory(context))
                            .hostnameVerifier(new TrustAllHostnameVerifier())
                            .addNetworkInterceptor(logging);

                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okHttpClient.build())

                            .build();
                    retrofitMap.put(baseUrl, retrofit);
                    if (apiServiceMap == null) {
                        apiServiceMap = new HashMap<String, Object>();
                    }
                    T service = retrofit.create(clz);
                    apiServiceMap.put(baseUrl + ":" + clz.getName(), service);
                    return service;
                }

            }

        }
        T objects = (T) apiServiceMap.get(baseUrl + ":" + clz.getName());
        return objects;
    }


    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            Log.e("SSLContext", "checkClientTrusted: ");
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            Log.e("SSLContext", "checkServerTrusted: ");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            Log.e("SSLContext", "getAcceptedIssuers: ");
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static X509TrustManager createTrustManagerFactory() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            trustManager = (X509TrustManager) trustManagers[0];
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException kse) {
            kse.printStackTrace();
        }
        return trustManager;
    }
}
