package com.capsule.apps.todokotlin.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @author Larry Pham
 * @date: ${SHORT_NAME_MONTH}.07.2016
 * <br/>
 * Email: larrypham.vn@gmail.com.
 * Copyright (C) 2016 Capsule Inc. All rights reserved.
 */

class TaskDbHelper(context: Context?) : SQLiteOpenHelper(context, TaskDbHelper.DATABASE_NAME, null, TaskDbHelper.DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "task.db"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTaskTable = "CREATE TABLE ${TaskContract.TaskEntry.TABLE_NAME} (" +
                "${TaskContract.TaskEntry._ID} INTEGER PRIMARY KEY, " +
                "${TaskContract.TaskEntry.COL_TITLE} TEXT NOT NULL, " +
                "${TaskContract.TaskEntry.COL_DESCRIPTION} TEXT NOT NULL, " +
                " UNIQUE (${TaskContract.TaskEntry.COL_TITLE}) ON CONFLICT REPLACE" +");";
        db?.execSQL(createTaskTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TaskContract.TaskEntry.TABLE_NAME}")
        onCreate(db)
    }
}