package com.sina.feature_item.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_details.R
import com.sina.feature_details.databinding.FragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemFragment : Fragment(R.layout.fragment_item) {
    private val TAG = "ProductFragment"
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentItemBinding.bind(view)
        val args = arguments
        Log.e(TAG, "onViewCreated:333 ${args?.getInt("productId")}")
        observers()
    }

    private fun observers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productItem.collectLatest {
                    when (it) {
                        is InteractState.Error -> {}
                        is InteractState.Loading -> {}
                        is InteractState.Success -> {
                        }
                    }
                }
            }
        }
    }
}