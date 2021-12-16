package com.example.appcentertask2

import retrofit2.Call
import retrofit2.http.*

interface UrlService {
    @FormUrlEncoded
    @POST("shorturl")
    fun requestTranslation (@Header("X-Naver-Client-Id")clientID: String = Configs.clientID,
                            @Header("X-Naver-Client-Secret")apiKey: String = Configs.apiKey,
                            @Field("url")originalUrl: String = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EA%B0%9C%EB%B0%9C%EC%9E%90%EC%84%BC%ED%84%B0") : Call<UrlEntity>
}