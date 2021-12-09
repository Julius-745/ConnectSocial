package com.julius745.connect.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class BackendService {
    static public BackendInterface service;

    // Mempreparasi Backend untuk koneksi ke server
    static public void initService(Context context) {
        //Moshi2 merupakan build JSON parsher
        Moshi moshi = new Moshi.Builder().build();
        //Build OkhttpClient merupakan Http Client
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //Biar coockienya bekerja
        builder.addInterceptor(new AddCookiesInterceptor(context)); // VERY VERY IMPORTANT
        builder.addInterceptor(new ReceivedCookiesInterceptor(context)); // VERY VERY IMPORTANT
        //Fungsi ini membuat data tercatat di logcat
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Untuk melimit waktu jika koneksi tidak terhubung
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(logging);
        OkHttpClient client = builder.build();
        Retrofit.Builder builder2 = new Retrofit.Builder();
        builder2.client(client).baseUrl("https://connectsocial.domcloud.io/");
        builder2.addConverterFactory(MoshiConverterFactory.create(moshi));
        Retrofit retrofit = builder2.build();
        service = retrofit.create(BackendInterface.class);
    }
}

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may ary, but this will work 99% of the time.
 */
//Ini merupakan copypaste dari kitab suci
class AddCookiesInterceptor implements Interceptor {
    public static final String PREF_COOKIES = "PREF_COOKIES";
    // We're storing our stuff in a database made just for cookies called PREF_COOKIES.
    // I reccomend you do this, and don't change this default value.
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES, new HashSet<String>());

        // Use the following if you need everything in one line.
        // Some APIs die if you do it differently.
        /*String cookiestring = "";
        for (String cookie : preferences) {
            String[] parser = cookie.split(";");
            cookiestring = cookiestring + parser[0] + "; ";
        }
        builder.addHeader("Cookie", cookiestring);
        */

        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}

class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    } // AddCookiesInterceptor()
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet("PREF_COOKIES", new HashSet<String>());

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(context).edit();
            memes.putStringSet("PREF_COOKIES", cookies).apply();
            memes.commit();
        }

        return originalResponse;
    }
}