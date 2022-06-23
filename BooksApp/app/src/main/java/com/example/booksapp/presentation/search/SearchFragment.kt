package com.example.booksapp.presentation.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booksapp.R
import com.example.booksapp.databinding.SearchFragmentBinding
import com.example.booksapp.domain.adapter.BooksSearchAdapter
import com.example.booksapp.domain.entities.BooksSearchData
import com.example.booksapp.data.handler.Result
import com.example.booksapp.domain.tools.KeyboardHider
import com.example.booksapp.domain.tools.SnackBars

class SearchFragment : Fragment(R.layout.search_fragment) {
    private var pages = 1
    private var booksCount = 0
    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var keyboardHider: KeyboardHider


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        setUi()
        keyboardHider = KeyboardHider()
        keyboardHider.hideKeyboard(binding.root,requireActivity())
        return binding.root
    }

    private fun setUi() {
        binding.searchBtn.setOnClickListener {
            keyboardHider.hideSoftKeyboard(requireActivity())
            pages = 1
            setObserve()
            viewModel.searchBook(binding.searchEdtxt.text.toString(), pages)
        }

        binding.searchEdtxt.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                keyboardHider.hideSoftKeyboard(requireActivity())
                pages = 1
                setObserve()
                viewModel.searchBook(binding.searchEdtxt.text.toString(), pages)
                true
            }
            false
        }

        riple()
        nextBtnListener()
        previevBtnListener()
    }


    private fun drawRec(data: BooksSearchData) {
        binding.bookRec.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.bookRec.adapter = BooksSearchAdapter(data, this)
        if (data.books.isNotEmpty()) {
            binding.nextBtn.visibility = View.VISIBLE

        } else {
            binding.nextBtn.visibility = View.GONE
        }
    }

    private fun nextBtnListener() {
        binding.nextBtn.setOnClickListener {
            binding.previewBtn.visibility = View.VISIBLE
            pages += 1
            setObserve()
            viewModel.searchBook(binding.searchEdtxt.text.toString(), pages)
        }
    }

    private fun previevBtnListener() {
        binding.previewBtn.setOnClickListener {
            if (pages == 2) {
                binding.previewBtn.visibility = View.GONE
                pages = pages - 1
                setObserve()
                viewModel.searchBook(binding.searchEdtxt.text.toString(), pages)
            } else {
                pages = pages - 1
                setObserve()
                viewModel.searchBook(binding.searchEdtxt.text.toString(), pages)
            }

        }
    }

    private fun riple() {
        binding.nextBtn.isClickable = true
        binding.previewBtn.isClickable = true
    }

    private fun handleSearchedBooks(result: Result<BooksSearchData>) {
        when (result) {
            is Result.Loading -> {
                binding.initialLoadingProgress.visibility = View.VISIBLE

            }
            is Result.Error -> {
                binding.initialLoadingProgress.visibility = View.GONE
                val snackBars = SnackBars()
                snackBars.snackBar(binding.root,layoutInflater)

            }
            is Result.Success -> {
                binding.initialLoadingProgress.visibility = View.GONE
                if (result.data.books.isNotEmpty()) {
                    if (result.data.page == "1") {
                        binding.previewBtn.visibility = View.GONE
                        booksCount = result.data.books.size - 1
                    }
                    drawRec(result.data)
                } else {
                    binding.nextBtn.visibility = View.GONE

                    Toast.makeText(requireContext(), "Book Not Found", Toast.LENGTH_LONG).show()
                }
                if (result.data.books.size < booksCount) {
                    binding.nextBtn.visibility = View.GONE
                }
            }
        }
    }

    private fun setObserve() {
        viewModel.getSearchedBook.observe(viewLifecycleOwner, ::handleSearchedBooks)
    }


}