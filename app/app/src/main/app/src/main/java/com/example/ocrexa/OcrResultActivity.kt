package com.example.ocrexa

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OcrResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ocrText = intent.getStringExtra("ocr_text") ?: ""
        val result = AnswerChecker.check(ocrText)

        val total = result.found.size + result.missing.size
        val percent = if (total > 0) (result.found.size * 100) / total else 0
        val passed = percent >= 60

        val statusText = if (passed) "СКЛАВ" else "НЕ СКЛАВ"
        val statusColor = if (passed) Color.parseColor("#2E7D32") else Color.parseColor("#C62828")

        val reportText = buildString {
            append("РЕЗУЛЬТАТ ІСПИТУ\n")
            append("====================\n")
            append("Статус: $statusText\n")
            append("Результат: $percent%\n\n")

            append("ЗНАЙДЕНО:\n")
            if (result.found.isEmpty()) append("— немає\n")
            else result.found.forEach { append("✔ $it\n") }

            append("\nНЕ ЗНАЙДЕНО:\n")
            if (result.missing.isEmpty()) append("— немає\n")
            else result.missing.forEach { append("✘ $it\n") }

            append("\n--------------------\n")
            append("OCR ТЕКСТ:\n")
            append(ocrText)
        }

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
            setPadding(0, 16, 0, 16)
        }

        val detailView = TextView(this).apply {
            text = reportText
            textSize = 14f
        }

        val saveBtn = Button(this).apply {
            text = "Зберегти результат у файл"
            setOnClickListener {
                saveToFile(reportText)
            }
        }

        container.addView(statusView)
        container.addView(percentView)
        container.addView(saveBtn)
        container.addView(detailView)

        val scroll = ScrollView(this).apply {
            addView(container)
        }

        setContentView(scroll)
        title = "Результат іспиту"
    }

    private fun saveToFile(text: String) {
        try {
            val time = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Date())

            val file = File(filesDir, "exam_result_$time.txt")
            file.writeText(text)

            Toast.makeText(
                this,
                "Файл збережено:\n${file.name}",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Помилка збереження", Toast.LENGTH_SHORT).show()
        }
    }
}
