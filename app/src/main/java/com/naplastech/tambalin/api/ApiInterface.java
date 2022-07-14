package com.naplastech.tambalin.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login/")
    Call<LoginRequest> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("registrasi/pengendara/")
    Call<InsertDataP> registrasiPengendara(@Field("nama") String nama,
                                           @Field("kota") String kota,
                                           @Field("no_hp") String nomor_hp,
                                           @Field("password") String password,
                                           @Field("role") Integer role);

    @FormUrlEncoded
    @POST("registrasi/mitra/")
    Call<InsertDataM> registrasiMitra(@Field("nama") String nama,
                                           @Field("kota") String kota,
                                           @Field("no_hp") String nomor_hp,
                                           @Field("password") String password,
                                           @Field("role") Integer role);

    @FormUrlEncoded
    @POST("mitra/bengkel/")
    Call<EditBengkel> editBengkel(@Field("user_id") Integer user_id,
                                  @Field("nama_bengkel") String nama_bengkel,
                                  @Field("alamat") String alamat,
                                  @Field("jenis_ban") Integer jenis_ban,
                                  @Field("layanan_tambahan") Integer layanan_tambahan);

    @FormUrlEncoded
    @POST("registrasi/cek_nomor/")
    Call<CekNomor> cekNomor(@Field("no_hp") String no_hp);

    @FormUrlEncoded
    @POST("pengendara/get_data/")
    Call<Pengendara> dataPengendara(@Field("user_id") Integer user_id);

    @FormUrlEncoded
    @POST("mitra/get_data/")
    Call<Mitra> dataMitra(@Field("user_id") Integer user_id);
}
