package com.example.itsum.retrofit

import com.google.gson.annotations.SerializedName

data class kakaoDataClass(
  var accessToken:String,
  //var idToken:String
)
data class kakaoResponse(
  var appToken:String,
  var isNewMember:Boolean,
  var socialId:String,
  var userName:String,
)

