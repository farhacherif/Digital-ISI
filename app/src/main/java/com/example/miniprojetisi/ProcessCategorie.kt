package com.example.miniprojetisi

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetisi.adapter.MyAdapter
import com.example.miniprojetisi.model.Categorie
import com.example.miniprojetisi.repository.Repository
import org.json.JSONArray
import java.util.List.of

class ProcessCategorie : AppCompatActivity() {
    private val myAdapter by lazy { MyAdapter() }
    var sharedPrefer: SharedPreferences? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_categorie)
        sharedPrefer= this.getSharedPreferences("sharedPreferFile", Context.MODE_PRIVATE)

        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider (this, viewModelFactory).get(MainViewModel::class.java)
        val sessionid:String?=sharedPrefer!!.getString("JSESSIONID",null)
        val token:String?=sharedPrefer!!.getString("TOKEN",null)
        val userid:String?=sharedPrefer!!.getString("USERID",null)
        Log.d("res",userid.toString())
        viewModel.getProcessCategorie(sessionid+";"+token,0,"userid")
       // Log.d("res2","hello")
        viewModel.myCategorie.observe(this, Observer { response ->
            //Log.d("res3","hello")
            if(response.isSuccessful) {

                    response.body()?.let { myAdapter.setData(it) }


              //  val mToast2 = Toast.makeText(applicationContext,"GET USER"+response.body().toString(), Toast.LENGTH_SHORT)
               // mToast2.show()
            } else {
                val mToast3 = Toast.makeText(applicationContext,"Erreur", Toast.LENGTH_SHORT)
                mToast3.show()
            }
        })
}

    private fun setupRecyclerview() {
        var recyclerView= findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }



}