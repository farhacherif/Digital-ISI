package com.example.miniprojetisi.repository

import com.example.miniprojetisi.api.RetrofitInstance
import com.example.miniprojetisi.model.*
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

class Repository {

    /*suspend fun pushLogin(user: User):Response<User>{
        return RetrofitInstance.api.pushLogin(user)
    }*/

    suspend fun pushLogin2(username:String,password:String,redirect:Boolean): Response<User> {
        return RetrofitInstance.api.pushLogin2(username,password,redirect)
    }

    suspend fun getUserId(@Header("Cookie")jsessionidAndToken : String): Response<UserIdResponse>{
        return RetrofitInstance.api.getUserId(jsessionidAndToken)
    }

    suspend fun getProcessCategorie(@Header("Cookie")jsessionidAndToken : String, p:Int, f:String): Response<List<Categorie>> {
        return RetrofitInstance.api.getProcessCategorie(jsessionidAndToken,p,f)
    }



    suspend fun getProcess(@Header("Cookie")jsessionidAndToken : String, p:Int, c: Int, f1:String,f2:String): Response<List<Process>> {
        return RetrofitInstance.api.getProcess(jsessionidAndToken,p,c,f1,f2)
    }

    suspend fun getFormProcess(@Header("Cookie")jsessionidAndToken : String, @Path(value = "processId") processId: String): Response<Contrat> {
        return RetrofitInstance.api.getFormProcess(jsessionidAndToken,processId)
    }

    suspend fun postFormProcess(@Header("Cookie")jsessionidAndToken  : String, @Header("X-Bonita-API-Token") token: String,@Path(value = "processId") processId: String ,@Body myObject: String):Response<Case>{
    return RetrofitInstance.api.postFormProcess(jsessionidAndToken,token,processId,myObject)
    }

}