package com.example.itsum.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
   @POST("/posts")
   fun requestClubPost(
      @Body jsonparams: ClubPostData
   ): Call<ClubPostResponse>

   @FormUrlEncoded
   @POST("/auth/kakao")
   fun kakaoLoginAuth(
      @Header("accessToken") accessToken: String,
      @Field("idToken") idToken: String?
   ): Call<kakaoResponse>

   @GET("/posts/{ID}")
   fun requestClubData(): Call<ClubGetData>

   companion object{
      private const val BASE_URL = "https://localhost:8080"
      fun create():APIService {
         val gson : Gson = GsonBuilder().setLenient().create()

         return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService::class.java)
      }
   }
}

