package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.Charsets
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

/*
* Notes:
* Kotlin has this weird syntax for inline functions. Look at
* setonClickListener
*
* The RecyclerView is a component that allows us to render
* a collection of items. A typical RecyclerView can consist of:
* Adapter: A class used to connect our data to our app
* Layout Manager: A component that allows us to layout our
* content in any way that we want
* ItemAnimator: Helps us with Animating our items if we make any
* changes to our data like adding or removing items
* */

// TODO: Try to implement a Model for RecyclerView
class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()

    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyItemRemoved(position)
                saveItems()
            }
        }

        loadItems()

        // Detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            Log.i("Caren", "User clicked on button")
//        }


        // Look up the recycleView in the layout (id: recyclerView)
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)

        //Create adapter
        adapter = TaskItemAdapter(listOfTasks,onLongClickListener)
        //Attach adapter to the recyclerView
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        val todo_textField = findViewById<EditText>(R.id.addTaskField)
        // Add functionality to our button and text field
        val todo_button = findViewById<Button>(R.id.button).setOnClickListener {
            //Grad the text from text field
            var userInputtedTask = todo_textField.text.toString()

            //Add text to our array
            listOfTasks.add(userInputtedTask)

            //Update adapter to reflect change
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // Clear the text field
            todo_textField.setText("")

            //Save tasks
            saveItems()
        }

    }

    //Save the data that the user has inputted by reading and writing from file

    //Get the file we need
    fun getDataFile(): File {
        return File(filesDir,"data.txt")
    }
    //Load the items y reading every lines in file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch(ioException: IOException){
            ioException.printStackTrace()

        }
    }
    //Save items by writing the them into our data file
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(),listOfTasks)
        }catch(ioException:IOException){
            ioException.printStackTrace()
        }
    }
}