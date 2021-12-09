package com.julius745.connect.data;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BackendInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<Map> login(@Field("email") String email, @Field("pass") String pass);
    @FormUrlEncoded
    @POST("register.php")
    Call<Map> register(@Field("name") String name, @Field("email") String email, @Field("password") String pass);
    @GET("post/get.php")
    Call<Map> postGet(@Query("id") String id);
    @GET("post/list.php")
    Call<List<Map>> postList(@Query("offset") Integer offset);
    @Multipart
    @POST("post/list.php")
    Call<Map> postNew(@Part("title") String title, @Part("content") String content, @Part MultipartBody.Part image);
    @FormUrlEncoded
    @POST("post/like.php")
    Call<Map> postLike(@Query("id") String id, @Field("liked") String liked);
    @FormUrlEncoded
    @POST("user/edit.php")
    Call<Map> userEdit(@Field("name") String name, @Field("email") String email,
                              @Field("password") String password, @Field("lang") String lang);
}
