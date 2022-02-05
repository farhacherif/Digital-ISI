package com.example.miniprojetisi.adapter

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetisi.ProcessCategorie
import com.example.miniprojetisi.ProcessList
import com.example.miniprojetisi.R
import com.example.miniprojetisi.model.Categorie

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var myList = emptyList<Categorie>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var CategorieId = itemView.findViewById<Button>(R.id.categorieId)
        var idC = itemView.findViewById<TextView>(R.id.idC)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.CategorieId.text = myList[position].name
        holder.idC.text = myList[position].id.toString()
        holder.CategorieId.setOnClickListener {
            //Log.d("reeeeeeeees", holder.idC.text.toString())

            val activity = holder.itemView.context as Activity

            val intent = Intent(activity, ProcessList::class.java).apply {
            }
            intent.putExtra("CategorieId", holder.idC.text.toString());
            activity.startActivity(intent)
        }
    }
        fun setData(newList: List<Categorie>) {
            myList = newList
            notifyDataSetChanged()
        }

}