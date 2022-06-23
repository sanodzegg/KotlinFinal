package com.example.booksapp.presentation.search.details.bottomSheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.booksapp.databinding.SavedFragmentBinding
import com.example.booksapp.databinding.WebBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(val url:String):BottomSheetDialogFragment() {
    private lateinit var binding:WebBottomSheetBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (dialog is BottomSheetDialog) {
            dialog.behavior.apply {
                isDraggable = false
                isFitToContents = true
                isHideable = false
                skipCollapsed = true
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WebBottomSheetBinding.inflate(layoutInflater)
        setWeb()
        binding.btnClose.setOnClickListener {
            close()
        }
        return binding.root
    }
    private fun setWeb(){
        binding.webWiew.loadUrl(url)
        binding.webWiew.apply {
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
    }
    private fun close(){
        dismiss()
    }

}