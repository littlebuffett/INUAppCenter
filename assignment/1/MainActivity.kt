package edu.ourincheon.inuappcenterprac1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val waringMessage:TextView = findViewById(R.id.tv_notCorrectMsg)
        val loginButton :Button = findViewById(R.id.btn_login)
        loginButton.setOnClickListener {
            waringMessage.visibility = View.VISIBLE
        }
    }
}
