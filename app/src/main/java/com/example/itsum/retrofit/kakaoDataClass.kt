package com.example.itsum.retrofit

import com.google.gson.annotations.SerializedName

data class kakaoDataClass(
  var accessToken:String,
  //var idToken:String
)
data class kakaoResponse(
  var accessToken:String,
  var isNewMember:Boolean
)

