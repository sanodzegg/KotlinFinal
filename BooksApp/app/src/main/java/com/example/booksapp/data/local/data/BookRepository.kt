package com.example.booksapp.data.local.data

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val readAllData: LiveData<List<Book>> = bookDao.readAllData()

    suspend fun addUser(book: Book){
        bookDao.addUser(book)
    }

}