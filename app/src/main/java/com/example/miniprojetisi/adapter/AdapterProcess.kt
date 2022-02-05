package com.example.miniprojetisi.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetisi.FormProcess
import com.example.miniprojetisi.R
import com.example.miniprojetisi.model.Process


class AdapterProcess : RecyclerView.Adapter<AdapterProcess.MyViewHolder>() {

    private var myList = emptyList<Process>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ProcessId = itemView.findViewById<Button>(R.id.processId)
        var idP = itemView.findViewById<TextView>(R.id.idP)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.raw_process, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ProcessId.text = myList[position].name
        holder.idP.text = myList[position].id.toString()
        val activity = holder.itemView.context as Activity

        holder.ProcessId.setOnClickListener {
        val intent = Intent(activity, FormProcess::class.java).apply {
        }
        intent.putExtra("ProcessId", holder.idP.text.toString());
        activity.startActivity(intent)
    }
    }

    fun setData(newList: List<Process>) {
        myList = newList
        notifyDataSetChanged()
    }

}