package com.example.appcentertask2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UrlServiceCreator {

    val BASE_URL = "https://openapi.naver.com/v1/util/shorturl/" // JSON 출력

    fun create() : UrlService { //서버와 통신하기 위한 Service객체를 생성해주는 역할.
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UrlService::class.java)
    }
}
