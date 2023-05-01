package com.example.itsum.retrofit

data class kakaoDataClass(
  var accessToken:String,
  var idToken:String
)
data class kakaoResponse(
  var appToken:String,
  var isNewMember:Boolean
)