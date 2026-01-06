package com.example.ocrexa

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

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
    }

    override fun onBackPressed() {
        // БЛОКУЄМО КНОПКУ BACK
    }

    override fun onDestroy() {
        stopLockTask()
        super.onDestroy()
    }
}
