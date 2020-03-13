package com.bolasepak.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OpenHelper (
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_RESPONSE_TABLE = (
                "CREATE TABLE " + TABLE_NAME +  "("
                + COLUMN_ID + " PRIMARY KEY, "
                + COLUMN_NAME_1 + " TEXT,"
                + COLUMN_NAME_2 + " TEXT" + ")"
                )
        db?.execSQL(CREATE_RESPONSE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addEvent(eventDB: EventDB) {
        val values = ContentValues()
        values.put(COLUMN_NAME_1, eventDB.responseEvent)
        values.put(COLUMN_NAME_2, eventDB.responseTeam)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllResponse(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "event.db"
        val TABLE_NAME = "event"
        val COLUMN_ID = "_id"
        val COLUMN_NAME_1 = "responseEvent"
        val COLUMN_NAME_2 = "responseTeam"
    }
}