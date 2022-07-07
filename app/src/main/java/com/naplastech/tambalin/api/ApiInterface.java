package com.naplastech.tambalin.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login/")
    Call<LoginRequest> tesLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("regPengendara/")
    Call<PengendaraReq> registrasiPengendara(@Field("nama") String nama,
                                             @Field("kota") String kota,
                                             @Field("jenis_ban") Integer jenis_ban,
                                             @Field("no_hp") String nomor_hp,
                                             @Field("password") String password,
                                             @Field("role") Integer role);

}
