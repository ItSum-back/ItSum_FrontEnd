package com.example.itsum.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
}

