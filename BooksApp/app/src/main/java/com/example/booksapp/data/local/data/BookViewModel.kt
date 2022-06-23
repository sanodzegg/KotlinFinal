package com.example.booksapp.data.local.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Book>>
    private val repository: BookRepository

    init {
        val userDao = BookDatabase.getDatabase(application).userDao()
        repository = BookRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(book)
        }
    }

}