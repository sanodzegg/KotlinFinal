package com.example.booksapp.presentation.search.details

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.example.booksapp.data.local.data.Book
import com.example.booksapp.data.local.data.BookViewModel
import com.example.booksapp.databinding.DetailsFragmentBinding
import com.example.booksapp.domain.entities.BookDetails
import com.example.booksapp.data.handler.Result
import com.example.booksapp.domain.tools.SnackBars
import com.example.booksapp.presentation.search.details.bottomSheet.BottomSheet

class DetailsFragment : Fragment(R.layout.details_fragment) {

    private lateinit var viewModel: DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var url: String
    private lateinit var book: ArrayList<Book>
    private lateinit var bookViewModel: BookViewModel
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(DetailsViewModel::class.java)
        bookViewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        book = ArrayList<Book>()
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.share.setOnClickListener { share() }
        binding.save.setOnClickListener { save() }
        binding.web.setOnClickListener { web() }
        setupObservers()
        viewModel.bookDetails(args.isbn13)
    }

    private fun getTextInfoSpan(textFirst: String, textSecond: String): SpannedString {
        return buildSpannedString {
            append(textFirst)
            append(" ")
            inSpans(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey
                    )
                )
            ) { append(textSecond) }
        }
    }

    private fun save() {
        bookViewModel.addUser(book[0])
        Toast.makeText(requireContext(),"book added sussesfully",Toast.LENGTH_LONG).show()
    }

    private fun share() {
        val share = Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, url)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            },
            "Book"
        )

        requireActivity().startActivity(share)
    }

    private fun web() {
        val webView = BottomSheet(url)
        fragmentManager?.let { webView.show(it, "BottomSheet") }
    }
    private fun handleTermsAndConditions(result: Result<BookDetails>) {
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
                Glide.with(requireContext()).load(result.data.image).into(binding.appCompatImageView)
                binding.autor.text = getTextInfoSpan("Authors:", result.data.authors)
                binding.publisher.text = getTextInfoSpan("Publisher:", result.data.publisher)
                binding.rating.text = getTextInfoSpan("Rating:", result.data.rating)
                binding.year.text = getTextInfoSpan("Year:", result.data.year)
                url = result.data.url
                book.add(
                    Book(
                        id = 0,
                        title = result.data.title,
                        subtitle = result.data.subtitle,
                        isbn13 = result.data.isbn13,
                        price = result.data.price,
                        image = result.data.image
                    )
                )
                binding.description.text = result.data.desc
            }
        }
    }

    private fun setupObservers() {
        viewModel.getBookDetails.observe(viewLifecycleOwner, ::handleTermsAndConditions)

    }
}