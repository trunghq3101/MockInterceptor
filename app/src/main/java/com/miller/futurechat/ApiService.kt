package com.miller.futurechat

import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("http://mock.api/something")
    fun getSomething(): Single<SomeThing>
}