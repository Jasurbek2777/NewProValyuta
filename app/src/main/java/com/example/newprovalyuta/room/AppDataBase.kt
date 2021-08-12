package com.example.newprovalyuta.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newprovalyuta.room.dao.ValyutaDao
import com.example.newprovalyuta.room.entity.ValyutaEntity

@Database(entities = [ValyutaEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): ValyutaDao

}