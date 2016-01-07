package com.capsule.apps.todokotlin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.capsule.apps.todokotlin.models.TaskContract
import com.capsule.apps.todokotlin.widgets.OpenEditText
import com.capsule.apps.todokotlin.widgets.OpenTextView

/**
 * @author Larry Pham
 * @date: ${SHORT_NAME_MONTH}.07.2016
 * <br/>
 * Email: larrypham.vn@gmail.com.
 * Copyright (C) 2016 Capsule Inc. All rights reserved.
 */

class CreateTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_close, theme)
        toolbar.showOverflowMenu()

        setSupportActionBar(toolbar)
        supportActionBar.setDisplayShowTitleEnabled(false)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun saveTask() {
        val taskTitleEdiText = findViewById(R.id.task_title) as OpenEditText
        val taskDescriptionEditText = findViewById(R.id.task_description) as OpenEditText

        val taskTitle: String = taskTitleEdiText.text.toString()
        val taskDescription: String = taskDescriptionEditText.text.toString()

        if (taskTitle.isEmpty() or taskDescription.isEmpty()) {
            val inputEmpty = getString(R.string.error_input_empty)
            Toast.makeText(applicationContext, inputEmpty, Toast.LENGTH_LONG).show()
        } else {
            val values = ContentValues()
            values.put(TaskContract.TaskEntry.COL_TITLE, taskTitle)
            values.put(TaskContract.TaskEntry.COL_DESCRIPTION, taskDescription)

            var inserted = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, values)
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
            overridePendingTransition(0,0)

            Log.d("NewTask", "Inserted: $inserted")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_create_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        when (itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
                overridePendingTransition(0,0)
                return true
            }
            R.id.action_save -> {
                saveTask()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}