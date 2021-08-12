package com.example.newprovalyuta.room.dao

import androidx.room.*
import com.example.newprovalyuta.room.entity.ValyutaEntity

@Dao
interface ValyutaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(valyuta: ValyutaEntity)

    @Query("select * from valyutaentity")
    fun getAll(): List<ValyutaEntity>

    @Query("delete from valyutaentity where code =:code")
    fun deleteById(code: String)

    @Update
    fun update(valyuta: ValyutaEntity)
}