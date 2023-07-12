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
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Path

interface APIService {

   @POST("/post")
   fun requestClubPost(
      @Header("Authorization") accessToken:String?,
      @Body parameters : ClubPostData
   ): Call<ClubPostResponse>

   @GET("/post/{id}")
   fun requestClubData(
      @Header("Authorization") accessToken:String?,
      @Path("id") id:Int
   ): Call<ClubGetData>

   @PUT("/post/{id}")
   fun requestClubPut(
      @Header("Authorization") accessToken:String?,
      @Path("id") id:Int,
      @Body parameters : ClubPutData
   ): Call<ClubPutResponse>

   @DELETE("/post/{id}")
   fun requestClubDelete(
      @Header("Authorization") accessToken:String?,
      @Path("id") id:Int
   ): Call<Void>

   @PUT("/user/{id}")
   fun requestUserNameChange(
      @Header("Authorization") accessToken:String?,
      @Path("id") id:Int,
      @Body UserName:String
   ):Call<UserNameChangeResponse>

   @POST("/auth/kakao")
   fun kakaoLoginAuth(
      @Body accessToken: String,
   ): Call<kakaoResponse>

   @POST("auth/google")
   fun googleLoginAuth(
      @Body accessToken: String
   ): Call<googleResponse>

   @POST("/comments/{postId}")
   fun postComment(
      @Header("Authorization") accessToken:String?,
      @Path("postId") postId:Int,
      @Body parameters: postComment
   ):Call<postCommentResponse>

   @GET("/comments/{postId}")
   fun requestCommentList(
      @Header("Authorization") accessToken:String?,
      @Path("postId") postId:Int,
   ):Call<CommentGetResponse>

   @GET("/post")
   fun searchUsingGet(
      @Query("contents") contents:String?=null,
      @Query("meetingWay") meetingway:String?=null,
      @Query("page") page: Int?=null,
      @Query("positionList") positionlist:String?=null,
      @Query("size") pageSize: Int?=null,
      @Query("sort", encoded=true) sortSorted:String?=null,
      @Query("techSkill") techskill:String?=null,
      @Query("title") title:String?=null,
      @Query("members") members:String?=null
   ):Call<ClubSearchResponse>
   companion object{
      private const val BASE_URL = "http://192.168.219.110:8080"

      fun create():APIService {
         val gson : Gson = GsonBuilder().setLenient().create()

         return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(APIService::class.java)
      }
   }
}

class ATM{
   companion object{
      private var token:String=""
      private var socialId:String=""
      private var userName:String=""
      fun getToken():String = this.token
      fun getId():String = this.socialId
      fun getName():String = this.userName
      fun setToken(token:String) {
         this.token = token
         println("토큰" + token)
      }
      fun setId(socialId:String) {
         this.socialId = socialId
         println("소셜아이디" + socialId)
      }
      fun setName(userName:String) {
         this.userName = userName
         println("유저닉네임" + userName)
      }
   }

}
