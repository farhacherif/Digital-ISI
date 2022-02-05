package com.example.miniprojetisi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.miniprojetisi.repository.Repository

class MainActivity : AppCompatActivity() {

    var sharedPrefer: SharedPreferences? = null



    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPrefer= this.getSharedPreferences("sharedPreferFile",Context.MODE_PRIVATE)
        }
        fun login(v: View) {
            val username = findViewById(R.id.inputEmail) as EditText
            val password = findViewById(R.id.inputPassword) as EditText
            //val textView = findViewById(R.id.textviewid) as TextView
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider (this, viewModelFactory).get(MainViewModel::class.java)

            // val myUser = User("etud1.l3sil", "bpm")

            viewModel.pushLogin2(username.text.toString(),password.text.toString(),false)
            viewModel.myResponse1.observe(this, Observer { response ->

                if(response.isSuccessful){
                    val Cookielist = response.headers().values("Set-Cookie")
                    //Log.d("headers", response.headers().values("Set-Cookie"))
                    val jsessionid = Cookielist[1].split(";").toTypedArray()[0]
                    //Log.d("headers", jsessionid)

                    val accessToken = Cookielist[2].split(";").toTypedArray()[0]
                    //Log.d("headersT", accessToken)

                    val editor:SharedPreferences.Editor =  sharedPrefer!!.edit()
                    editor.apply{
                        putString("JSESSIONID",jsessionid)
                        putString("TOKEN",accessToken)
                    }.apply()

                    val mToast4 = Toast.makeText(applicationContext,"Successful Sign-In", Toast.LENGTH_SHORT)
                    mToast4.show()
                    val sessionid:String?=sharedPrefer!!.getString("JSESSIONID",null)
                    val token:String?=sharedPrefer!!.getString("TOKEN",null)

                    viewModel.getUserId(sessionid+";"+token)

                    viewModel.myResponse.observe(this, Observer { response ->
                        if(response.isSuccessful){
                            val editor:SharedPreferences.Editor =  sharedPrefer!!.edit()
                            editor.apply{
                                putString("USERID",response.body()?.user_id.toString())

                            }.apply()
                            val mToast2 = Toast.makeText(applicationContext,"GET USER"+response.body()?.user_id.toString(), Toast.LENGTH_SHORT)
                            mToast2.show()
                        }
                        else{
                            val mToast3 = Toast.makeText(applicationContext,"ELSE GET USER"+response.code().toString(), Toast.LENGTH_SHORT)
                            mToast3.show()
                        }
                    })
                    val intent = Intent(this, ProcessCategorie::class.java).apply {
                    }
                    startActivity(intent)

                }else{
                    val mToast4 = Toast.makeText(applicationContext,"Failed Sign-In", Toast.LENGTH_SHORT)
                    mToast4.show()                }
            })


        }


        /*fun getUser(v: View){
            val sessionid:String?=sharedPrefer!!.getString("JSESSIONID",null)
            val token:String?=sharedPrefer!!.getString("TOKEN",null)

            viewModel.getUserId(sessionid+";"+token)
            viewModel.myResponse.observe(this, Observer { response ->
                if(response.isSuccessful){

                    val mToast2 = Toast.makeText(applicationContext,"GET USER "+response.body()?.user_id.toString(), Toast.LENGTH_SHORT)
                    mToast2.show()
                }
                else{
                    val mToast3 = Toast.makeText(applicationContext,"ELSE GET USER"+response.code().toString(), Toast.LENGTH_SHORT)
                    mToast3.show()
                }
            })

        }*/



}