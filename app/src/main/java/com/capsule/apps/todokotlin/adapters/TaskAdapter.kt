package com.capsule.apps.todokotlin.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.capsule.apps.todokotlin.R
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

class TaskAdapter(context: Context, cursor: Cursor?, flags: Int) : CursorAdapter(context, cursor, flags) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View? {
        return LayoutInflater.from(context).inflate(R.layout.task_listview_item, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        var titleTextView = view?.findViewById(R.id.list_item_title) as EllipsizedTextView
        val TITLE_COL_INDEX = cursor?.getColumnIndex(TaskContract.TaskEntry.COL_TITLE) as Int

        val taskTitle = cursor?.getString(TITLE_COL_INDEX)
        titleTextView.text = taskTitle
    }

    override fun isEmpty(): Boolean {
        return cursor?.count == 0
    }
}