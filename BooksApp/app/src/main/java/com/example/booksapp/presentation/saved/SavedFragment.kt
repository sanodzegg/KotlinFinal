package com.example.booksapp.presentation.saved

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.example.booksapp.data.local.data.Book
import com.example.booksapp.data.local.data.BookViewModel
import com.example.booksapp.databinding.DetailsFragmentBinding
import com.example.booksapp.databinding.SavedFragmentBinding
import com.example.booksapp.domain.adapter.BooksListAdapter
import com.example.booksapp.domain.entities.BookDetails
import com.example.booksapp.presentation.search.details.DetailsViewModel

class SavedFragment : Fragment(R.layout.saved_fragment) {
    private lateinit var binding: SavedFragmentBinding
    private lateinit var bookViewModel:BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SavedFragmentBinding.inflate(layoutInflater)

        bookViewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)

        getData()
        return binding.root
    }
    private fun getData(){
        bookViewModel.readAllData.removeObservers(viewLifecycleOwner)
        bookViewModel.readAllData.observe(viewLifecycleOwner, Observer { book ->
            binding.recOfSaveData.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            val books = ArrayList<Book>()
            for (i in 0 until book.size){
                books.add(book[i])
            }
            binding.recOfSaveData.adapter = BooksListAdapter(books,this)
        })
    }


}