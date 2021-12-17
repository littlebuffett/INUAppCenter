package com.example.appcentertask2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response

var inputUrl:String = "" // 다른 클래스 밖에서도 참조해야 하기 때문에 클래스 바깥에 선언.

class MainActivity : AppCompatActivity() {

    private lateinit var et_inputUrl:EditText
    private lateinit var et_shortUrl:EditText
    private lateinit var btn_change:Button
    private lateinit var shortUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_inputUrl = findViewById(R.id.et_inputUrl)
        et_shortUrl = findViewById(R.id.et_shortUrl)
        btn_change = findViewById(R.id.btn_change)

        btn_change.setOnClickListener {
            inputUrl = et_inputUrl.text.toString()
            val urlService = UrlServiceCreator().create()
            val call = urlService.requestTranslation()
            call.enqueue(object: retrofit2.Callback<UrlEntity>{

                override fun onResponse(call: Call<UrlEntity>, response: Response<UrlEntity>) {
                    if(response.isSuccessful){

                        val result = response.body()
                        shortUrl = result?.result?.url.toString()
                        // 첫 번째 result는 Response<>객체의 body. 두 번째 result는 UrlEntity의 변수 result이다.
                        // UrlEntity의 변수 result는  inner class Result 객체이고, 이 객체의 변수 url을 참조한 것이다.
                        et_shortUrl.setText(shortUrl)
                        copyToClip(shortUrl) //단축 url을 클립보드에 복사.

                        Toast.makeText(this@MainActivity,"클립보드에 복사되었습니다.",Toast.LENGTH_SHORT).show()
                        Log.d("요청 성공", shortUrl)

                    } else{
                        Log.d("요청 실패", "!response.isSuccessful")
                    }
                }

                override fun onFailure(call: Call<UrlEntity>, t: Throwable) {
                    Log.d("실패", "onFailure")
                    t.printStackTrace()
                }
            })
        }
    }

    fun copyToClip(url:String){
        var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //클립보드 매니저 객체 생성.
        val clip : ClipData = ClipData.newPlainText("short url",url)
        clipboardManager.setPrimaryClip(clip)
    }
}
