package com.example.miniprojetisi

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetisi.adapter.AdapterProcess
import com.example.miniprojetisi.repository.Repository

class ProcessList : AppCompatActivity() {
    private val myAdapter1 by lazy { AdapterProcess() }
    var sharedPrefer: SharedPreferences? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_list)
        val ss:String = intent.getStringExtra("CategorieId").toString()
        sharedPrefer= this.getSharedPreferences("sharedPreferFile", Context.MODE_PRIVATE)

        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider (this, viewModelFactory).get(MainViewModel::class.java)
        val sessionid:String?=sharedPrefer!!.getString("JSESSIONID",null)
        val token:String?=sharedPrefer!!.getString("TOKEN",null)

        val userid:String?=sharedPrefer!!.getString("USERID",null)
          //  Log.d("catid", ss)
         //  Log.d("userid", userid.toString())

        viewModel.getProcessList(sessionid+";"+token,0,100,"user_id="+userid,"categoryId="+ss)

        viewModel.myProcessList.observe(this, Observer { response ->
            //Log.d("helloooooo",response.toString())
            if(response.isSuccessful) {

                response.body()?.let { myAdapter1.setData(it) }
                //val mToast2 = Toast.makeText(applicationContext,"MRIGLA"+response.body().toString(), Toast.LENGTH_SHORT)
                // mToast2.show()
            } else {
                val mToast3 = Toast.makeText(applicationContext,"Erreur"+response.code(), Toast.LENGTH_SHORT)
                mToast3.show()
            }
        })
    }

    private fun setupRecyclerview() {
        var recyclerView= findViewById<RecyclerView>(R.id.recyclerView1)

        recyclerView.adapter = myAdapter1
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


}
