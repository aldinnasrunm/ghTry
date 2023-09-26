package com.aldin.ghtry.Interfaces

import retrofit2.Retrofit




object githubAPI {

    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()

    var service: GithubService = retrofit.create(GithubService::class.java)

}