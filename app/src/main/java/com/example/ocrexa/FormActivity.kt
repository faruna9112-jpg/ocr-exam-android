package com.example.ocrexa

import android.os.Bundle
import android.os.CountDownTimer
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // УВІМКНЕННЯ KIOSK
        startLockTask()

        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        setContentView(webView)

        webView.loadUrl(
            "https://docs.google.com/forms/d/e/ВАШ_ID_ФОРМИ/viewform"
        )

        startExamTimer()
    }

    private fun startExamTimer() {
        // ⏱️ 15 ХВИЛИН (у мілісекундах)
        val examTime = 15 * 60 * 1000L

        timer = object : CountDownTimer(examTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                title = "Час: %02d:%02d".format(minutes, seconds)
            }

            override fun onFinish() {
                Toast.makeText(
                    this@FormActivity,
                    "Час вийшов. Тест завершено.",
                    Toast.LENGTH_LONG
                ).show()

                finishExam()
            }
        }.start()
    }

    private fun finishExam() {
        stopLockTask()
        finish()
    }

    override fun onBackPressed() {
        // Заблоковано
    }

    override fun onDestroy() {
        timer.cancel()
        stopLockTask()
        super.onDestroy()
    }
}
