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
import retrofit2.http.Path

interface APIService {

   @POST("/post")
   fun requestClubPost(
      @Header("Authorization") accessToken:String?,
      @Body parameters : ClubPostData
   ): Call<ClubPostResponse>


   @POST("/auth/kakao")
   fun kakaoLoginAuth(
      @Body accessToken: String,
   ): Call<kakaoResponse>

   @GET("/post/{ID}")
   fun requestClubData(
      @Header("Authorization") accessToken:String?,
      @Path("ID") id:Int
   ): Call<ClubGetData>

   companion object{
      private const val BASE_URL = "http://172.30.1.82:8080"
      fun create():APIService {
         val gson : Gson = GsonBuilder().setLenient().create()

         return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService::class.java)
      }
   }
}

