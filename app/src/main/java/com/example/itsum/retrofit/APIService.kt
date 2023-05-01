package com.example.itsum.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
   @FormUrlEncoded
   @POST("/posts")
   fun requestClubPost(
      @Field("title") title: String,
      @Field("contents") contents: String,
      @Field("positionList ") positionList: String,
      @Field("personnel") personnel: Int,
      @Field("techSkill") techSkill: String,
      @Field("meetingWays ") meetingWays: String,
   ): Call<postDataClass>

   @FormUrlEncoded
   @POST("/auth/kakao")
   fun kakaoLoginAuth(
      @Header("accessToken") accessToken: String,
      @Field("idToken") idToken: String?
   ): Call<kakaoResponse>

   @GET("/posts/{ID}")
   fun requestClubData(
      @Field("title") title: String,
      @Field("contents") contents: String,
      @Field("positionList ") positionList: String,
      @Field("personnel") personnel: Int,
      @Field("techSkill") techSkill: String,
      @Field("meetingWays ") meetingWays: String,
   ): Call<postDataClass>
}

