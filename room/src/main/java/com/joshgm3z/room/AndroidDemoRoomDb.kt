package com.joshgm3z.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AndroidDemoRoomDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}