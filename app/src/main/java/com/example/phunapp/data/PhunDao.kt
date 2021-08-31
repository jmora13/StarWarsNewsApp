package com.example.phunapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.phunapp.PhunModel.PhunModelItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PhunDao {

    @Query("SELECT * FROM PhunModelItem ORDER BY date")
    fun getAllItems(): Flow<List<PhunModelItem>>

    @Query("SELECT * FROM PhunModelItem WHERE id =:id")
    suspend fun getPhunItem(id: Int): PhunModelItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PhunModelItem)

    @Query("DELETE FROM PhunModelItem")
    fun deleteItems()
}
