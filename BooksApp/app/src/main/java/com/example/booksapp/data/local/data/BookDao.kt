package com.example.booksapp.data.local.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(book: Book)

    @Query("SELECT * FROM book_table ORDER BY isbn13 ASC")
    fun readAllData(): LiveData<List<Book>>

}