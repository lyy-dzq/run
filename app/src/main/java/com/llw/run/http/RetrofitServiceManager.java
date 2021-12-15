package com.llw.run.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    private static final int DEFAULT_CONNECT_TIME = 20;
    private static final int DEFAULT_WRITE_TIME = 30;
    private static final int DEFAULT_READ_TIME = 30;
    private final OkHttpClient okHttpClient;
    public static final String REQUEST_PATH = "http://101.35.202.198:8080/";
    private final Retrofit retrofit;

    private RetrofitServiceManager() {

        HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
                .addInterceptor(LoginInterceptor)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + CommonUtil.getStringFromSP(Constant.TOKEN)).build();
//                        return chain.proceed(request);
//                    }
//                })
                .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)//设置读操作超时时间
                .build();


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)//设置使用okhttp网络请求
                .baseUrl(REQUEST_PATH)//设置服务器路径
                .addConverterFactory(GsonConverterFactory.create())//添加转化库，默认是Gson
                .addConverterFactory(StringConverterFactory.create())//添加转化库，默认是Gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加回调库，采用RxJava
                .build();

    }

//    private static class SingletonHolder {
//        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
//    }

    static RetrofitServiceManager retrofitServiceManager;

    /*
     * 获取RetrofitServiceManager
     **/
    public static RetrofitServiceManager getInstance() {

        if (retrofitServiceManager == null) {
            retrofitServiceManager = new RetrofitServiceManager();
        }


        return retrofitServiceManager;
    }

    public static void init() {
        retrofitServiceManager = null;

    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
