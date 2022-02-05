package com.example.miniprojetisi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniprojetisi.model.*
import com.example.miniprojetisi.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse1: MutableLiveData<Response<User>> = MutableLiveData()
    val myResponse: MutableLiveData<Response<UserIdResponse>> = MutableLiveData()
    var myCategorie: MutableLiveData<Response<List<Categorie>>> = MutableLiveData()
    var myProcessList: MutableLiveData<Response<List<Process>>> = MutableLiveData()
    var myFormList: MutableLiveData<Response<Contrat>> = MutableLiveData()
    var myForm: MutableLiveData<Response<Case>> = MutableLiveData()

    /*
  fun pushLogin(user: User){
      viewModelScope.launch {
          val response :Response<User> = repository.pushLogin(user)
          myResponse1.value = response
      }

  }*/

    fun pushLogin2(username:String,password:String,redirect:Boolean){
        viewModelScope.launch {
            val response :Response<User> = repository.pushLogin2(username,password,redirect)
            myResponse1.value = response
        }
    }

    fun getUserId(@Header("Cookie")jsessionidAndToken : String) {
        viewModelScope.launch {
            val response: Response<UserIdResponse> = repository.getUserId(jsessionidAndToken)
            myResponse.value = response
        }
    }

    fun getProcessCategorie(@Header("Cookie")jsessionidAndToken : String, p: Int, f: String) {
        viewModelScope.launch {
            val response = repository.getProcessCategorie(jsessionidAndToken,p,f)
            myCategorie.value = response
        }
    }

    fun getProcessList(@Header("Cookie") jsessionidAndToken: String, p:Int, c: Int, f1: String, f2: String) {
        viewModelScope.launch {
            val response = repository.getProcess(jsessionidAndToken,p,c,f1,f2)
            myProcessList.value = response
        }
    }

    fun getFormProcess(@Header("Cookie") jsessionidAndToken: String, @Path(value = "processId") processId: String) {
        viewModelScope.launch {
            val response: Response<Contrat> = repository.getFormProcess(jsessionidAndToken,processId)
            myFormList.value = response
        }
    }

    fun postFormProcess(@Header("Cookie") jsessionidAndToken: String, @Header("X-Bonita-API-Token") token: String ,@Path(value = "processId") processId: String,@Body myObject: String){
        viewModelScope.launch {
            val response: Response<Case> = repository.postFormProcess(jsessionidAndToken,token,processId,myObject)
            myForm.value = response
        }
    }
}