package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
* Tells the recyclerview how to display the data
* */
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener)
    : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    //Create an interface so that we have access to MainActivity's
    //Array
    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        //We want to create our view of items
        //We can use LayoutInflater to construct a view from an XML File
        var inflater = LayoutInflater.from(context)
        //simple_list_item_1 is a built in view that just renders some text
        var contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)

        return ViewHolder(contactView)
    }

    // Adding the data into each of our items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get the data model based on position
        val item = listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //Store references to elements in our layout

        val textView: TextView

        init {
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

    }
}