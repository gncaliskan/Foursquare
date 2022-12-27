package com.gmz.foursquare.viewModel.webView

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel

class WebViewViewModel(webView: WebView, progressBar: ProgressBar) : ViewModel() {
    private val webView: WebView = webView
    private val progressBar: ProgressBar = progressBar

    fun configureWebView() {
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }

    fun loadUrl(url: String) {
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
    }

}