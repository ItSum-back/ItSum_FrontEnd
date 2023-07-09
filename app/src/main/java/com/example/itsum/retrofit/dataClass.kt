package com.example.itsum.retrofit

import android.icu.number.IntegerWidth


data class ClubPostData(
  var category : String,
  var contact : String,
  var contents:String,
  var deadLine:String,
  var meetingWays :String,
  var members :String,
  var personnel:Int,
  var positionList :String,
  var projectEndTime:String,
  var projectStartTime:String,
  var socialId: String,
  var techSkill:String,
  var title:String,
)

data class ClubPostResponse(
  var success : Boolean,
  var code : Int,
  var message : String,
  var data : Int,
)

data class ClubGetData(
  var success: Boolean,
  var code: Int,
  var message: String,
  var data: ClubGetDataDto,
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
  var socialId:String,
  var techSkill: String?=null,
  var title: String?=null,
  var view:Int?=null,
)

data class ClubPutData(
  var category : String,
  var contact : String,
  var contents:String,
  var deadLine:String,
  var meetingWays :String,
  var members :String,
  var personnel:Int,
  var positionList :String,
  var projectEndTime:String,
  var projectStartTime:String,
  var techSkill:String,
  var title:String,
)

data class ClubPutResponse(
  val void: Void
)

data class CommentGetResponse(
  var code: Int,
  var message: String,
  var success: Boolean,
  var data: CommentDataList
)

data class CommentDataList(
  var code: Int,
  var msg: String,
  var success: Boolean,
  var data: CommentGetDto
)

data class CommentGetDto(
  var content: List<Comment>?=null,
  var pageable: CommentPageable,
  var sort: CommentSort,
  var size: Int,
  var number: Int,
  var first: Boolean,
  var last: Boolean,
  var numberOfElements: Int,
  var empty: Boolean,

)

data class CommentPageable(
  var page: Int,
  var size: Int,
  var sort: CommentSort
)

data class CommentSort(
  var empty: Boolean,
  var sorted: Boolean,
  var unsorted: Boolean
)

data class Comment(
  var contents: String,
  var createdAt: String,
  var creatorName: String,
  var id: Int,
  var modifiedAt: String
)

data class postComment(
  var contents: String,
  var creatorName : String,
  var post_id : Int,
  var socialId: String
)

data class postCommentResponse(
  var code: Int?=null,
  var data: Int?=null,
  var message: String,
  var success: Boolean,
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