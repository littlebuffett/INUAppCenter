package com.example.appcentertask2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UrlServiceCreator {

    //안드로이드에서 api 요청을 보낼 때 URL맨 끝은 '/'로 끝이나야 한다!
    val BASE_URL = "https://openapi.naver.com/v1/util/" 

    fun create() : UrlService { //서버와 통신하기 위한 Service객체를 생성해주는 역할.
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UrlService::class.java)
    }
}
}
