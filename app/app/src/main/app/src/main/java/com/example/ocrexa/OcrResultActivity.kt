package com.example.ocrexa

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OcrResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ocrText = intent.getStringExtra("ocr_text") ?: ""
        val result = AnswerChecker.check(ocrText)

        val displayText = StringBuilder()

        displayText.append("🔤 РОЗПІЗНАНИЙ ТЕКСТ:\n\n")
        displayText.append(ocrText)
        displayText.append("\n\n====================\n\n")

        displayText.append("✅ ЗНАЙДЕНО:\n")
        if (result.found.isEmpty()) {
            displayText.append("— немає\n")
        } else {
            result.found.forEach { displayText.append("✔ $it\n") }
        }

        displayText.append("\n❌ НЕ ЗНАЙДЕНО:\n")
        if (result.missing.isEmpty()) {
            displayText.append("— немає\n")
        } else {
            result.missing.forEach { displayText.append("✘ $it\n") }
        }

        displayText.append("\n📊 РЕЗУЛЬТАТ: ${result.found.size} / ${result.found.size + result.missing.size}")

        val textView = TextView(this).apply {
            text = displayText.toString()
            textSize = 15f
            setPadding(24, 24, 24, 24)
        }

        val scrollView = ScrollView(this).apply {
            addView(textView)
        }

        setContentView(scrollView)
        title = "Перевірка результатів"
    }
}
