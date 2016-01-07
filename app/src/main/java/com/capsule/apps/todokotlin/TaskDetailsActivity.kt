package com.capsule.apps.todokotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.capsule.apps.todokotlin.models.TaskContract
import com.capsule.apps.todokotlin.widgets.EllipsizedTextView
import com.capsule.apps.todokotlin.widgets.OpenTextView

/**
 * @author Larry Pham
 * @date: ${SHORT_NAME_MONTH}.07.2016
 * <br/>
 * Email: larrypham.vn@gmail.com.
 * Copyright (C) 2016 Capsule Inc. All rights reserved.
 */

class TaskDetailsActivity : AppCompatActivity() {
    companion object {
        var taskId = 0L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_close, theme)
        toolbar.showOverflowMenu()

        setSupportActionBar(toolbar)

        supportActionBar.setDisplayShowTitleEnabled(false)
        supportActionBar.setDisplayHomeAsUpEnabled(true)

        val taskUri = intent?.data as Uri
        taskId = TaskContract.TaskEntry.getIdFromUri(taskUri)

        val cursor = contentResolver.query(taskUri, null, null, null, null)
        cursor.moveToFirst()

        val taskTitle = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TITLE))
        val taskDescription = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_DESCRIPTION))

        val titleTextView = findViewById(R.id.detail_task_title) as EllipsizedTextView
        val descriptionTextView = findViewById(R.id.detail_task_description) as OpenTextView

        titleTextView.text = taskTitle
        descriptionTextView.text = taskDescription
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            R.id.action_delete -> {
                val deleted = contentResolver.delete(TaskContract.TaskEntry.CONTENT_URI,
                        "${TaskContract.TaskEntry._ID} = ?", arrayOf(taskId.toString()))
                if (deleted == 1) {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0,0)
                    this.finish()
                }
                return true
            }
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(0,0)
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}