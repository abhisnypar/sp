package com.spidugu.nycshools.api

import retrofit2.http.GET

interface ChaseApi {

    @GET("f9bf-2cp4.json")
    suspend fun getNYCSchoolsList(): List<ChaseResponse>
}