package com.example.ocrexa

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OcrResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ocrText = intent.getStringExtra("ocr_text") ?: ""
        val result = AnswerChecker.check(ocrText)

        val total = result.found.size + result.missing.size
        val percent = if (total > 0) {
            (result.found.size * 100) / total
        } else {
            0
        }

        val passed = percent >= 60

        val statusText = if (passed) "✅ СКЛАВ" else "❌ НЕ СКЛАВ"
        val statusColor = if (passed) Color.parseColor("#2E7D32") else Color.parseColor("#C62828")

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        val statusView = TextView(this).apply {
            text = statusText
            textSize = 28f
            setTextColor(statusColor)
        }

        val percentView = TextView(this).apply {
            text = "Результат: $percent%"
            textSize = 20f
            setPadding(0, 16, 0, 24)
        }

        val detailView = TextView(this).apply {
            textSize = 15f
            text = buildString {
                append("🔤 РОЗПІЗНАНИЙ ТЕКСТ:\n\n")
                append(ocrText)
                append("\n\n====================\n\n")

                append("✅ ЗНАЙДЕНО:\n")
                if (result.found.isEmpty()) {
                    append("— немає\n")
                } else {
                    result.found.forEach { append("✔ $it\n") }
                }

                append("\n❌ НЕ ЗНАЙДЕНО:\n")
                if (result.missing.isEmpty()) {
                    append("— немає\n")
                } else {
                    result.missing.forEach { append("✘ $it\n") }
                }
            }
        }

        container.addView(statusView)
        container.addView(percentView)
        container.addView(detailView)

        val scroll = ScrollView(this).apply {
            addView(container)
        }

        setContentView(scroll)
        title = "Результат іспиту"
    }
}
