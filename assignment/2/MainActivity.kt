package com.example.appcentertask2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response

 var inputUrl:String =""

class MainActivity : AppCompatActivity() {

    private lateinit var et_inputUrl: EditText
    private lateinit var et_shortUrl: EditText
    private lateinit var btn_change: Button
    private lateinit var shortUrl: String
    private lateinit var iv_clear:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_inputUrl = findViewById(R.id.et_inputUrl)
        et_shortUrl = findViewById(R.id.et_shortUrl)

        btn_change = findViewById(R.id.btn_change)
        btn_change.setOnClickListener(changeBtnListener())

        iv_clear = findViewById(R.id.iv_clear)
        iv_clear.setOnClickListener {
            et_inputUrl.setText("")
            et_shortUrl.setText("")
            et_inputUrl.requestFocus()
        }
    }

    inner class changeBtnListener : View.OnClickListener {
        override fun onClick(v: View?) {
            inputUrl = et_inputUrl.text.toString()
            val urlService = UrlServiceCreator().create()
            val call = urlService.requestTranslation()
            call.enqueue(object : retrofit2.Callback<UrlEntity> {

                override fun onResponse(call: Call<UrlEntity>, response: Response<UrlEntity>) {
                    if (response.isSuccessful) {

                        val result = response.body()
                        shortUrl = result?.result?.url.toString()
                        // 첫 번째 result는 Response<>객체의 body. 두 번째 result는 UrlEntity의 변수 result이다.
                        // UrlEntity의 변수 result는  inner class Result 객체이고, 이 객체의 변수 url을 참조한 것이다.
                        et_shortUrl.setText(shortUrl)
                        copyToClip(shortUrl) //단축 url을 클립보드에 복사.

                        Toast.makeText(this@MainActivity, "클립보드에 복사되었습니다", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("요청 성공", shortUrl)

                    } else {
                        Log.d("요청 실패", "!response.isSuccessful")
                        Toast.makeText(this@MainActivity, "주소를 다시 확인해주세요", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<UrlEntity>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "주소를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
                    Log.d("실패", "onFailure")
                    t.printStackTrace()
                }
            })

            //단축 url editText에 포커스 주기
            et_shortUrl.requestFocus()

            //키보드 내리기
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }
    }

    fun copyToClip(url: String) {
        var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //클립보드 매니저 객체 생성.
        val clip: ClipData = ClipData.newPlainText("short url", url)
        clipboardManager.setPrimaryClip(clip)
    }

}
