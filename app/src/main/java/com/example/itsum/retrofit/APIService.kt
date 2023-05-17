package com.example.itsum.retrofit

import retrofit2.Call
import retrofit2.http.*

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


   @POST("/auth/kakao")
   fun kakaoLoginAuth(
      @Body accessToken: String
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
