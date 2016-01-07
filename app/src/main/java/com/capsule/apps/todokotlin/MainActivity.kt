package com.capsule.apps.todokotlin

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.RelativeLayout
import com.capsule.apps.todokotlin.adapters.TaskAdapter
import com.capsule.apps.todokotlin.models.TaskContract

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor>? {
        return CursorLoader(applicationContext, TaskContract.TaskEntry.CONTENT_URI, null, null, null, null)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        taskAdapter?.swapCursor(null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        taskAdapter?.swapCursor(data)
    }


    companion object {
        val TASK_LOADER = 0
        var taskAdapter: TaskAdapter? = null
        var listView: ListView? = null
        var emptyFrame: RelativeLayout? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        loaderManager.initLoader(TASK_LOADER, null, this)
        listView = findViewById(R.id.task_list_view) as ListView
        emptyFrame = findViewById(R.id.empty_frame) as RelativeLayout

        taskAdapter = TaskAdapter(applicationContext, null, 0)

        if ((taskAdapter as TaskAdapter).isEmpty()) {
            listView?.visibility = View.GONE
            emptyFrame?.visibility = View.VISIBLE
        } else {
            listView?.visibility = View.VISIBLE
            emptyFrame?.visibility = View.GONE
        }

        listView?.adapter = taskAdapter
        listView?.setOnItemClickListener ({ parent, view, position, id ->

            val currentTask: Cursor? = parent.getItemAtPosition(position) as Cursor
            val detailsIntent = Intent(this, TaskDetailsActivity::class.java)

            val TASK_ID_COL = currentTask?.getColumnIndex(TaskContract.TaskEntry._ID) as Int
            val _id = currentTask?.getLong(TASK_ID_COL) as Long
            val taskUri = TaskContract.TaskEntry.buildWithId(_id)

            detailsIntent.setData(taskUri)
            startActivity(detailsIntent)
            overridePendingTransition(0,0)
        })


        val newTaskFab = findViewById(R.id.fab_create_task) as FloatingActionButton
        newTaskFab.setOnClickListener({ view ->
            val newTaskIntent = Intent(applicationContext, CreateTaskActivity::class.java)
            startActivity(newTaskIntent)
            overridePendingTransition(0,0)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        when (itemId) {
            R.id.action_refresh -> {
                return true
            }
            R.id.action_settings -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
