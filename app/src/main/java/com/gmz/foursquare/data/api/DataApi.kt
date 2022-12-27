package com.gmz.foursquare.data.api

import com.gmz.foursquare.data.entities.AccessToken
import com.gmz.foursquare.data.entities.LikedVenuesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {
    @GET("oauth2/access_token?")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("grant_type") grantType: String,
        @Query(value ="redirect_uri", encoded = true) redirectUri: String,
        @Query("code") code: String
    ): Response<AccessToken>

    @GET("users/self/venuelikes?")
    suspend fun getLikedVenues(
        @Query("oauth_token") token: String,
        @Query("v") version: String?
    ) : Response<LikedVenuesResponse>
}