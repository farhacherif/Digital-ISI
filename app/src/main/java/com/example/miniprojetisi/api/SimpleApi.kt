package com.example.miniprojetisi.api
import com.example.miniprojetisi.model.*
import org.json.JSONObject
import retrofit2.http.*

import retrofit2.Response

interface SimpleApi {

   /*@POST("bonita/loginservice")
   suspend fun pushLogin(
      @Body user:User
   ):Response<User>*/

   //Encoded data x-www-form-urlencoded
   @FormUrlEncoded
   @POST("bonita/loginservice")
   suspend fun pushLogin2(
      @Field("username") username:String?,
      @Field("password") password:String?,
      @Field("redirect") redirect: Boolean
   ):Response<User>

   //Pass cookies into header
   @GET("bonita/API/system/session/unusedId")
   suspend fun getUserId(@Header("Cookie")jsessionidAndToken : String): Response<UserIdResponse>


    @GET("bonita/portal/custom-page/API/bpm/category")
    suspend fun getProcessCategorie(
            @Header("Cookie")jsessionidAndToken  : String, @Query("p")p: Int, @Query("f")f: String): Response<List<Categorie>>

    @GET("bonita/API/bpm/process")
    suspend fun getProcess(
        @Header("Cookie")jsessionidAndToken  : String, @Query("p")p: Int, @Query("c")c: Int,
        @Query("f")f1: String, @Query("f")f2: String): Response<List<Process>>


    @GET("bonita/API/bpm/process/{processId}/contract")
    suspend fun getFormProcess(@Header("Cookie")jsessionidAndToken  : String,
                               @Path(value = "processId") processId: String): Response<Contrat>

    @Headers("Content-Type: application/json")
    @POST("bonita/API/bpm/process/{processId}/instantiation")
    suspend fun postFormProcess(@Header("Cookie")jsessionidAndToken  : String,
                                @Header("X-Bonita-API-Token") token : String,
                                @Path(value = "processId") processId: String,
                                @Body myObject:String
                                ):Response<Case>
}