package com.aldin.ghtry.Interfaces

import com.aldin.ghtry.Models.SearchResponse
import com.aldin.ghtry.Models.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getListUsers(): Call<List<UserData>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ) : Call<SearchResponse>


    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserData(
        @Path("username") username: String
    ): Call<UserData>


    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserData>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserData>>


}