package com.gmz.foursquare.viewModel.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmz.foursquare.data.api.DataService
import com.gmz.foursquare.data.shared.PreferencesManager
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val dataService = DataService.getLoginService()
    val authCompleted = MutableLiveData<Boolean>()
    val preferencesManager = PreferencesManager(application)

    companion object UrlComponents {
        const val AUTH_URL = "https://foursquare.com/oauth2/authenticate"
        const val CLIENT_ID = "DCR123GJUF0HZSKGSN4HPBULCQPABMHEUKMEMGEQPASNTK0E"
        const val CLIENT_SECRET = "O3ZBTGLDMI0SW2PJGZHVSCKHA3SEUDCTB0TRMWJK2P0DSTKE"
        const val RESPONSE_TYPE = "code"
        const val GRANT_TYPE = "authorization_code"
        const val REDIRECT_URI = "https://github.com/gncaliskan/caseStudy"
    }

    fun isLoggedIn(): Boolean {
        var isLogged = false
        viewModelScope.launch {
            preferencesManager.getToken().collect { it ->
                isLogged = !it.isNullOrEmpty()
            }
        }
        return isLogged
    }

    fun getAuthUrl(): String {
        var url: StringBuilder = StringBuilder()
        url.append(AUTH_URL)
            .append("?client_id=").append(CLIENT_ID)
            .append("&response_type=").append(RESPONSE_TYPE)
            .append("&redirect_uri=").append(REDIRECT_URI)
        return url.toString()
    }

    fun checkRedirectUrl(url: String): Boolean {
        if (url.indexOf(REDIRECT_URI) == 0) {
            getAccessTokenUrl(getUrlCode(url))
            return true;
        }
        return false
    }

    private fun getUrlCode(url: String): String {
        return url.substring(url.indexOf("code=") + 5, url.indexOf("#"))
    }

    fun getAccessTokenUrl(code: String) {
        viewModelScope.launch {
            var accessToken = dataService.getAccessToken(
                CLIENT_ID, CLIENT_SECRET,
                GRANT_TYPE, REDIRECT_URI, code
            )
            accessToken?.let {
                println(accessToken.body())
                accessToken.body()?.accessToken?.let { it1 -> preferencesManager.setToken(it1) }
                authCompleted.value = true
            }
        }
    }

}