package com.gmz.foursquare.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gmz.foursquare.R
import com.gmz.foursquare.databinding.FragmentLoginBinding
import com.gmz.foursquare.viewModel.webView.WebViewViewModel
import com.gmz.foursquare.viewModel.webView.WebViewViewModelFactory
import com.gmz.foursquare.viewModel.login.LoginViewModel
import com.gmz.foursquare.viewModel.login.LoginViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var webViewViewModel: WebViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        createViewModels()
        if (!loginViewModel.isLoggedIn()) {
            loadAuthUrl()
            addAuthCompletedListener()
        }
    }

    private fun addAuthCompletedListener() {
        loginViewModel.authCompleted.observe(this) { it ->
            if (it) {
                findNavController().navigate(R.id.action_loginToLikedVenus)
            }
        }
    }

    private fun createViewModels() {
        activity?.let {
            loginViewModel =
                ViewModelProvider(
                    this,
                    LoginViewModelFactory(it.application)
                ).get(LoginViewModel::class.java)
        }
        webViewViewModel = ViewModelProvider(
            this,
            WebViewViewModelFactory(binding.wvLoginLoginPage, binding.pbLoginProgress)
        )[WebViewViewModel::class.java]
    }

    private fun loadAuthUrl() {
        webViewViewModel.configureWebView()
        var authUrl = loginViewModel.getAuthUrl()
        println(authUrl)
        webViewViewModel.loadUrl(authUrl)
        addRedirectListener()
    }

    private fun addRedirectListener() {
        binding.wvLoginLoginPage.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    return loginViewModel.checkRedirectUrl(url)
                }
                return false;
            }
        })
    }
}