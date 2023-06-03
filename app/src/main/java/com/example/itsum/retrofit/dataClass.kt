package com.example.itsum.retrofit

import android.icu.number.IntegerWidth


data class ClubPostData(
  var title:String?=null,
  var contents:String?=null,
  var positionList :String?=null,
  var personnel:Int?=null,
  var techSkill:String?=null,
  var meetingWays :String?=null,
  var members :String?=null,
  var category : String?=null,
)

data class ClubPostResponse(
  var success : Boolean?=null,
  var code : Int?=null,
  var message : String?=null,
  var data : Int?=null,

  /* var id:Long?=null,
  var title:String?=null,
  var contents:String?=null,
  var view:Int?=null,
  var positionList:String?=null,
  var personnel:Int?=null,
  var techSkill:String?=null,
  var meetingWay:String?=null,
  var members:String?=null,
  var createdAt:String?=null,
  var modifiedAt:String?=null, */
)

data class ClubGetData(
  var success: Boolean?=null,
  var code: Int?=null,
  var message: String?=null,
  var data: ClubGetDataDto?=null,
)

data class ClubGetDataDto(
  var category: String?=null,
  var contact: String?=null,
  var contents: String?=null,
  var createdAt: String?=null,
  var deadline: String?=null,
  var id: Int?=null,
  var meetingWay: String?=null,
  var members: String?=null,
  var modifiedAt: String?=null,
  var personnel: Int?=null,
  var positionList: String?=null,
  var projectEndTime: String?=null,
  var projectStartTime: String?=null,
  var techSkill: String?=null,
  var title: String?=null,
  var view:Int?=null,
)

data class ClubSearchResponse(
  var success: Boolean?=null,
  var code: Int?=null,
  var msg: String?=null,
  var data: PostsListResponse?=null
)

data class PostsListResponse(
  //var content:PostsListResponseDto?=null,
  var content:List<PostsListResponseDto>?=null,
  var pageable: MyPageable?=null,
  var size:Int?=null,
  var number:Int?=null,
  var sort:Sort?=null,
  var first:Boolean?=null,
  var last:Boolean?=null,
  var numberofelements:Int?=null,
  var empty:Boolean?=null
)

data class PostsListResponseDto(
  var id:Int?=null,
  var title:String?=null,
  var contents:String?=null,
  var positionList:String?=null,
  var techSkill:String?=null,
  var meetingWays:String?=null,
  var personnel: Int?=null,
  var members:String?=null,
  var createdAt: String?=null,
  var modifiedAt:String?=null,
  var category:String?=null,
  var projectStartTime:String?=null,
  var projectEndTime:String?=null,
  var deadline:String?=null,
  var contact:String?=null,
  var view:Int?=null
)

data class MyPageable(
  var sort:Sort?=null,
  var offset:Int?=null,
  var pageSize:Int?=null,
  var pageNumber:Int?=null,
  var paged:Boolean?=null,
  var unpaged:Boolean?=null,
)

data class Sort(
  var empty:Boolean?=null,
  var sorted:Boolean?=null,
  var unsorted:Boolean?=null
)

data class googleResponse(
  var appToken:String,
  var isNewMember:Boolean
)