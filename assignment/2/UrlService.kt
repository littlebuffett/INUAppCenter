package com.example.appcentertask2

import retrofit2.Call
import retrofit2.http.*

interface UrlService {
    @FormUrlEncoded
    @POST("shorturl")
    fun requestTranslation (@Header("X-Naver-Client-Id")clientID: String = Configs.clientID,
                            @Header("X-Naver-Client-Secret")apiKey: String = Configs.apiKey,
                            @Field("url")originalUrl: String = inputUrl ) : Call<UrlEntity>
}
