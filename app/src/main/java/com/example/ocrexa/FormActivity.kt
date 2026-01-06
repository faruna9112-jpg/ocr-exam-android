package com.example.ocrexa

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

class FormActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private val ADMIN_PIN = "1234" // 🔐 ЗМІНИ, ЯК ПОТРІБНО

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // УВІМКНЕННЯ KIOSK
        startLockTask()

        // Контейнер
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        // Заголовок з таймером (довге натискання = PIN)
        val header = TextView(this).apply {
            text = "Екзамен"
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setOnLongClickListener {
                showPinDialog()
                true
            }
        }

        // WebView з Google Form
        val webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://docs.google.com/forms/d/e/ВАШ_ID_ФОРМИ/viewform")
        }

        root.addView(header)
        root.addView(webView, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ))

        setContentView(root)

        startExamTimer(header)
    }

    private fun startExamTimer(header: TextView) {
        val examTime = 15 * 60 * 1000L // ⏱️ 15 хв

        timer = object : CountDownTimer(examTime, 1000) {
            override fun onTick(ms: Long) {
                val m = ms / 1000 / 60
                val s = (ms / 1000) % 60
                header.text = "Час: %02d:%02d  (утримай для PIN)".format(m, s)
            }

            override fun onFinish() {
                Toast.makeText(this@FormActivity, "Час вийшов", Toast.LENGTH_LONG).show()
                finishExam()
            }
        }.start()
    }

    private fun showPinDialog() {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            hint = "Введіть PIN"
        }

        AlertDialog.Builder(this)
            .setTitle("Адмін-вихід")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                if (input.text.toString() == ADMIN_PIN) {
                    finishExam()
                } else {
                    Toast.makeText(this, "Невірний PIN", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    private fun finishExam() {
        stopLockTask()
        finish()
    }

    override fun onBackPressed() {
        // Заблоковано
    }

    override fun onDestroy() {
        if (::timer.isInitialized) timer.cancel()
        stopLockTask()
        super.onDestroy()
    }
}
