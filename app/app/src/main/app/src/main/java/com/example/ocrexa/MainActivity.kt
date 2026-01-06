package com.example.ocrexa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.openFormBtn)
        btn.setOnClickListener {
            startActivity(
                Intent(this, FormActivity::class.java)
            )
        }
    }
}
