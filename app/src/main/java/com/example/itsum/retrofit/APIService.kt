package com.example.itsum.retrofit

import com.example.itsum.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.TestOnly
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
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

   @POST("auth/google")
   fun googleLoginAuth(
      @Body accessToken: String
   ): Call<googleResponse>

   @GET("/post/{id}")
   fun requestClubData(
      @Header("Authorization") accessToken:String?,
      @Path("id") id:Int
   ): Call<ClubGetData>

   @GET("/post")
   fun searchUsingGet(
      @Query("contents") contents:String?=null,
      @Query("meetingWay") meetingway:String?=null,
      @Query("page") page: Int?=null,
      @Query("positionList") positionlist:String?=null,
      @Query("size") pageSize: Int?=null,
      @Query("sort", encoded=true) sortSorted:String?=null,
      @Query("techSkill") techskill:String?=null,
      @Query("title") title:String?=null
   ):Call<ClubSearchResponse>
   companion object{
      private const val BASE_URL = "http://172.30.1.21:8080"

      fun create():APIService {
         val gson : Gson = GsonBuilder().setLenient().create()

         return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService::class.java)
      }
   }
}

