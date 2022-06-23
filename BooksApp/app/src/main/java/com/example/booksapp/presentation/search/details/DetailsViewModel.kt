package com.example.booksapp.presentation.search.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.data.network.RetrofitInstance
import com.example.booksapp.data.network.Services
import com.example.booksapp.domain.entities.BookDetails
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import com.example.booksapp.data.handler.Result

class DetailsViewModel : ViewModel() {

    private val bookDetails = MutableLiveData<Result<BookDetails>>()
    val getBookDetails: LiveData<Result<BookDetails>> get() = bookDetails
    fun bookDetails(isbn13:String){
        viewModelScope.launch(
            CoroutineExceptionHandler { _, exeption ->
                bookDetails.value = Result.Error(exeption)
            }
        ) {
            val retrofitInstance =  RetrofitInstance.getRetrofitInstance().create(Services::class.java)
            val response = retrofitInstance.booksDetail(isbn13)
            bookDetails.value = Result.Success(response)
        }
    }

}