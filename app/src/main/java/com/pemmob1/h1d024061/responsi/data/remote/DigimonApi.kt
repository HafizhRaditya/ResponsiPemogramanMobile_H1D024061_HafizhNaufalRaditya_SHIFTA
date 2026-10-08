package com.pemmob1.h1d024061.responsi.data.remote

import com.pemmob1.h1d024061.responsi.data.model.DigimonDetail
import com.pemmob1.h1d024061.responsi.data.model.DigimonListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DigimonApi {

    @GET("digimon")
    suspend fun getDigimonList(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): DigimonListResponse

    @GET("digimon/{id}")
    suspend fun getDigimonDetail(
        @Path("id") id: Int,
    ): DigimonDetail
}

object ApiClient {

    private const val BASE_URL = "https://digi-api.com/api/v1/"

    val digimonApi: DigimonApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DigimonApi::class.java)
    }
}
