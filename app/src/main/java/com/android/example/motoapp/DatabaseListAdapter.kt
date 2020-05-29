package com.android.example.motoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DatabaseListAdapter internal constructor(
    context:Context
):RecyclerView.Adapter<DatabaseListAdapter.DatabaseViewHolder>() {

    private val inflater:LayoutInflater= LayoutInflater.from(context)
    private var info= emptyList<Table>()  //Cached copy of data

    inner class DatabaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        //val infoItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return DatabaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DatabaseViewHolder, position: Int) {
        val current=info[position]
        val latitude=current.latitudeDB.toString()
        val longitude=current.longitudeDB.toString()
        val rotation=current.rotationDB.toString()

        //Display info on ViewModel
        //holder.infoItemView.text= rotation
       //holder.infoItemView.text= latitude
        //holder.infoItemView.text=longitude

    }

    internal fun setInfo(info:List<Table>){
        this.info=info
        notifyDataSetChanged()
    }

    override fun getItemCount()=info.size
}