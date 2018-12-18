package com.dcmobile.messenger7egend.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Message::class, User::class, Attachment::class], version = 1, exportSchema = false)

abstract class MyDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}