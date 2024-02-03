package com.firstproject.new_project.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firstproject.new_project.Models.dataset
import com.firstproject.new_project.databinding.ListItems2Binding


class sAdapter(private  val list:MutableList<dataset>):
    RecyclerView.Adapter<sAdapter.ViewHolder>(){

    private var listener:AdapterClickInterface?=null

    fun setlistener(listener:AdapterClickInterface){
        this.listener=listener

    }

    inner class ViewHolder(val binding: ListItems2Binding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding=ListItems2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           with(holder){
               with(list[position]){
                   binding.tv.text=this.task

                   binding.deleteTask.setOnClickListener{
                      listener?.ondeleteBtnClick(this)
                   }
                   binding.editTask.setOnClickListener{
                     listener?.oneditBtnClick(this)
                   }
               }
           }
    }
    interface  AdapterClickInterface{
        fun ondeleteBtnClick(data:dataset)
        fun oneditBtnClick(data:dataset)
    }
}


