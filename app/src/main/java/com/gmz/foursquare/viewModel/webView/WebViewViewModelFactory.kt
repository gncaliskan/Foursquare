package com.gmz.foursquare.viewModel.webView

import android.webkit.WebView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WebViewViewModelFactory(
    private val webView: WebView,
    private val progressBar: ProgressBar,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebViewViewModel::class.java)) {
            return WebViewViewModel(webView, progressBar) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}