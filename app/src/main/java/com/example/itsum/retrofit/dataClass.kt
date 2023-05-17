package com.example.itsum.retrofit


data class ClubPostData(
  var title:String?=null,
  var contents:String?=null,
  var positionList :String?=null,
  var personnel:Int?=null,
  var techSkill:String?=null,
  var meetingWays :String?=null,
  var members :String?=null,
)

data class ClubPostResponse(
  var id:Long?=null,
  var title:String?=null,
  var contents:String?=null,
  var view:Int?=null,
  var positionList:String?=null,
  var personnel:Int?=null,
  var techSkill:String?=null,
  var meetingWay:String?=null,
  var members:String?=null,
  var createdAt:String?=null,
  var modifiedAt:String?=null,
)

data class ClubGetData(
  var title:String?=null,
  var contents:String?=null,
  var positionList :String?=null,
  var personnel:Int?=null,
  var techSkill:String?=null,
  var meetingWays :String?=null,
  var createdAt : String?=null,
)