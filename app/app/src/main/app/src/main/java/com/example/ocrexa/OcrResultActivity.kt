package com.example.ocrexa

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OcrResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = intent.getStringExtra("ocr_text") ?: "Текст не знайдено"

        val textView = TextView(this).apply {
            this.text = text
            textSize = 16f
            setPadding(24, 24, 24, 24)
        }

        val scrollView = ScrollView(this).apply {
            addView(textView)
        }

        setContentView(scrollView)
        title = "Результат OCR"
    }
}
